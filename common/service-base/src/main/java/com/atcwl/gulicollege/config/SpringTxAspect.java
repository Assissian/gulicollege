package com.atcwl.gulicollege.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Aspect
//@Configuration
public class SpringTxAspect {
    /**
     * 切面,根据自己的项目定义不同的表达式execution
     **/
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.atcwl.gulicollege.service.impl.*.*(..))";

    @Resource
    private TransactionManager transactionManager;

    /**
     * 增强(事务)的属性的配置
     *
     * @param
     * @return TransactionInterceptor
     * @title: txAdvice
     * @author lanys 2022-11-02
     * @Description: 配置
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Bean
    public TransactionInterceptor txAdvice() {
        NameMatchTransactionAttributeSource txAttributeS = new NameMatchTransactionAttributeSource();
        RuleBasedTransactionAttribute requiredAttr = new RuleBasedTransactionAttribute();
        // PROPAGATION_REQUIRED:如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中
        requiredAttr.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 抛出异常后执行切点回滚
        requiredAttr.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        //
        RuleBasedTransactionAttribute supportsAttr = new RuleBasedTransactionAttribute();
        // PROPAGATION_SUPPORTS:支持当前事务，如果当前没有事务，就以非事务方式执行
        supportsAttr.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        // 只读事务，不做更新操作
        supportsAttr.setReadOnly(true);

        // 注意：方法名称来自类匹配的到方法【save*, “*”表示匹配任意個字符】
        Map txMethod = new HashMap();
        txMethod.put("insert*", requiredAttr);
        txMethod.put("add*", requiredAttr);
        txMethod.put("update*", requiredAttr);
        txMethod.put("modify*", requiredAttr);
        txMethod.put("remove*", requiredAttr);
        txMethod.put("delete*", requiredAttr);
        txMethod.put("bind*", requiredAttr);
        txMethod.put("unbind*", requiredAttr);
        // readOnly = true
        txMethod.put("select*", supportsAttr);
        txMethod.put("get*", supportsAttr);
        txMethod.put("find*", supportsAttr);
        txMethod.put("query*", supportsAttr);
        txMethod.put("read*", supportsAttr);
        txMethod.put("check*", supportsAttr);
        //
        txAttributeS.setNameMap(txMethod);
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor(transactionManager, txAttributeS);
        return transactionInterceptor;
    }

    /**
     * AOP配置定义切面和切点的信息
     *
     * @return Advisor
     * @title: txAdviceAdvisor
     * @author lanys 2022-11-02
     * @Description: AdvisorBean
     * 这样配置方便异常回滚
     */
    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
