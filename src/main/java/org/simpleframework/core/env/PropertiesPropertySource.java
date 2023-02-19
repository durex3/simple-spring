package org.simpleframework.core.env;

import java.util.Map;
import java.util.Properties;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-19 12:50:04
 */
public class PropertiesPropertySource extends PropertySource<Map<String, Object>> {

    public PropertiesPropertySource(String name, Properties source) {
        super(name, (Map) source);
    }

    @Override
    public Object getProperty(String name) {
        return getSource().get(name);
    }
}
