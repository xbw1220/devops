package com.kanghouchao.study.devops.server.api.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "devops-server")
public interface UserFeignClient extends UserAPI {


}
