package org.simpleframework.context.support;

/**
 * <h1>提供可以配置的能力</h1>
 *
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-16 12:00:36
 */
public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext {

    private String[] configLocations;

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    public void setConfigLocation(String location) {
        setConfigLocations(location);
    }

    public void setConfigLocations(String... locations) {
        if (locations == null || locations.length == 0) {
            throw new IllegalArgumentException("Config locations must not be null");
        }

        this.configLocations = new String[locations.length];
        for (int i = 0; i < locations.length; i++) {
            this.configLocations[i] = locations[i].trim();
        }

    }

    protected String[] getConfigLocations() {
        return (this.configLocations != null ? this.configLocations : getDefaultConfigLocations());
    }

    protected String[] getDefaultConfigLocations() {
        return null;
    }
}
