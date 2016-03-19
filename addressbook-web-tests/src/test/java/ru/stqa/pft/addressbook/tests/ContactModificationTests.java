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
public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
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
  public void testContactModification() {
    Set<ContactData> before = app.contact().all();
    ContactData modifiedContact=before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("Ivan1").withLastname("Ivanov");
    app.contact().modify(contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedContact);
    before.add(contact);
    Assert.assertEquals(before, after);
  }

}
