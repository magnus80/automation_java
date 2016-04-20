package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.MantisUsers;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * Created by KIryshkov on 14.04.2016.
 */


public class ResetPasswordTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testResetPassword() throws IOException, MessagingException {
    app.admin().adminLogin(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
    app.admin().selectUser();
    MantisUsers userMantis = app.admin().getUser();
    app.admin().resetPassword();
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 30000);
    long now = System.currentTimeMillis();
    String newPassword = "password" + now;
    String confirmationLink = findConfirmationLink(mailMessages, userMantis.getEmail());
    app.registration().finish(confirmationLink, newPassword);
    assertTrue(app.newSession().login(userMantis.getUsername(), newPassword));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex=VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
