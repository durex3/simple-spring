package org.simpleframework.core.env;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-18 13:09:10
 */
public abstract class PropertySource<T> {

    protected final String name;

    protected final T source;

    public PropertySource(String name, T source) {
        this.name = name;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public T getSource() {
        return source;
    }

    public abstract Object getProperty(String name);

    public boolean containsProperty(String name) {
        return (getProperty(name) != null);
    }
}
