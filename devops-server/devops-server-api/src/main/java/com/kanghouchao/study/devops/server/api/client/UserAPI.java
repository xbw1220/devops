package com.kanghouchao.study.devops.server.api.client;

import com.kanghouchao.study.devops.server.api.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author kanghouchao
 */
@Validated
public interface UserAPI {

    /**
     * 根据ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("get")
    ResponseEntity<UserDTO> getUser(@RequestParam("user-id") Long userId);

}
