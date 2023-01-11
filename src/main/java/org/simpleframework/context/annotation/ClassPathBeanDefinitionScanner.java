package org.simpleframework.context.annotation;

import org.simpleframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.simpleframework.beans.factory.config.BeanDefinition;
import org.simpleframework.beans.factory.support.BeanDefinitionRegistry;
import org.simpleframework.core.type.AnnotationMetadata;
import org.simpleframework.util.ClassUtils;

import java.beans.Introspector;
import java.util.Map;
import java.util.Set;

/**
 * <h1>类路径注解方式 bean 定义信息扫描器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-10 19:25:14
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private final BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public int scan(String... basePackages) {
        int beanCountAtScanStart = this.registry.getBeanDefinitionCount();
        doScan(basePackages);
        return (this.registry.getBeanDefinitionCount() - beanCountAtScanStart);
    }

    protected void doScan(String... basePackages) {
        if (basePackages == null || basePackages.length == 0) {
            throw new IllegalArgumentException("At least one base package must be specified");
        }

        for (String basePackage : basePackages) {
            Set<BeanDefinition> beanDefinitions = findCandidateComponents(basePackage);
            beanDefinitions.forEach(bd -> this.registry.registerBeanDefinition(determineBeanNameFromAnnotation(bd), bd));
        }
    }

    protected String determineBeanNameFromAnnotation(BeanDefinition annotatedDef) {
        if (annotatedDef instanceof AnnotatedBeanDefinition) {
            AnnotationMetadata metadata = ((AnnotatedBeanDefinition) annotatedDef).getMetadata();
            Set<String> types = metadata.getAnnotationTypes();

            String beanName = null;
            for (String type : types) {
                if (isStereotypeWithNameValue(type)) {
                    Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(type);
                    beanName = annotationAttributes.get("value").toString();
                    if (beanName.isEmpty()) {
                        String shortClassName = ClassUtils.getShortName(annotatedDef.getBeanClassName());
                        beanName = Introspector.decapitalize(shortClassName);
                    }
                }
            }
            return beanName;
        } else {
            String shortClassName = ClassUtils.getShortName(annotatedDef.getBeanClassName());
            return Introspector.decapitalize(shortClassName);
        }
    }
}
