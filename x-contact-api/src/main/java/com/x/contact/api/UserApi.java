package com.x.contact.api;

import com.x.common.config.InternalInterceptor;
import com.x.common.response.BaseResult;
import com.x.contact.domain.UserDo;
import com.x.contact.dto.UserAuthDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author : xuemingqi
 * @since : 2023/1/13 17:06
 */
@FeignClient(name = "x-contact", configuration = InternalInterceptor.class)
public interface UserApi {

    @GetMapping("/contact/user/{mobile}/info")
    BaseResult<UserDo> getUserInfo(@PathVariable("mobile") String mobile);

    @PostMapping("/contact/user/auth")
    BaseResult<Long> userAuth(@RequestBody @Valid UserAuthDto param);
}
