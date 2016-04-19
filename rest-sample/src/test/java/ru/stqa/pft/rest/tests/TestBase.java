package ru.stqa.pft.rest.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Configuration;
import ru.stqa.pft.rest.appmanager.ApplicationManager;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

/**
 * Created by KIryshkov on 01.03.2016.
 */
public class TestBase {

  protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
    //app.ftp().upload(new File("src/test/resources/config_inc.php"),"config_inc.php","config_inc.php.bak");
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  /*private String getIssueStatus(int issueId) throws IOException {
    String requestString = "http://demo.bugify.com/api/issues/" + issueId + ".json";
    String json = getExecutor().execute(Request.Get(requestString))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issue = parsed.getAsJsonObject().get("issues");
    String status = parsed.getAsJsonObject().get("state_name").getAsString();

    return status;
  }*/

  Set<Issue> getIssuesSet(String jsonString) throws IOException {
    String json = getExecutor().execute(Request.Get("http://demo.bugify.com/api/issues.json"))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {}.getType());
  }

  public Set<Issue> getIssues() throws IOException {
    return getIssuesSet("http://demo.bugify.com/api/issues.json");
  }

  public Issue getIssueById(int id) throws IOException {
    Issue issue = new Issue();
    getIssuesSet(String.format("http://demo.bugify.com/api/issues/%s.json", id))
            .stream().findFirst().map
            ((i) -> issue.withId(i.getId()).withSubject(i.getSubject())
                    .withDescription(i.getDescription())
                    .withState(i.getState())
            );
    return issue;
  }

  private Executor getExecutor() {
    return Executor.newInstance().auth("LSGjeU4yP1X493ud1hNniA== ", "");
  }

  public boolean isIssueOpen(int issueId) throws IOException {
    return !getIssueById(issueId).getState().equals("Closed");
  }

  @AfterSuite
  public void tearDown() throws IOException {
    //app.ftp().restore("config_inc.php.bak","config_inc.php");
    app.stop();
  }

}
