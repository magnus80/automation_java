package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by KIryshkov on 03.03.2016.
 */
public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) {
      app.goTo().groupPage();
      if (app.db().groups().size() == 0) {
        app.group().create(new GroupData().withName("TestGroup100"));
      }
      app.goTo().homePage();
      app.contact().create(new ContactData().withFirstname("Ivan100").withLastname("Ivanov").withAddress("sdgjkdsflij").
              withHomePhone("345").withMobilePhone("345435").withWorkPhone("345435"));
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    //Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withFirstname("Ivan81").withLastname("Ivanov").withAddress("sdsdfrsdf").
                    withHomePhone("456456456").withMobilePhone("345435").withWorkPhone("456456");
    app.contact().modify(modifiedContact);
    assertThat(app.contact().count(), equalTo(before.size()));
    /*Contacts after = app.contact().all();
    Assert.assertEquals(after.size(), before.size());*/
    assertThat(before, equalTo(before.without(modifiedContact).withAdded(contact)));
/*  before.remove(modifiedContact);
    before.add(contact);
    Assert.assertEquals(before, after);*/
  }

}
