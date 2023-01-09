package org.simpleframework.beans;

/**
 * <h1>对象来保存单个bean属性的信息和值</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-09 18:50:00
 */
public class PropertyValue {

    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public PropertyValue(PropertyValue original) {
        this.name = original.getName();
        this.value = original.getValue();
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
