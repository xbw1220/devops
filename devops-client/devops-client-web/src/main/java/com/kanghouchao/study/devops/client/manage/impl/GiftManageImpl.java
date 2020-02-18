package com.kanghouchao.study.devops.client.manage.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.kanghouchao.study.devops.client.manage.GiftManage;
import com.kanghouchao.study.devops.server.api.client.GiftFeignClient;
import com.kanghouchao.study.devops.server.api.dto.GiftDTO;
import com.kanghouchao.study.devops.server.api.enums.ActivityType;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GiftManageImpl implements GiftManage {

    private GiftFeignClient giftFeignClient;


    @Override
    @SentinelResource(value = "GiftInfo", blockHandler = "exceptionHandler", fallback = "giftFallback")
    public GiftDTO get(ActivityType type, Long subjectId) {
        ResponseEntity<GiftDTO> entity = giftFeignClient.get(type, subjectId);
        if (entity != null) {
            return entity.getBody();
        }
        return null;
    }

    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public GiftDTO giftFallback(Long subjectId, ActivityType type) {
        final GiftDTO dto = new GiftDTO();
        dto.setName("降级处理礼物");
        return dto;
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public GiftDTO exceptionHandler(Long subjectId, ActivityType type, BlockException ex) {
        System.err.println("==============溶断处理：" + ex.getMessage());
        final GiftDTO dto = new GiftDTO();
        dto.setName("异常处理礼物");
        return dto;
    }

}
