package ru.stqa.pft.rest.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.rest.appmanager.ApplicationManager;


import java.io.File;
import java.io.IOException;

/**
 * Created by KIryshkov on 01.03.2016.
 */
public class TestBase {

  protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
    app.ftp().upload(new File("src/test/resources/config_inc.php"),"config_inc.php","config_inc.php.bak");
  }

  @BeforeSuite
  public void skipIfNotFixed(int issueId) {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  public boolean isIssueOpen(int issueId){
    return true;
  }

  @AfterSuite
  public void tearDown() throws IOException {
    app.ftp().restore("config_inc.php.bak","config_inc.php");
    app.stop();
  }

}
