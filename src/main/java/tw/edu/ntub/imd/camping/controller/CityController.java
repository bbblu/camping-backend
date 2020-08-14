package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.camping.service.CityService;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.camping.util.json.array.ArrayData;
import tw.edu.ntub.imd.camping.util.json.object.ObjectData;

@Tag(name = "City", description = "城市區域")
@RestController
@RequestMapping(path = "/city")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @Operation(
            tags = "City",
            method = "GET",
            summary = "查詢所有可租借城市與區域",
            description = "查詢所有可租借城市與區域，城市為台北市、新北市，區域為中正區、板橋區",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "查詢成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SearchCitySchema.class)
                    )
            )
    )
    @GetMapping(path = "")
    public ResponseEntity<String> searchAll() {
        ObjectData data = new ObjectData();
        ArrayData nameArray = data.addArray("nameArray");
        ArrayData areaNameArray = data.addArray("areaNameArray");
        cityService.searchAllEnableCity().forEach((name, areaNameList) -> {
            nameArray.add(name);
            areaNameArray.addStringArray(areaNameList);
        });
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(data)
                .build();
    }

    @Schema(name = "城市及區域", description = "城市代表台北市、新北市，區域代表中正區、板橋區")
    @Data
    public static class SearchCitySchema {
        @ArraySchema(minItems = 0, uniqueItems = true, schema = @Schema(description = "城市名稱陣列", example = "新北市"))
        private String[] nameArray;
        @ArraySchema(minItems = 0, uniqueItems = true, schema = @Schema(description = "區域名稱陣列", type = "array", example = "三峽區(這是二維陣列)"))
        private String[][] areaNameArray;
    }
}
