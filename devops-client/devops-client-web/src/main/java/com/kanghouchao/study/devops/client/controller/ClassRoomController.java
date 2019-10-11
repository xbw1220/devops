package com.kanghouchao.study.devops.client.controller;

import com.kanghouchao.study.devops.server.api.client.UserFeignClient;
import com.kanghouchao.study.devops.server.api.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassRoomController {

    @Autowired
    private UserFeignClient feignClient;

    @GetMapping("get")
    public ResponseEntity<String> get(Long userId) {
        ResponseEntity<UserDTO> entity = feignClient.getUser(userId);
        assert entity.getBody() != null;
        return ResponseEntity.ok(entity.getBody().getName());
    }


}
