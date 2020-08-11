package tw.edu.ntub.imd.camping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.camping.bean.ContactInformationBean;
import tw.edu.ntub.imd.camping.bean.UserBean;
import tw.edu.ntub.imd.camping.service.ContactInformationService;
import tw.edu.ntub.imd.camping.service.UserService;
import tw.edu.ntub.imd.camping.util.http.BindingResultUtils;
import tw.edu.ntub.imd.camping.util.http.ResponseEntityBuilder;

import javax.validation.Valid;

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

    @PostMapping(path = "")
    public ResponseEntity<String> register(@Valid @RequestBody UserBean userBean, BindingResult bindingResult) {
        BindingResultUtils.validate(bindingResult);
        userService.save(userBean);
        return ResponseEntityBuilder.success().message("註冊成功").build();
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
    @PreAuthorize("isAuthenticated()")
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
