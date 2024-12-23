package utils;

import java.util.HashMap;

public class ClassList {
    private static ClassList instance;
    HashMap<Class, Object> map = new HashMap<>();

    public static ClassList getInstance() {
        if (instance == null) {
            instance = new ClassList();

        }
        return instance;
    }

    public void put(Object object) {
        map.put(object.getClass(), object);
    }

    public <T> T get(Class<T> c) {
        if (map.get(c) == null) {
            try {
                map.put(c, c.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (T) map.get(c);
    }
}
