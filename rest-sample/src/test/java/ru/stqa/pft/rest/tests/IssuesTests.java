package ru.stqa.pft.rest.tests;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by Homer-PC on 19.04.2016.
 */
public class IssuesTests extends TestBase {

  @Test
  public void testPreconditions() throws IOException {

    //isIssueOpen(1);
    skipIfNotFixed(1);
    skipIfNotFixed(2);
    skipIfNotFixed(3);
  }

}
