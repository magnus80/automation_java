package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by KIryshkov on 03.03.2016.
 */
public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.goTo().groupPage();
      if (app.group().all().size() == 0) {
        app.group().create(new GroupData().withName("TestGroup100"));
      }
      app.goTo().homePage();
      app.contact().create(new ContactData().withFirstname("Ivan100").withMiddlename("I").withLastname("Ivanov").withNickname("Ivy"));
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withFirstname("Ivan81").withLastname("Ivanov");
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    //Assert.assertEquals(after.size(), before.size());
    assertThat(before, equalTo(before.without(modifiedContact).withAdded(contact)));
    //before.remove(modifiedContact);
    //before.add(contact);
    //Assert.assertEquals(before, after);
  }

}
