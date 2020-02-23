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
        System.out.println("=== 调用原始方法 ===");
        ResponseEntity<String> entity = giftFeignClient.clusterFlow();
        if (entity != null) {
            return entity.getBody();
        }
        return null;
    }

    /**
     * <p>
     * Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
     * 当调用原始方法报错时，会调用此方法，此时会先调用原始方法
     * </P>
     */
    public String giftFallback() {
        System.out.println("=== 调用降级处理方法 ===");
        return "降级处理";
    }

    /**
     * <p>
     * Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
     * 当原始方法调用达到限流设置的阈值时，才调用此方法,此时原始方法不会被调用
     * </p>
     *
     * @param ex
     * @return
     */
    public String exceptionHandler(BlockException ex) {
        System.out.println("=== 调用限流处理方法 ===");
        return "限流处理";
    }

}
