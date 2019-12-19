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
package com.kanghouchao.study.devops.server.api.client;

import com.kanghouchao.study.devops.server.api.dto.GiftDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import com.kanghouchao.study.devops.server.api.enums.ActivityType;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Lurker
 * @since 2019/12/18
 * <p>
 * 创建日期： 2019/12/18
 * 联系方式: hchkang8710@gmail.com
 * <p/>
 */
@Validated
public interface GiftAPI {

    /**
     * 根据类型和ID获取数据
     *
     * @param type      类型
     * @param subjectId 项目ID
     * @return 礼物
     */
    @GetMapping("get")
    ResponseEntity<GiftDTO> get(@RequestParam("type") ActivityType type, @RequestParam("subject_id") Long subjectId);

}
