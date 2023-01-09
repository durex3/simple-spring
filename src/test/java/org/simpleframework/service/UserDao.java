package org.simpleframework.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liugelong
 * @version 1.0
 * @since 1.0 2023-01-09 21:06:00
 */
public class UserDao {

    private final Map<String, String> map = new HashMap<>();

    public UserDao() {
        map.put("Jack", "jack");
        map.put("Anni", "anni");
        map.put("Joan", "joan");
    }

    public List<String> findAllUsername() {
        return map.values().stream().toList();
    }
}
