package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.stqa.pft.addressbook.tests.TestBase.app;

/**
 * Created by KIryshkov on 06.04.2016.
 */
public class ContactAddToGroupTests {

  @DataProvider
  public Iterator<Object[]> validContacts() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.xml"))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
      return contacts.stream().map(g -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) {
      app.goTo().groupPage();
      if (app.db().groups().size() == 0) {
        app.group().createGroup(new GroupData().withName("TestGroup100"));
      }
      app.goTo().homePage();
      app.contact().createContact(new ContactData().withFirstname("Ivan100").withLastname("Ivanov").withAddress("sdgjkdsflij").
              withHomePhone("345").withMobilePhone("345435").withWorkPhone("345435"));
    }
  }

  @Test
  public void testAddContactToGroups(ContactData contact) {

    app.goTo().groupPage();
    Groups groups = app.group().all();
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    //Groups beforeGroups = app.db().groups();

    app.contact().addToGroup(modifiedContact, groups.iterator().next());

    for (ContactData contacts : (Set<ContactData>) before) {
      System.out.println(contact);
      System.out.println(contact.getGroups());
      // System.out.println(contact.getGroups().size() + "<" + app.db().groups().size());
      // System.out.println();

      if (contact.getGroups().size() < app.db().groups().size()) {
        System.out.println(contact);
        System.out.println(contact.getGroups());

      }

    }


    /*ContactData contactToMove = contacts.iterator().next();
    GroupData groupToAssign = groups.iterator().next();

    //app.contact().addToGroup(contactToMove, groupToAssign);

    System.out.println(contactToMove);
    System.out.println(groupToAssign);

    Contacts contactsModified = app.db().contacts();
    Boolean flag = false;
    for (ContactData c : contactsModified) {
      if (c.getGroups().iterator().next().getId() == groupToAssign.getId()) {
        flag = true;
      }
    }
    Assert.assertTrue(flag);*/
    //ContactData contactMoved = contactToMove.inGroup(groupToAssign);

    //assertThat(groupToAssign.getId(), equalTo(contactMoved.getGroups().iterator().next().getId()));

    //contactToMove.getGroups().iterator().next().getId()
  }

}
