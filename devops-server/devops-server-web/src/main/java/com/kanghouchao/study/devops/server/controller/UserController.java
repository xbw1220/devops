package com.kanghouchao.study.devops.server.controller;

import com.kanghouchao.study.devops.server.api.client.UserAPI;
import com.kanghouchao.study.devops.server.api.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kanghouchao
 */
@RestController
@RefreshScope
public class UserController implements UserAPI {

    @Value("${name:康厚超}")
    private String name;

    public ResponseEntity<UserDTO> getUser(Long userId) {
        final UserDTO dto = new UserDTO();
        dto.setId(userId);
        dto.setName(name);
        dto.setCode("20191013");
        return ResponseEntity.ok(dto);
    }

}
