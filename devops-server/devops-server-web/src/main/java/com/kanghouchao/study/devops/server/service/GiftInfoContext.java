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

import com.kanghouchao.study.devops.server.api.enums.ActivityType;
import com.kanghouchao.study.devops.server.vo.GiftVO;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLockReactive;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Lurker
 * <p>
 * 日期： 2019/10/19
 * 联系方式: hchkang8710@gmail.com
 * <p/>
 */
@Component
public class GiftInfoContext {

    @Resource
    private List<IGiftInfoStrategyService> services;

    @Autowired
    private RedissonClient redissonClient;

    @Value("${max.thread.count:80}")
    private Long maxThreadCount;

    /**
     * 对外暴露的统一获取礼品信息的返回
     *
     * @param subjectId 项目编号
     * @param type      活动类型
     * @return 礼品信息
     */
    public GiftVO getGiftInfo(Long subjectId, ActivityType type) {
        RedissonReactiveClient reactive = Redisson.createReactive(redissonClient.getConfig());
        RLockReactive lock = reactive.getLock(subjectId.toString());
        Mono<Boolean> lockRes = lock.tryLock(100, 10, TimeUnit.SECONDS);
        lockRes.doOnNext(res -> {
            RAtomicLong atomicLong = redissonClient.getAtomicLong("myAtomicLong");
            if (!atomicLong.isExists()) {
                atomicLong.set(maxThreadCount);
            }
            if (atomicLong.get() > 0) {
                System.out.println("I'm get Lock,I'm doing my work and I want told you the subject is " + atomicLong.decrementAndGet());
            }
        }).doFinally(res -> lock.unlock()).subscribe();
        IGiftInfoStrategyService service = services.stream().filter(
                s -> s.getType().equals(type)
        ).findFirst().orElseThrow(() -> new RuntimeException("对此类型没有业务实现"));
        return service.get(subjectId);
    }

}
