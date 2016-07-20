package uk.co.codera.test.dto;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class JiraIssueUrlFactoryTest {

    private static final String JIRA_BASE_URL = "http://jira/browse/";

    private IssueUrlFactory factory;

    @Before
    public void before() {
        this.factory = new JiraIssueUrlFactory(JIRA_BASE_URL);
    }

    @Test
    public void shouldGenerateCorrectUrl() {
        assertThat(this.factory.generate("ISSUE-1"), is("http://jira/browse/ISSUE-1"));
    }
}