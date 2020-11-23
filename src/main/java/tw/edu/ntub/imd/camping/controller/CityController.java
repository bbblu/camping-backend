package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.camping.bean.CityBean;
import tw.edu.ntub.imd.camping.service.CityService;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;

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
                            schema = @Schema(implementation = CityBean.class)
                    )
            )
    )
    @GetMapping(path = "")
    public ResponseEntity<String> searchAll() {
        return ResponseEntityBuilder.success("查詢成功")
                .data(cityService.searchAllEnableCity(), (data, cityBean) -> {
                    data.add("id", cityBean.getId());
                    data.add("name", cityBean.getName());
                    data.add("areaName", cityBean.getAreaName());
                })
                .build();
    }
}
