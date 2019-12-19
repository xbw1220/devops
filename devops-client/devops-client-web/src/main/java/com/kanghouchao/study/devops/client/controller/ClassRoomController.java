package com.kanghouchao.study.devops.client.controller;

import com.kanghouchao.study.devops.server.api.client.GiftFeignClient;
import com.kanghouchao.study.devops.server.api.dto.GiftDTO;
import com.kanghouchao.study.devops.server.api.enums.ActivityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lurker
 * @since 2019/12/18
 * <p>
 * 创建日期： 2019/12/18
 * 联系方式: hchkang8710@gmail.com
 * <p/>
 */
@RestController
public class ClassRoomController {

    @Autowired
    private GiftFeignClient giftFeignClient;

    @GetMapping("gift")
    public ResponseEntity<String> get(ActivityType type, Long subjectId) {
        ResponseEntity<GiftDTO> entity = giftFeignClient.get(type, subjectId);
        assert entity.getBody() != null;
        return ResponseEntity.ok(entity.getBody().getName());
    }

}
