package com.kanghouchao.study.devops.client.manage;

import com.kanghouchao.study.devops.server.api.dto.GiftDTO;
import com.kanghouchao.study.devops.server.api.enums.ActivityType;

public interface GiftManage {

    GiftDTO get(ActivityType type, Long subjectId);
}
