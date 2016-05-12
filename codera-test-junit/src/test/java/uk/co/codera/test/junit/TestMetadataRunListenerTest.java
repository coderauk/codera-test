package uk.co.codera.test.junit;

import org.junit.Test;
import org.junit.runner.JUnitCore;

@TestMetadata(type = TestType.UNIT, issues = { "JIRA-01" })
public class TestMetadataRunListenerTest {

    public static void main(String[] args) {
        JUnitCore core = new JUnitCore();
        core.addListener(new TestMetadataRunListener());
        core.run(TestMetadataRunListenerTest.class);
    }

    @TestMetadata(issues = { "JIRA-02" })
    @Test
    public void shouldDoSomething() {
    }
}