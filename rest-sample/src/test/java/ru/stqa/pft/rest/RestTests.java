package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.message.BasicNameValuePair;
import org.omg.CORBA.Request;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

import static org.testng.Assert.assertEquals;

/**
 * Created by KIryshkov on 15.04.2016.
 */
public class RestTests {

  @Test
  public void testCreateIssue(){
    Set<Issue> oldIssues=getIssues();
    Issue newIssue=new Issue().withSubject("werfewf").withDescription("dfwd");
    int issueId =createIssue(newIssue);
    Set<Issue> newIssues=getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues,oldIssues);
  }

  private Set<Issue> getIssues() {
    String json=getExecutor().execute(Request.Get("http://demo.bugify.com/api/issues.json")).returnContent.asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues,new TypeToken<Set<Issue>>(){}.getType());
    return null;
  }

  private int createIssue(Issue newIssue) {
    String json=getExecutor().execute(Request.Post("http://demo.bugify.com/api/issues.json"))
            .bodyFrom(new BasicNameValuePair("subject",newIssue.getSubject()),
                      new BasicNameValuePair("description",newIssue.getDescription()))
            .returnContent.asString();
    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

  private Executor getExecutor() {
    return Executor.newInstance().auth("LSGjeU4yP1X493ud1hNniA== ","");
  }
}
