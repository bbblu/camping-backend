package tw.edu.ntub.imd.camping.util;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import tw.edu.ntub.birc.common.util.MathUtils;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.dto.Bank;
import tw.edu.ntub.imd.camping.dto.BankAccount;
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
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Validated
@Component
public class TransactionUtils {
    private final static HttpClient CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .proxy(HttpClient.Builder.NO_PROXY)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final String searchBankUrl;
    private final String createBankAccountUrl;
    private final String createTransactionUrl;
    private final ObjectMapper mapper;

    public TransactionUtils(
            @Value("${camping.credit-card-api.base-url}/bank")
                    String searchBankUrl,
            @Value("${camping.credit-card-api.base-url}/bank-account")
                    String createBankAccountUrl,
            @Value("${camping.credit-card-api.base-url}/transaction")
                    String createTransactionUrl,
            ObjectMapper mapper
    ) {
        this.searchBankUrl = searchBankUrl;
        this.createBankAccountUrl = createBankAccountUrl;
        this.createTransactionUrl = createTransactionUrl;
        this.mapper = mapper;
    }

    public List<Bank> searchBank() {
        return sendGetRequest(searchBankUrl, new TypeReference<>() {
        });
    }

    public void createBankAccount(@Valid BankAccount bankAccount) {
        try {
            ObjectData body = new ObjectData();
            body.add("account", bankAccount.getAccount());
            body.add("bankId", bankAccount.getBankId());
            if (bankAccount.getBankType() != null) {
                body.add("bankType", bankAccount.getBankType().ordinal());
            }
            body.add("bankName", bankAccount.getBankName());
            body.add("money", bankAccount.getMoney() != null ? bankAccount.getMoney() : 10_000);
            sendPostRequest(createBankAccountUrl, body, new TypeReference<>() {
            });
        } catch (CreditCardTransactionException e) {
            if (StringUtils.isNotEquals(e.getResponseErrorCode(), "Create - Duplicate")) {
                throw e;
            }
        }
    }

    private <T> T sendPostRequest(String url, ObjectData body, TypeReference<ResponseBody<T>> typeReference) {
        return sendRequest(
                url, "POST",
                HttpRequest.BodyPublishers.ofString(body.toString()),
                Collections.singletonMap("Content-Type", MediaType.APPLICATION_JSON_VALUE),
                typeReference
        );
    }

    private <T> T sendRequest(String url, String method, HttpRequest.BodyPublisher body, Map<String, String> headerMap, TypeReference<ResponseBody<T>> typeReference) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(url))
                    .method(method, body)
                    .version(HttpClient.Version.HTTP_1_1);
            headerMap.forEach(requestBuilder::header);
            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (MathUtils.isInRange(response.statusCode(), 200, 299)) {
                ResponseBody<T> responseBody = mapper.readValue(response.body(), typeReference);
                if (responseBody.isSuccess()) {
                    return responseBody.data;
                } else {
                    throw new CreditCardTransactionException("金流交易失敗：" + responseBody.getMessage(), responseBody.getErrorCode());
                }
            } else {
                throw new CreditCardTransactionException("金流交易失敗，Http狀態為：" + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new CreditCardTransactionException(e);
        }
    }

    private <T> T sendGetRequest(String url, TypeReference<ResponseBody<T>> typeReference) {
        return sendRequest(url, "GET", HttpRequest.BodyPublishers.noBody(), Collections.emptyMap(), typeReference);
    }

    public int createTransaction(@Valid CreditCard creditCard, String payeeBankAccount, int money) {
        try {
            ObjectData body = new ObjectData(mapper.writeValueAsString(creditCard));
            String expireDate;
            if (creditCard.getExpireDate() != null) {
                expireDate = creditCard.getExpireDate().format(DateTimeFormatter.ofPattern("MM/yy"));
            } else {
                expireDate = String.format("%02d/%02d", creditCard.getExpireMonth(), creditCard.getExpireYear());
            }
            body.add("expireDate", expireDate);
            body.add("payeeBankAccount", payeeBankAccount);
            body.add("money", money);
            TransactionId id = sendPostRequest(createTransactionUrl, body, new TypeReference<>() {
            });
            return id.getId();
        } catch (JsonProcessingException e) {
            throw new CreditCardTransactionException(e);
        }
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
