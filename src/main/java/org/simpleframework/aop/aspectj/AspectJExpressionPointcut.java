package org.simpleframework.aop.aspectj;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParameter;
import org.aspectj.weaver.tools.PointcutParser;
import org.simpleframework.aop.ClassFilter;
import org.simpleframework.aop.MethodMatcher;
import org.simpleframework.aop.Pointcut;
import org.simpleframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-27 12:23:49
 */
public class AspectJExpressionPointcut implements ClassFilter, MethodMatcher, Pointcut {

    private String location;
    private String expression;
    private final PointcutExpression pointcutExpression;

    public AspectJExpressionPointcut(Class<?> declarationScope, String expression) {
        PointcutParser pointcutParser = PointcutParser
                .getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
                        PointcutParser.getAllSupportedPointcutPrimitives(),
                        ClassUtils.getDefaultClassLoader()
                );
        this.pointcutExpression = pointcutParser.parsePointcutExpression(expression, declarationScope, new PointcutParameter[]{});
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return this.expression;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
