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
package com.kanghouchao.study.devops.server.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Lurker
 * <p>
 * 日期： 2019/10/19
 * 联系方式: hchkang8710@gmail.com
 * <p/>
 */
@Data
public class GiftVO {

    private Long id;

    private Long subjectId;

    private BigDecimal price;

    private String name;

}
