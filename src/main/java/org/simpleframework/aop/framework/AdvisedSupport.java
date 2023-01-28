package org.simpleframework.aop.framework;

import org.simpleframework.aop.Advisor;
import org.simpleframework.aop.TargetSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 19:58:35
 */
public class AdvisedSupport {

    private TargetSource targetSource;
    private List<Advisor> advisors = new ArrayList<>();

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public List<Advisor> getAdvisors() {
        return advisors;
    }

    public void setAdvisors(List<Advisor> advisors) {
        this.advisors = advisors;
    }
}
