package com.kanghouchao.study.devops.server.api.client;

import com.kanghouchao.study.devops.server.api.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Validated
public interface UserAPI {

    @GetMapping("get")
    ResponseEntity<UserDTO> getUser(@RequestParam("user-id") Long userId);

}
