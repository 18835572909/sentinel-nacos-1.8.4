package com.alibaba.csp.sentinel.dashboard.rule.nacos.authority;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
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
 * @author: rhb
 * @date: 2023/10/28 1:54
 * @description: 访问控制规则类
 */
@Component("authorityRuleNacosProvider")
public class AuthorityRuleNacosProvider implements DynamicRuleProvider<List<AuthorityRuleEntity>> {

    @Autowired
    private ConfigService configService;

    @Autowired
    private Converter<String, List<AuthorityRuleEntity>> converter;

    @Override
    public List<AuthorityRuleEntity> getRules(String appName) throws Exception {
        String rules = configService.getConfig(appName + NacosConfigUtil.AUTHORITY_DATA_ID_POSTFIX,
                NacosConfigUtil.GROUP_ID, 3000);
        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }
        return converter.convert(rules);
    }
}
