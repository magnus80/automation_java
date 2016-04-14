package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Created by KIryshkov on 14.04.2016.
 */


public class ResetPasswordTests extends TestBase{

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testResetPassword(){
    String admin = app.getProperty("web.adminLogin");
    String password = app.getProperty("web.adminPassword");
    app.admin().adminLogin(admin,password);
    app.admin().selectUser();
    //app.admin().resetPassword();
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
