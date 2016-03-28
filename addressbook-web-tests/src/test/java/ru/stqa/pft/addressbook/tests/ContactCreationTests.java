package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() {
    List<Object[]> list = new ArrayList<>();
    list.add(new Object[]{new ContactData().withFirstname("Firstname1").withMiddlename("Middlename1").
            withLastname("Lastname1").withAddress("Address 1").withEmail("email 1").withHomePhone("54321").
            withWorkPhone("236541").withMobilePhone("66566")});
    list.add(new Object[]{new ContactData().withFirstname("Firstname2").withMiddlename("Middlename2").
            withLastname("Lastname2").withAddress("Address 2").withEmail("email 2").withHomePhone("54321").
            withWorkPhone("236541").withMobilePhone("66566")});
    list.add(new Object[]{new ContactData().withFirstname("Firstname3").withMiddlename("Middlename3").
            withLastname("Lastname3").withAddress("Address 3").withEmail("email 3").withHomePhone("54321").
            withWorkPhone("236541").withMobilePhone("66566")});
    return list.iterator();
  }

  @Test(dataProvider = "validContacts")
  public void testContactCreation(ContactData contact) {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    File photo = new File("/src/test/resources/stru.png");
    //ContactData contact = new ContactData().withFirstname("Ivan80").withLastname("Ivanov").withPhoto(photo);
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    //assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }
}
