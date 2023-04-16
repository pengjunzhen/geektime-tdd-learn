package org.example.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Args {

    public static <T> T parse(Class<T> optionsClass, String... args) {
        Constructor<?> declaredConstructor = optionsClass.getDeclaredConstructors()[0];
        try {
            return (T) declaredConstructor.newInstance(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
