package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by KIryshkov on 01.03.2016.
 */
public class TestBase {

  protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  public void verifyGroupListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Groups dbGroups = app.db().groups();
      Groups uiGroups = app.group().all();
      //в dbGroups убираем хэдер и футер
      assertThat(uiGroups, equalTo(dbGroups.stream()
              .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
              .collect(Collectors.toSet())));
    }
  }

  public void verifyContactListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Contacts dbContacts = app.db().contacts();
      Contacts uiContacts = app.contact().all();
      assertThat(uiContacts, equalTo(dbContacts
              .stream().map((c) -> new ContactData().withId(c.getId()).withFirstname(c.getFirstname())
                      .withLastname(c.getLastname()).withMiddlename(c.getMiddlename()).withAddress(c.getAddress())
                      .withMobilePhone(c.getMobilePhone()).withHomePhone(c.getHomePhone()).withWorkPhone(c.getWorkPhone())
                      .withEmail(c.getEmail()).withEmail2(c.getEmail2()).withEmail3(c.getEmail3()))
              .collect(Collectors.toSet())));
    }
  }

  @AfterSuite
  public void tearDown() {
    app.stop();
  }

}
