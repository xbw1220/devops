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
package com.kanghouchao.study.devops.client;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lurker
 * <p>
 * 日期： 2019/11/9
 * 联系方式: hchkang8710@gmail.com
 * <p/>
 */
@RunWith(SpringRunner.class)
@Slf4j
public class TestController {

    private static final String URL = "http://127.0.0.1/lib/api/xmlrpc/v1/xmlrpc.php";
    private final static String key = "1c94d5d9cce739600c6b030a810bd1c8";

    private TestLinkAPI api = null;

    @Before
    public void createApi() throws MalformedURLException {
        api = new TestLinkAPI(new URL(URL), key);
    }

    @Test
    public void testPing() {
        Assert.assertEquals("不相等", api.ping(), "Hello!");
    }

    @Test
    public void testGetTestCase() {
        TestCase testCase = api.getTestCaseByExternalId("hess-1", 1);
        log.info("testCase is {}", testCase);
        Assert.assertNotNull(testCase);
    }

    @Test
    public void testReportTCResult() {
        TestPlan plan = api.getTestPlanByName("Test Plan 1", "Hess");
        Build build = api.createBuild(plan.getId(), "Version 2", "<p>Version 2</p>");
        TestCase testCase = api.getTestCaseByExternalId("hess-1", 1);
        List<TestCaseStepResult> results = testCase.getSteps().stream().map(testCaseStep -> {
            log.debug("testCaseStep is {}", testCaseStep);
            TestCaseStepResult result = new TestCaseStepResult();
            result.setNotes("执行成功");
            result.setNumber(testCaseStep.getNumber());
            result.setResult(ExecutionStatus.PASSED);
            return result;
        }).collect(Collectors.toList());
        ReportTCResultResponse response = api.reportTCResult(testCase.getId(),
                testCase.getExternalId(),
                plan.getId(),
                ExecutionStatus.PASSED,
                results,
                build.getId(),
                build.getName(),
                "测试通过",
                10,
                true,
                "",
                0,
                null,
                null,
                true,
                "user",
                "");
        log.debug("response : {}", response);
        Assert.assertNotNull(response);
    }

}
