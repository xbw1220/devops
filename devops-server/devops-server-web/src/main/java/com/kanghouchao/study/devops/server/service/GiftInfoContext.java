/*
  Copyright 2019 kanghouchao
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.kanghouchao.study.devops.server.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.kanghouchao.study.devops.server.api.enums.ActivityType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Lurker
 * <p>
 * 日期： 2019/10/19
 * 联系方式: hchkang8710@gmail.com
 * <p/>
 */
@Component
@RefreshScope
public class GiftInfoContext {

    @Resource
    private List<IGiftInfoStrategyService> services;

    @Value("${type:1}")
    private int code;


    /**
     * @return 调用信息
     */
    @SentinelResource(value = "GiftInfo")
    public String clusterFlow() {
        final IGiftInfoStrategyService service = services.stream().filter(
                s -> s.getType().equals(ActivityType.getByCode(code))
        ).findFirst().orElseThrow(() -> new RuntimeException("对此类型没有业务实现"));
        return service.get();
    }

}
