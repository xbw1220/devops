package com.kanghouchao.study.devops.client.manage.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.kanghouchao.study.devops.client.manage.ClusterFlowManage;
import com.kanghouchao.study.devops.server.api.client.GiftFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClusterFlowManageImpl implements ClusterFlowManage {

    private GiftFeignClient giftFeignClient;


    @Override
    @SentinelResource(value = "cluster_flow", blockHandler = "exceptionHandler", fallback = "giftFallback")
    public String clusterFlow() {
        ResponseEntity<String> entity = giftFeignClient.clusterFlow();
        if (entity != null) {
            return entity.getBody();
        }
        return null;
    }

    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String giftFallback() {
        return "降级处理";
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionHandler(BlockException ex) {
        return "限流处理";
    }

}
