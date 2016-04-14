package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by KIryshkov on 14.04.2016.
 */
public class AdminHelper extends HelperBase{

  public AdminHelper(ApplicationManager app) {
    super(app);
  }

  public void adminLogin(String username,String password) {
    /*String adminLogin = app.getProperty("web.adminLogin");
    String adminPassword = app.getProperty("web.adminPassword");*/
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), username);
    type(By.name("password"), password);
    click(By.cssSelector("input[value='Login']"));
  }

  public void chooseUser() {
    //String adminLogin = app.getProperty("web.adminLogin");
    click(By.xpath("//a[contains(.,'Manage Users')]"));
    WebElement selectedUser = wd.findElement(By.xpath("//a[contains(user3)]"));
    //WebElement chosenUser = users.stream().filter((u) -> !(u.getText().equals(adminLogin))).findFirst().get();
    selectedUser.click();
  }

  public void resetPassword() {

  }
}
