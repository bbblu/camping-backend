package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.camping.dto.Bank;
import tw.edu.ntub.imd.camping.service.BankService;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.camping.util.json.object.ObjectData;

@Tag(name = "Bank", description = "金融機構API")
@RestController
@RequestMapping(path = "/bank")
public class BankController {
    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @Operation(
            tags = "Bank",
            method = "GET",
            summary = "查詢銀行清單",
            description = "查詢銀行清單",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "查詢成功",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Bank.class))
                    )
            )
    )
    @GetMapping(path = "")
    public ResponseEntity<String> searchAll() {
        return ResponseEntityBuilder.success("查詢成功")
                .data(bankService.searchAll(), this::addBankToObjectData)
                .build();
    }

    private void addBankToObjectData(ObjectData bankData, Bank bank) {
        bankData.add("id", bank.getId());
        bankData.add("type", bank.getTypeName());
        bankData.add("name", bank.getName());
    }
}
