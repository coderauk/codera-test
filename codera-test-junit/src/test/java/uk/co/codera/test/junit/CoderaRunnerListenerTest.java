package uk.co.codera.test.junit;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;

@TestMetadata(type = TestType.UNIT, issues = { "JIRA-01" })
@RunWith(CoderaJUnitRunner.class)
public class CoderaRunnerListenerTest {

    public static void main(String[] args) {
        JUnitCore core = new JUnitCore();
        core.addListener(new CoderaRunnerListener());
        core.run(CoderaRunnerListenerTest.class);
    }

    @TestMetadata(issues = { "JIRA-02" })
    @Test
    public void shouldDoSomething() {
    }
}
