package com.kanghouchao.study.devops.client.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class SentinelAspectConfiguration {

    private static final String APP_NAME = AppNameUtil.getAppName();

    private static final String REMOTE_ADDRESS = "nacos";
    private static final String GROUP_ID = "SENTINEL_GROUP";

    private static final String FLOW_POSTFIX = "-flow-rules";

    private static final String CLUSTER_SERVER_HOST = "sentinel-token-server";
    private static final int CLUSTER_SERVER_PORT = 11111;
    private static final int REQUEST_TIME_OUT = 200;

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @PostConstruct
    public void load() {
        loadClusterClientConfig();
        registerClusterClientProperty();
        registerClusterFlowRuleProperty();
        System.out.println("----------------------SentinelAspectConfiguration.load----------------------------");
    }

    /**
     * 加载集群客户端配置
     * 主要是集群服务端的相关连接信息
     */
    private void loadClusterClientConfig() {
        ClusterClientAssignConfig assignConfig = new ClusterClientAssignConfig();
        assignConfig.setServerHost(CLUSTER_SERVER_HOST);
        assignConfig.setServerPort(CLUSTER_SERVER_PORT);
        ClusterClientConfigManager.applyNewAssignConfig(assignConfig);

        ClusterClientConfig clientConfig = new ClusterClientConfig();
        clientConfig.setRequestTimeout(REQUEST_TIME_OUT);
        ClusterClientConfigManager.applyNewConfig(clientConfig);
        ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);
    }

    /**
     * 为ClusterClientConfig注册一个SentinelProperty
     * 这样的话可以动态的更改这些配置
     */
    private void registerClusterClientProperty() {
        String clientConfigDataId = "cluster-client-config";
        // 初始化一个配置ClusterClientConfig的 Nacos 数据源
        ReadableDataSource<String, ClusterClientConfig> clientConfigDS = new NacosDataSource<>(REMOTE_ADDRESS, GROUP_ID, clientConfigDataId,
                source -> JSON.parseObject(source, new TypeReference<ClusterClientConfig>() {
                }));
        ClusterClientConfigManager.registerClientConfigProperty(clientConfigDS.getProperty());

        String clientAssignConfigDataId = "cluster-client-assign-config";
        // 初始化一个配置ClusterClientAssignConfig的 Nacos 数据源
        ReadableDataSource<String, ClusterClientAssignConfig> clientAssignConfigDS = new NacosDataSource<>(REMOTE_ADDRESS, GROUP_ID, clientAssignConfigDataId,
                source -> JSON.parseObject(source, new TypeReference<ClusterClientAssignConfig>() {
                }));
        ClusterClientConfigManager.registerServerAssignProperty(clientAssignConfigDS.getProperty());

    }

    /**
     * 注册动态规则Property
     * 当client与Server连接中断，退化为本地限流时需要用到的该规则
     */
    private void registerClusterFlowRuleProperty() {
        // 使用 Nacos 数据源作为配置中心，需要在 REMOTE_ADDRESS 上启动一个 Nacos 的服务
        ReadableDataSource<String, List<FlowRule>> ds = new NacosDataSource<>(REMOTE_ADDRESS, GROUP_ID, APP_NAME + FLOW_POSTFIX,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                }));
        // 为集群客户端注册动态规则源
        FlowRuleManager.register2Property(ds.getProperty());
    }

}
