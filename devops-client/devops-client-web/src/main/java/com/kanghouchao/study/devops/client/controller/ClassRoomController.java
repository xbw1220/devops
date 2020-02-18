package com.kanghouchao.study.devops.client.controller;

import com.kanghouchao.study.devops.client.manage.GiftManage;
import com.kanghouchao.study.devops.client.vo.GiftVO;
import com.kanghouchao.study.devops.server.api.dto.GiftDTO;
import com.kanghouchao.study.devops.server.api.enums.ActivityType;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
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
@AllArgsConstructor
public class ClassRoomController {

    private GiftManage giftManage;

    @GetMapping("gift")
    public ResponseEntity<GiftVO> get(ActivityType type, Long subjectId) {
        final GiftDTO dto = giftManage.get(type, subjectId);
        final GiftVO vo = new GiftVO();
        BeanUtils.copyProperties(dto, vo);
        return ResponseEntity.ok(vo);
    }

}

