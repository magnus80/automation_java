package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

/**
 * Created by KIryshkov on 03.03.2016.
 */
public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().list().size() == 0) {
      app.goTo().groupPage();
      if (app.group().list().size() == 0) {
        app.group().create(new GroupData().withName("TestGroup1"));
      }
      app.goTo().homePage();
      app.contact().create(new ContactData().withFirstname("Ivan3").withMiddlename("I").withLastname("Ivanov").withNickname("Ivy"));
    }
  }

  @Test(enabled = false)
  public void testContactDeletion() {
    List<ContactData> before = app.contact().list();
    int index=before.size() - 1;
    app.contact().delete(index);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(),index);

    before.remove(index);
    Assert.assertEquals(before, after);
  }


}
