package uk.co.codera.test.utility;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public final class ClassAssert {

    private ClassAssert() {
        super();
    }

    public static void assertStaticUtilityClass(Class<?> clazz) {
        int modifiers = clazz.getModifiers();
        assertThat("Static utility class should be final", Modifier.isFinal(modifiers), is(true));
        assertNoPublicConstructors(clazz);
        Constructor<?>[] allConstructors = clazz.getDeclaredConstructors();
        if (allConstructors.length > 1) {
            failDueToLackOfSingleNoArgsPrivateConstructor();
        }
        Constructor<?> constructor = allConstructors[0];
        assertNoArgs(constructor);
        assertPrivate(constructor);
    }

    private static void assertPrivate(Constructor<?> constructor) {
        if (!Modifier.isPrivate(constructor.getModifiers())) {
            failDueToLackOfSingleNoArgsPrivateConstructor();
        }
    }

    private static void assertNoArgs(Constructor<?> constructor) {
        if (constructor.getParameterTypes().length > 0) {
            failDueToLackOfSingleNoArgsPrivateConstructor();
        }
    }

    private static void assertNoPublicConstructors(Class<?> clazz) {
        if (clazz.getConstructors().length > 0) {
            failDueToLackOfSingleNoArgsPrivateConstructor();
        }
    }

    private static void failDueToLackOfSingleNoArgsPrivateConstructor() {
        fail("Static utility class should have single no-args private constructor");
    }
}