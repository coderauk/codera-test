package uk.co.codera.test.junit;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class CoderaJUnitRunner extends BlockJUnit4ClassRunner {

    public CoderaJUnitRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }
}