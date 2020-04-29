package com.nacos.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class SentinelConfig implements ApplicationRunner {
    // 限流规则名称
    private static final String GETORDER_KEY = "orderToMember";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<FlowRule> rules = new ArrayList<FlowRule>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource(GETORDER_KEY);
        // QPS控制在1
        rule1.setCount(1);
        // QPS限流
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setLimitApp("default");
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
        System.out.println(">>>限流配置加载成功<<<");

    }
}
