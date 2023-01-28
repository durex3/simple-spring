package org.simpleframework.aop.framework.autoproxy;

import org.simpleframework.aop.Advisor;
import org.simpleframework.beans.factory.BeanFactory;
import org.simpleframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h1>切面代理创建器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 11:08:50
 */
public class AspectJAwareAdvisorAutoProxyCreator extends AbstractAutoProxyCreator {

    private ConfigurableListableBeanFactory beanFactory;

    protected List<Advisor> findCandidateAdvisors() {
        Map<String, Advisor> beansOfType = beanFactory.getBeansOfType(Advisor.class);
        if (beansOfType != null && beansOfType.size() > 0) {
            return new ArrayList<>(beansOfType.values());
        }
        return new ArrayList<>();
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        this.beanFactory = ((ConfigurableListableBeanFactory) beanFactory);
    }

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }
}
