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
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.kanghouchao.study.devops.server.api.enums.ActivityType;
import com.kanghouchao.study.devops.server.exception.ExceptionUtil;
import com.kanghouchao.study.devops.server.vo.GiftVO;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GiftInfoContext {

    @Resource
    private List<IGiftInfoStrategyService> services;

    // 对应的 `handleException` 函数需要位于 `ExceptionUtil` 类中，并且必须为 static 函数.
    @SentinelResource(value = "test", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    public void test() {
        System.out.println("Test");
    }

    /**
     * 对外暴露的统一获取礼品信息的返回
     *
     * @param subjectId 项目编号
     * @param type      活动类型
     * @return 礼品信息
     */
    @SentinelResource(value = "GiftInfo", blockHandler = "exceptionHandler", fallback = "giftFallback")
    public GiftVO getGiftInfo(Long subjectId, ActivityType type) {
        final IGiftInfoStrategyService service = services.stream().filter(
                s -> s.getType().equals(type)
        ).findFirst().orElseThrow(() -> new RuntimeException("对此类型没有业务实现"));
        return service.get(subjectId);
    }

    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public GiftVO giftFallback(Long subjectId, ActivityType type) {
        GiftVO vo = new GiftVO();
        vo.setId(subjectId);
        vo.setName("降级处理礼物");
        return vo;
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public GiftVO exceptionHandler(Long subjectId, ActivityType type, BlockException ex) {
        log.error("异常处理", ex);
        GiftVO vo = new GiftVO();
        vo.setId(subjectId);
        vo.setName("异常处理礼物");
        return vo;
    }

}
