package com.alibaba.csp.sentinel.dashboard.rule.nacos.system;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.util.NacosConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: rhb
 * @date: 2023/10/28 2:00
 * @description: 系统规则
 */
@Component("systemRuleNacosPublisher")
public class SystemRuleNacosPublisher implements DynamicRulePublisher<List<SystemRuleEntity>> {

    @Autowired
    private ConfigService configService;

    @Autowired
    private Converter<List<SystemRuleEntity>, String> converter;

    @Override
    public void publish(String app, List<SystemRuleEntity> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");
        if (rules == null) {
            return;
        }
        configService.publishConfig(app + NacosConfigUtil.SYSTEM_DATA_ID_POSTFIX,
                NacosConfigUtil.GROUP_ID, converter.convert(rules), ConfigType.JSON.getType());
    }
}
