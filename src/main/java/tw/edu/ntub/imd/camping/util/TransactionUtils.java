package tw.edu.ntub.imd.camping.util;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.MathUtils;
import tw.edu.ntub.imd.camping.dto.Bank;
import tw.edu.ntub.imd.camping.dto.CreditCard;
import tw.edu.ntub.imd.camping.exception.CreditCardTransactionException;
import tw.edu.ntub.imd.camping.util.json.object.ObjectData;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

@Component
public class TransactionUtils {
    private final static HttpClient CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .proxy(HttpClient.Builder.NO_PROXY)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final String searchBankUrl;
    private final String createBankAccountUrl;
    private final String createCreditCardUrl;
    private final String createTransactionUrl;
    private final String transactionDebitUrl;
    private final ObjectMapper mapper;

    public TransactionUtils(
            @Value("${camping.credit-card-api.base-url}/bank")
                    String searchBankUrl,
            @Value("${camping.credit-card-api.base-url}/bank-account")
                    String createBankAccountUrl,
            @Value("${camping.credit-card-api.base-url}/credit-card")
                    String createCreditCardUrl,
            @Value("${camping.credit-card-api.base-url}/transaction")
                    String createTransactionUrl,
            @Value("${camping.credit-card-api.base-url}/transaction/{id}/debit")
                    String transactionDebitUrl,
            ObjectMapper mapper
    ) {
        this.searchBankUrl = searchBankUrl;
        this.createBankAccountUrl = createBankAccountUrl;
        this.createCreditCardUrl = createCreditCardUrl;
        this.createTransactionUrl = createTransactionUrl;
        this.transactionDebitUrl = transactionDebitUrl;
        this.mapper = mapper;
    }

    public List<Bank> searchBank() {
        return sendGetRequest(searchBankUrl, new TypeReference<>() {
        });
    }

    public void createCreditCard(@Valid CreditCard creditCard) {
        ObjectData body = new ObjectData();
        body.add("cardId", creditCard.getCardId());
        body.add("safeCode", creditCard.getSafeCode());
        body.add("expireDate", creditCard.getExpireDate());
        body.add("balance", 30_000);
        sendPostRequest(createCreditCardUrl, body, new TypeReference<>() {
        });
    }

    private <T> T sendPostRequest(String url, ObjectData body, TypeReference<ResponseBody<T>> typeReference) {
        return sendRequest(url, "POST", HttpRequest.BodyPublishers.ofString(body.toString()), typeReference);
    }

    private <T> T sendRequest(String url, String method, HttpRequest.BodyPublisher body, TypeReference<ResponseBody<T>> typeReference) {
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                    .method(method, body)
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (MathUtils.isInRange(response.statusCode(), 200, 299)) {
                ResponseBody<T> responseBody = mapper.readValue(response.body(), typeReference);
                if (responseBody.isSuccess()) {
                    return responseBody.data;
                } else {
                    throw new CreditCardTransactionException("金流交易失敗：" + responseBody.getMessage());
                }
            } else {
                throw new CreditCardTransactionException("金流交易失敗，Http狀態為：" + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new CreditCardTransactionException(e);
        }
    }

    private <T> T sendGetRequest(String url, TypeReference<ResponseBody<T>> typeReference) {
        return sendRequest(url, "GET", HttpRequest.BodyPublishers.noBody(), typeReference);
    }

    public int createTransaction(CreditCard creditCard, int money) {
        try {
            ObjectData body = new ObjectData(mapper.writeValueAsString(creditCard));
            body.add("money", money);
            TransactionId id = sendPostRequest(createTransactionUrl, body, new TypeReference<>() {
            });
            return id.getId();
        } catch (JsonProcessingException e) {
            throw new CreditCardTransactionException(e);
        }
    }

    public void transactionDebit(int transactionId) {
        sendPostRequest(
                transactionDebitUrl.replace("{id}", String.valueOf(transactionId)),
                new ObjectData(),
                new TypeReference<>() {
                }
        );
    }

    @Data
    @EqualsAndHashCode
    private static class ResponseBody<T> {
        @JsonAlias("result")
        private boolean success;
        private String errorCode;
        private String message;
        private T data;
    }

    @Data
    @EqualsAndHashCode
    private static class TransactionId {
        private int id;
    }
}
