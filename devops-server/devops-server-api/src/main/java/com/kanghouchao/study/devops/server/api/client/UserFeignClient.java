package com.kanghouchao.study.devops.server.api.client;

import com.kanghouchao.study.devops.server.api.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(path = "/api", value = "devops-server")
public interface UserFeignClient {

    @GetMapping(value = "get", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<UserDTO> getUser(Long userId);

}
