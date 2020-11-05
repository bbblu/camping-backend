package tw.edu.ntub.imd.camping.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                version = "1.4.4",
                title = "借借露 - API",
                description = "此為所有系統API功能列表，如有疑問，請洽負責人員\n" +
                        "\n" +
                        "| 負責人姓名 | 信箱 |\n" +
                        "|:---------:|:----:|\n" +
                        "| 李恩瑋 | 10646007@ntub.edu.tw |\n" +
                        "| 黃峻彥 | 10646003@ntub.edu.tw |\n" +
                        "\n" +
                        "  以下為標準回傳格式，data請替換成API的Responses\n" +
                        "  ```json=\n" +
                        "  {\n" +
                        "      \"result\": boolean,\n" +
                        "      \"errorCode\": string,\n" +
                        "      \"message\": string,\n" +
                        "      \"data\": (參考Responses)\n" +
                        "  }\n" +
                        "  ```",
                contact = @Contact(
                        name = "李恩瑋",
                        email = "10646007@ntub.edu.tw"
                )
        ),
        servers = {
                @Server(url = "http://211.75.1.201:50004", description = "測試機"),
                @Server(url = "http://211.75.1.201:50004", description = "正式機")
        }
)
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi userApi(@Qualifier("userApiCustomiser") OpenApiCustomiser openApiCustomiser) {
        return GroupedOpenApi.builder()
                .setGroup("使用者 - User")
                .pathsToMatch("/user/**")
                .addOpenApiCustomiser(openApiCustomiser)
                .build();
    }

    @Bean("defaultOpenApiCustomiser")
    public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
        return openApi -> openApi.getPaths().values().parallelStream().map(PathItem::readOperations).flatMap(Collection::stream).forEach(operation -> {
            ApiResponses apiResponses = operation.getResponses();
            apiResponses.addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), new ApiResponse()
                    .description("請求缺少必要參數，請檢查Path/Query/Parameters/RequestBody")
            ).addApiResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()), new ApiResponse()
                    .description("使用者尚未登入")
            ).addApiResponse(String.valueOf(HttpStatus.FORBIDDEN.value()), new ApiResponse()
                    .description("使用者無權限使用此API")
            ).addApiResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), new ApiResponse()
                    .description("伺服器錯誤，請聯繫負責人員")
            );
        });
    }

    @Bean("userApiCustomiser")
    public OpenApiCustomiser openApiCustomiser(@Qualifier("defaultOpenApiCustomiser") OpenApiCustomiser openApiCustomiser) {
        return openApi -> {
            Paths paths = new Paths();
            Map<String, Schema> userLoginProperties = new LinkedHashMap<>();
            userLoginProperties.put("account", new Schema().type("string").description("帳號").maxLength(100));
            userLoginProperties.put("password", new Schema().type("string").description("密碼"));
            paths.addPathItem("/login", new PathItem().post(
                    new Operation()
                            .tags(Collections.singletonList("User"))
                            .summary("登入")
                            .requestBody(new RequestBody()
                                    .description("登入資訊，帳號與密碼")
                                    .required(true)
                                    .content(new Content()
                                            .addMediaType(APPLICATION_JSON_VALUE, new MediaType()
                                                    .schema(new Schema<>().name("Login").type("object").properties(userLoginProperties))
                                            )
                                    )
                            )
                            .responses(new ApiResponses()
                                            .addApiResponse(String.valueOf(HttpStatus.OK.value()), new ApiResponse()
                                                            .addHeaderObject("X-Auth-Token", new Header().schema(
                                                                    new Schema<>().type("string"))
                                                                    .description("JWT Token，往後每次請求皆須在request header中帶Authentication':' bearer {X-Auth-Token}")
                                                            )
//                                            .content(new Content().addMediaType(APPLICATION_JSON_VALUE, new MediaType().schema(new Schema<>().$ref("User"))))
                                            )
                            )
            ));
            openApi.getPaths().forEach(paths::addPathItem);
            openApi.setPaths(paths);
            openApiCustomiser.customise(openApi);
        };
    }

    @Bean
    public GroupedOpenApi productApi(@Qualifier("defaultOpenApiCustomiser") OpenApiCustomiser openApiCustomiser) {
        return GroupedOpenApi.builder()
                .setGroup("商品 - Product")
                .pathsToMatch("/product-group/**")
                .addOpenApiCustomiser(openApiCustomiser)
                .build();
    }

    @Bean
    public GroupedOpenApi cityApi(@Qualifier("defaultOpenApiCustomiser") OpenApiCustomiser openApiCustomiser) {
        return GroupedOpenApi.builder()
                .setGroup("城市 - City")
                .pathsToMatch("/city/**")
                .addOpenApiCustomiser(openApiCustomiser)
                .build();
    }

    @Bean
    public GroupedOpenApi rentalApi(@Qualifier("defaultOpenApiCustomiser") OpenApiCustomiser openApiCustomiser) {
        return GroupedOpenApi.builder()
                .setGroup("商品租借 - Rental")
                .pathsToMatch("/rental/**")
                .addOpenApiCustomiser(openApiCustomiser)
                .build();
    }

    @Bean
    public GroupedOpenApi problemReportApi(@Qualifier("defaultOpenApiCustomiser") OpenApiCustomiser openApiCustomiser) {
        return GroupedOpenApi.builder()
                .setGroup("問題回報 - Problem Report")
                .pathsToMatch("/problem-report/**")
                .addOpenApiCustomiser(openApiCustomiser)
                .build();
    }

    @Bean
    public GroupedOpenApi bankApi(@Qualifier("defaultOpenApiCustomiser") OpenApiCustomiser openApiCustomiser) {
        return GroupedOpenApi.builder()
                .setGroup("金融機構 - Bank")
                .pathsToMatch("/bank/**")
                .addOpenApiCustomiser(openApiCustomiser)
                .build();
    }

    @Bean
    public GroupedOpenApi problemReportApi(@Qualifier("defaultOpenApiCustomiser") OpenApiCustomiser openApiCustomiser) {
        return GroupedOpenApi.builder()
                .setGroup("問題回報 - ProblemReport")
                .pathsToMatch("/problem-report/**")
                .addOpenApiCustomiser(openApiCustomiser)
                .build();
    }
}
