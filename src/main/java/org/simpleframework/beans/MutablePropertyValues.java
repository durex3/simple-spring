package org.simpleframework.beans;

import java.util.*;
import java.util.stream.Stream;

/**
 * <h1>{@link PropertyValues}接口的默认实现。允许对属性进行简单操作，并提供构造函数来支持进行深拷贝。</h1>
 * <p> PropertyValues 类似 Map一样 </p>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-09 19:05:37
 */
public class MutablePropertyValues implements PropertyValues {

    private final List<PropertyValue> propertyValueList;

    public MutablePropertyValues() {
        this.propertyValueList = new ArrayList<>(0);
    }

    public MutablePropertyValues(PropertyValues original) {
        if (original != null) {
            PropertyValue[] pvs = original.getPropertyValues();
            this.propertyValueList = new ArrayList<>(pvs.length);
            Arrays.stream(pvs).forEach(pv -> this.propertyValueList.add(new PropertyValue(pv)));
        } else {
            this.propertyValueList = new ArrayList<>(0);
        }
    }

    public MutablePropertyValues(List<PropertyValue> propertyValueList) {
        this.propertyValueList = (propertyValueList != null ? propertyValueList : new ArrayList<>());
    }

    @Override
    public Iterator<PropertyValue> iterator() {
        return Collections.unmodifiableList(this.propertyValueList).iterator();
    }

    @Override
    public Spliterator<PropertyValue> spliterator() {
        return Spliterators.spliterator(this.propertyValueList, 0);
    }

    @Override
    public Stream<PropertyValue> stream() {
        return this.propertyValueList.stream();
    }

    @Override
    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    @Override
    public PropertyValue getPropertyValue(String propertyName) {
        return this.propertyValueList.stream().filter(pv -> pv.getName().equals(propertyName)).findFirst().orElse(null);
    }

    @Override
    public boolean contains(String propertyName) {
        return getPropertyValue(propertyName) != null;
    }

    @Override
    public boolean isEmpty() {
        return this.propertyValueList.isEmpty();
    }

    public int size() {
        return this.propertyValueList.size();
    }

    /**
     * <h2>添加一个 PropertyValues 对象</h2>
     *
     * @param other 添加的 PropertyValues 对象
     * @return {@link MutablePropertyValues}
     */
    public MutablePropertyValues addPropertyValues(PropertyValues other) {
        if (other != null) {
            PropertyValue[] pvs = other.getPropertyValues();
            for (PropertyValue pv : pvs) {
                addPropertyValue(new PropertyValue(pv));
            }
        }
        return this;
    }

    /**
     * <h2>添加一个 PropertyValue 对象</h2>
     *
     * @param pv 添加的 PropertyValue 对象
     * @return {@link MutablePropertyValues}
     */
    public MutablePropertyValues addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
        return this;
    }

    /**
     * <h2>添加一组属性</h2>
     *
     * @param propertyName  属性名
     * @param propertyValue 属性值
     */
    public void addPropertyValue(String propertyName, Object propertyValue) {
        addPropertyValue(new PropertyValue(propertyName, propertyValue));
    }

    /**
     * <h2>添加一组属性</h2>
     *
     * @param propertyName  属性名
     * @param propertyValue 属性值
     * @return {@link MutablePropertyValues}
     */
    public MutablePropertyValues add(String propertyName, Object propertyValue) {
        addPropertyValue(new PropertyValue(propertyName, propertyValue));
        return this;
    }

    /**
     * <h2>移除属性</h2>
     *
     * @param propertyName 属性名
     */
    public void removePropertyValue(String propertyName) {
        this.propertyValueList.remove(getPropertyValue(propertyName));
    }

    /**
     * <h2>移除 PropertyValue 对象</h2>
     *
     * @param pv 移除的 PropertyValue 对象
     */
    public void removePropertyValue(PropertyValue pv) {
        this.propertyValueList.remove(pv);
    }

    /**
     * <h2>获取 PropertyValue 的属性值</h2>
     *
     * @param propertyName 属性名
     * @return 属性值
     */
    public Object get(String propertyName) {
        PropertyValue pv = getPropertyValue(propertyName);
        return (pv != null ? pv.getValue() : null);
    }
}
