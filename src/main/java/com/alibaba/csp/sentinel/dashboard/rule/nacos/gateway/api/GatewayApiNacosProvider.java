package com.alibaba.csp.sentinel.dashboard.rule.nacos.gateway.api;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.util.NacosConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 拉取Nacos中存储的网关分组管理规则配置信息
 *
 * @date: 2023/10/28 1:21
 * @description:
 */
@Component("gatewayApiNacosProvider")
public class GatewayApiNacosProvider implements DynamicRuleProvider<List<ApiDefinitionEntity>> {
    @Autowired
    private ConfigService configService;

    @Autowired
    private Converter<String, List<ApiDefinitionEntity>> converter;

    @Override
    public List<ApiDefinitionEntity> getRules(String appName) throws Exception {
        // appName + '-gw-api-group-rules'
        String rules = configService.getConfig(appName + NacosConfigUtil.GATEWAY_API_DATA_ID_POSTFIX, NacosConfigUtil.GROUP_ID, 3000);
        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }
        return converter.convert(rules);
    }
}