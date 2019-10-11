package com.kanghouchao.study.devops.server.controller;

import com.kanghouchao.study.devops.server.vo.UserVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class UserController {

    @Value("${name:康厚超}")
    private String name;

    @GetMapping(value = "api/get", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserVO> getUser(@RequestParam(required = false, defaultValue = "100000") Long userId) {
        final UserVO vo = new UserVO();
        vo.setId(userId);
        vo.setName(name);
        return ResponseEntity.ok(vo);
    }

}
