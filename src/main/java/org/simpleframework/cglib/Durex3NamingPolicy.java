package org.simpleframework.cglib;

import net.sf.cglib.core.DefaultNamingPolicy;

/**
 * <h1>CGLIB 的 DefaultNamingPolicy 的自定义扩展，将生成的类名中的标记从 “ByCGLIB” 修改为 “ByDurex3CGLIB”</h1>
 *
 * @Author: liugelong
 * @createTime: 2022-12-31 22:40:03
 * @version: 1.0
 */
public class Durex3NamingPolicy extends DefaultNamingPolicy {

    public static final Durex3NamingPolicy INSTANCE = new Durex3NamingPolicy();

    @Override
    protected String getTag() {
        return "ByDurex3CGLIB";
    }
}
