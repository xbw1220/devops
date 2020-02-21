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
package com.kanghouchao.study.devops.server.api.enums;

import javax.xml.crypto.Data;

/**
 * @author Lurker
 * <p>
 * 日期： 2019/10/19
 * 联系方式: hchkang8710@gmail.com
 * <p/>
 */
public enum ActivityType {

    /**
     * 普通活动
     */
    Ordinary(0),
    /**
     * 夏日满减
     */
    SummerBuyDay(1),
    /**
     * 双十一
     */
    DoubleEleven(2);

    private int code;

    ActivityType(int code) {
        this.code = code;
    }

    public static ActivityType getByCode(int code) {
        switch (code) {
            case 1:
                return SummerBuyDay;
            case 2:
                return DoubleEleven;
            default:
                return Ordinary;
        }
    }

}
