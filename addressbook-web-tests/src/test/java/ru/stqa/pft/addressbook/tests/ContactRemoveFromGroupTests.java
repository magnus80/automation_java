package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
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

import static ru.stqa.pft.addressbook.tests.TestBase.app;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
/**
 * Created by KIryshkov on 07.04.2016.
 */
public class ContactRemoveFromGroupTests extends TestBase{

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


  public void testContactRemoveFromGroup(){
    Contacts beforeContacts = app.db().contacts();
    Groups beforeGroups = app.db().groups();
    ContactData contactToGroup = app.db().contacts().iterator().next();
    GroupData groupForContact = app.db().groups().iterator().next();

    Boolean foundContactAndGroup = false;

    for (ContactData c : beforeContacts) {
      if (c.getGroups().size() > 0) {
        for (GroupData g : beforeGroups) {
          if (g.getContacts().size() > 0) {
            foundContactAndGroup = true;
            contactToGroup = c;
            groupForContact = g;
            break;
          }
        }
      }
    }

    Groups beforeContactToGroup = contactToGroup.getGroups();

    if (!foundContactAndGroup) {
      addContactToGroup(contactToGroup, groupForContact);
      beforeContacts = app.db().contacts();

      for (ContactData c : beforeContacts ) {
        if (c.getId() == contactToGroup.getId()) {
          beforeContactToGroup = c.getGroups();
          break;
        }
      }
    }

    removeContactFromGroup(contactToGroup, groupForContact);

    Contacts afterContacts = app.db().contacts();
    Groups afterContactToGroup = null;

    for (ContactData c : afterContacts ) {
      if (c.getId() == contactToGroup.getId()) {
        afterContactToGroup = c.getGroups();
        break;
      }
    }

    assertThat(beforeContactToGroup, equalTo(afterContactToGroup.withAdded(groupForContact)));
//      verifyContactsInGroupUI(groupForContact);


  }

  private void removeContactFromGroup(ContactData contactToGroup, GroupData groupForContact) {
  }


}
