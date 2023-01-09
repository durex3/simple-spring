package org.simpleframework.beans;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * <h1>包含一个或多个{@link PropertyValue}对象的 Holder</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-09 18:53:13
 */
public interface PropertyValues extends Iterable<PropertyValue> {

    PropertyValue[] getPropertyValues();

    /**
     * <h2>在属性值上返回一个{@link Iterable}.</h2>
     *
     * @return {@link Iterator<PropertyValue>}
     */
    @Override
    default Iterator<PropertyValue> iterator() {
        return Arrays.asList(getPropertyValues()).iterator();
    }

    /**
     * <h2>返回属性值上的{@link Spliterator}</h2>
     *
     * @return {@link Spliterator<PropertyValue>}
     */
    @Override
    default Spliterator<PropertyValue> spliterator() {
        return Spliterators.spliterator(getPropertyValues(), 0);
    }

    /**
     * <h2>返回一个包含属性值的{@link Stream}</h2>
     *
     * @return {@link Stream<PropertyValue>}
     */
    default Stream<PropertyValue> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * <h2>返回具有给定名称的属性值</h2>
     *
     * @param propertyName 属性名
     * @return 属性值
     */
    PropertyValue getPropertyValue(String propertyName);

    /**
     * <h2>是否有此属性的属性值</h2>
     *
     * @param propertyName 属性名
     */
    boolean contains(String propertyName);

    /**
     * <h2>判断是否为空</h2>
     */
    boolean isEmpty();

}
