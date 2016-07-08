package uk.co.codera.test.utility;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ClassAssertTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldPassValidStaticUtilityClass() {
        ClassAssert.assertStaticUtilityClass(ClassAssert.class);
    }

    @Test
    public void shouldFailStaticUtilityClassThatIsNotFinal() {
        expectedAssertionError("Static utility class should be final");
        ClassAssert.assertStaticUtilityClass(NonFinalUtilityClass.class);
    }

    @Test
    public void shouldFailStaticUtilityClassThatHasPublicConstructor() {
        expectAssertionErrorDueToLackOfSinglePrivateConstructor();
        ClassAssert.assertStaticUtilityClass(PublicConstructorClass.class);
    }

    @Test
    public void shouldFailStaticUtilityClassThatHasProtectedConstructor() {
        expectAssertionErrorDueToLackOfSinglePrivateConstructor();
        ClassAssert.assertStaticUtilityClass(ProtectedConstructorClass.class);
    }

    @Test
    public void shouldFailStaticUtilityClassThatHasDefaultConstructor() {
        expectAssertionErrorDueToLackOfSinglePrivateConstructor();
        ClassAssert.assertStaticUtilityClass(DefaultConstructorClass.class);
    }

    @Test
    public void shouldFailStaticUtilityClassThatHasMixtureOfConstructors() {
        expectAssertionErrorDueToLackOfSinglePrivateConstructor();
        ClassAssert.assertStaticUtilityClass(MixtureOfConstructorsClass.class);
    }

    @Test
    public void shouldFailStaticUtilityClassThatHasPrivateConstructorWithArgs() {
        expectAssertionErrorDueToLackOfSinglePrivateConstructor();
        ClassAssert.assertStaticUtilityClass(PrivateConstructorWithArgsClass.class);
    }

    @Test
    public void shoulFailStaticUtilityClassThatCannotBeInstantiated() {
        expectedAssertionError("Unable to instantiate class");
        ClassAssert.assertStaticUtilityClass(UninstantiableClass.class);
    }

    private void expectAssertionErrorDueToLackOfSinglePrivateConstructor() {
        expectedAssertionError("Static utility class should have single no-args private constructor");
    }

    private void expectedAssertionError(String message) {
        this.expectedException.expect(AssertionError.class);
        this.expectedException.expectMessage(message);
    }

    public static class NonFinalUtilityClass {
        private NonFinalUtilityClass() {
        }
    }

    public static final class PublicConstructorClass {
        public PublicConstructorClass() {
        }
    }

    public static final class ProtectedConstructorClass {
        protected ProtectedConstructorClass() {
        }
    }

    public static final class DefaultConstructorClass {
        DefaultConstructorClass() {
        }
    }

    public static final class MixtureOfConstructorsClass {
        private MixtureOfConstructorsClass(int i) {
        }

        public MixtureOfConstructorsClass() {
            this(0);
        }
    }

    public static final class PrivateConstructorWithArgsClass {
        private PrivateConstructorWithArgsClass(int arg) {
        }
    }

    public static final class UninstantiableClass {
        private UninstantiableClass() {
            throw new UnsupportedOperationException();
        }
    }
}