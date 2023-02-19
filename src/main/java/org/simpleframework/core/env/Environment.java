package org.simpleframework.core.env;

import java.util.List;

/**
 * <h1>环境接口</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-02-19 13:28:36
 */
public interface Environment extends PropertyResolver {

    List<PropertySource<?>> getPropertySources();
}
