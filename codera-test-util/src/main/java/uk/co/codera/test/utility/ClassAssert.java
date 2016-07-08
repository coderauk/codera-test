package uk.co.codera.test.utility;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public final class ClassAssert {

    private ClassAssert() {
        super();
    }

    public static void assertStaticUtilityClass(Class<?> clazz) {
        assertClassIfFinal(clazz);
        assertSingleNoArgsPrivateConstructor(clazz);
    }

    private static void assertClassIfFinal(Class<?> clazz) {
        int modifiers = clazz.getModifiers();
        assertThat("Static utility class should be final", Modifier.isFinal(modifiers), is(true));
    }

    private static void assertSingleNoArgsPrivateConstructor(Class<?> clazz) {
        Constructor<?>[] allConstructors = clazz.getDeclaredConstructors();

        if (allConstructors.length > 1) {
            failDueToLackOfSingleNoArgsPrivateConstructor();
        }

        Constructor<?> constructor = allConstructors[0];

        if (constructor.getParameterTypes().length > 0) {
            failDueToLackOfSingleNoArgsPrivateConstructor();
        }

        if (!Modifier.isPrivate(constructor.getModifiers())) {
            failDueToLackOfSingleNoArgsPrivateConstructor();
        }

        instantiateForCoverage(constructor);
    }

    private static void instantiateForCoverage(Constructor<?> constructor) throws AssertionError {
        try {
            constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new AssertionError("Unable to instantiate class");
        }
    }

    private static void failDueToLackOfSingleNoArgsPrivateConstructor() {
        fail("Static utility class should have single no-args private constructor");
    }
}