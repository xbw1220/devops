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
package com.kanghouchao.study.devops.server.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;


/**
 * @author Lurker
 * @since 2019/12/20
 * <p>
 * 创建日期： 2019/12/20
 * 联系方式: hchkang8710@gmail.com
 * <p/>
 */
@Slf4j
public class RedisLock implements Closeable {
    private RedisTemplate redisTemplate;
    private String lockKey;
    private String lockValue;
    private int expireTime;

    public RedisLock(RedisTemplate redisTemplate, String lockKey, String lockValue, int expireTime) {
        this.redisTemplate = redisTemplate;
        //redis key
        this.lockKey = lockKey;
        //redis value
        this.lockValue = lockValue;
        //过期时间 单位：s
        this.expireTime = expireTime;
    }

    /**
     * 获取分布式锁
     */
    public boolean getLock() {
        //获取锁的操作
        return (boolean) redisTemplate.execute((RedisCallback) connection -> {
            //过期时间 单位：s
            Expiration expiration = Expiration.seconds(expireTime);
            //执行NX操作
            SetOption setOption = SetOption.ifAbsent();
            //序列化key
            byte[] serializeKey = redisTemplate.getKeySerializer().serialize(lockKey);
            //序列化value
            byte[] serializeVal = redisTemplate.getValueSerializer().serialize(lockValue);
            //获取锁
            return connection.set(serializeKey, serializeVal, expiration, setOption);
        });
    }

    public void tryGetLock() {
        while (true) {
            if (getLock()) {
                return;
            }
        }
    }

    /**
     * 自动释放锁
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        //释放锁的lua脚本
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        RedisScript<String> redisScript = RedisScript.of(script, Boolean.class);
        //是否redis锁
        redisTemplate.execute(redisScript, Arrays.asList(lockKey), lockValue);
    }
}
