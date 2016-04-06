package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by KIryshkov on 03.03.2016.
 */
public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) {
      app.goTo().groupPage();
      if (app.db().contacts().size() == 0) {
        app.group().createGroup(new GroupData().withName("TestGroup100"));
      }
      app.goTo().homePage();
      app.contact().createContact(new ContactData().withFirstname("Ivan100").withMiddlename("I").withLastname("Ivanov").withNickname("Ivy"));
    }
  }

  @Test
  public void testContactDeletion() {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();
    app.contact().deleteContact(deletedContact);
    assertThat(app.contact().count(), equalTo(before.size() - 1));
    Contacts after = app.db().contacts();
    //assertEquals(after.size(), before.size() - 1);
    assertThat(after, equalTo(before.without(deletedContact)));
    verifyContactListInUI();
    //Assert.assertEquals(after.size(),before.size()-1);

    //before.remove(deletedContact);
    //Assert.assertEquals(before, after);
  }


}
