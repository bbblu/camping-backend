package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.camping.bean.ContactInformationBean;
import tw.edu.ntub.imd.camping.bean.UserBean;
import tw.edu.ntub.imd.camping.service.ContactInformationService;
import tw.edu.ntub.imd.camping.service.UserService;
import tw.edu.ntub.imd.camping.util.http.BindingResultUtils;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.camping.util.json.object.ObjectData;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Tag(name = "User", description = "使用者API")
@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;
    private final ContactInformationService contactInformationService;

    public UserController(UserService userService, ContactInformationService contactInformationService) {
        this.userService = userService;
        this.contactInformationService = contactInformationService;
    }

    @Operation(
            tags = "User",
            method = "POST",
            summary = "使用者註冊",
            description = "使用者註冊，預設權限為一般使用者",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "註冊成功",
                    content = @Content(
                            mediaType = "application/json"
                    )
            )
    )
    @PostMapping(path = "")
    public ResponseEntity<String> register(@Valid @RequestBody UserBean userBean, BindingResult bindingResult) {
        BindingResultUtils.validate(bindingResult);
        userService.save(userBean);
        return ResponseEntityBuilder.success().message("註冊成功").build();
    }

    @Operation(
            tags = "User",
            method = "GET",
            summary = "查詢使用者資訊",
            description = "查詢使用者資訊",
            parameters = @Parameter(
                    name = "account",
                    content = @Content(schema = @Schema(type = "string")),
                    description = "帳號",
                    example = "test"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "查詢成功",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserBean.class)
                    )
            )
    )
    @PreAuthorize("hasAnyAuthority('Administrator', 'User')")
    @GetMapping(path = "/{account}")
    public ResponseEntity<String> getUserInfo(
            @PathVariable(name = "account")
            @NotBlank(message = "使用者帳號不能為空")
                    String account
    ) {
        Optional<UserBean> optionalUser = userService.getById(account);
        if (optionalUser.isPresent()) {
            UserBean userBean = optionalUser.get();
            ObjectData data = new ObjectData();
            data.add("account", userBean.getAccount());
            data.add("firstName", userBean.getFirstName());
            data.add("lastName", userBean.getLastName());
            data.add("gender", userBean.getGenderName());
            data.add("birthday", userBean.getBirthday());
            data.add("experience", userBean.getExperience().toString());
            data.add("email", userBean.getEmail());
            data.add("address", userBean.getAddress());
            return ResponseEntityBuilder.success()
                    .message("查詢成功")
                    .data(data)
                    .build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            tags = "User",
            method = "POST",
            summary = "新增聯絡方式",
            description = "新增登入者的聯絡方式",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "新增成功",
                    content = @Content(
                            mediaType = "application/json"
                    )
            )
    )
    @PostMapping(path = "/contact-information")
    public ResponseEntity<String> createContactInformation(
            @Valid @RequestBody ContactInformationBean contactInformationBean,
            BindingResult bindingResult
    ) {
        BindingResultUtils.validate(bindingResult);
        contactInformationService.save(contactInformationBean);
        return ResponseEntityBuilder.success().message("新增成功").build();
    }
}
