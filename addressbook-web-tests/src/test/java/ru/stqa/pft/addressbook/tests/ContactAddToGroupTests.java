package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
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
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.stqa.pft.addressbook.tests.TestBase.app;

/**
 * Created by KIryshkov on 06.04.2016.
 */
public class ContactAddToGroupTests extends TestBase{

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
    Groups beforeGroups = app.db().groups();
    GroupData modifiedGroup = beforeGroups.iterator().next();

    Boolean foundContactAndGroup = false;

    for (ContactData c : before) {
      if (c.getGroups().size() < beforeGroups.size()) {
        for (GroupData g : beforeGroups) {
          if (g.getContacts().size() < before.size()) {
            foundContactAndGroup = true;
            modifiedContact = c;
            modifiedGroup = g;
            break;
          }
        }
      }
    }

    if (!foundContactAndGroup) {
      app.goTo().groupPage();
    }
    long now = System.currentTimeMillis();
    GroupData newGroup = new GroupData().withName((String.format("Test%s", now))).withFooter("22").withHeader("33");
    app.group().createGroup(newGroup);

    beforeGroups = app.db().groups();

    for (GroupData g : beforeGroups) {
      if (g.getName().equals(newGroup.getName())) {
        modifiedGroup = g;
        break;
      }
    }

    Groups beforeContactToGroup = modifiedContact.getGroups();

    addContactToGroup(modifiedContact, modifiedGroup);

    Contacts afterContacts = app.db().contacts();
    Groups afterContactToGroup = null;

    for (ContactData c : afterContacts) {
      if (c.getId() == modifiedContact.getId()) {
        afterContactToGroup = c.getGroups();
        break;
      }
    }
    assertThat(beforeContactToGroup, equalTo(afterContactToGroup.without(modifiedGroup)));
  }

  private void addContactToGroup(ContactData modifiedContact, GroupData modifiedGroup) {
    app.goTo().homePage();
    app.contact().addToGroup(modifiedContact, modifiedGroup);
    app.contact().addContactToGroup();
  }


}


