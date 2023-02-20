package org.simpleframework.core.env;

/**
 * <h1>抽象的资源映射器</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-19 13:37:38
 */
public abstract class AbstractPropertyResolver implements PropertyResolver {

    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    @Override
    public boolean containsProperty(String key) {
        return (getProperty(key) != null);
    }

    @Override
    public String getProperty(String key) {
        return null;
    }

    @Override
    public String resolvePlaceholders(String text) {
        return doResolvePlaceholders(text);
    }

    private String doResolvePlaceholders(String text) {
        if (!text.contains(DEFAULT_PLACEHOLDER_PREFIX) || !text.contains(DEFAULT_PLACEHOLDER_SUFFIX)) {
            return text;
        }
        String propVal = null;
        int startIndex = text.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        int endIndex = text.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
        if (startIndex < endIndex) {
            String placeholder = text.substring(startIndex + DEFAULT_PLACEHOLDER_PREFIX.length(), endIndex);
            propVal = getProperty(placeholder);
            if (propVal == null) {
                propVal = placeholder;
            }
        }
        return propVal;
    }
}
