package uk.co.codera.test.dto;

public class JiraIssueUrlFactory implements IssueUrlFactory {

    private final String jiraBaseUrl;

    public JiraIssueUrlFactory(String jiraBaseUrl) {
        this.jiraBaseUrl = jiraBaseUrl;
    }

    @Override
    public String generate(String issue) {
        return this.jiraBaseUrl + issue;
    }
}