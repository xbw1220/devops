package com.kanghouchao.study.devops.client.controller;

import com.kanghouchao.study.devops.client.manage.ClusterFlowManage;
import lombok.AllArgsConstructor;
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
public class ClusterFlowController {

    private ClusterFlowManage clusterFlowManage;

    @GetMapping(value = "cluster_flow")
    public ResponseEntity<String> clusterFlow() {
        return ResponseEntity.ok(clusterFlowManage.clusterFlow());
    }

}

