package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

/**
 * Created by KIryshkov on 03.03.2016.
 */
public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.goTo().groupPage();
      if (app.group().all().size() == 0) {
        app.group().create(new GroupData().withName("TestGroup1"));
      }
      app.goTo().homePage();
      app.contact().create(new ContactData().withFirstname("Ivan3").withMiddlename("I").withLastname("Ivanov").withNickname("Ivy"));
    }
  }

  @Test
  public void testContactDeletion() {
    Set<ContactData> before = app.contact().all();
    ContactData deletedContact=before.iterator().next();
    app.contact().delete(deletedContact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(),before.size()-1);

    before.remove(deletedContact);
    Assert.assertEquals(before, after);
  }


}
