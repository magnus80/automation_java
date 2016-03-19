package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

/**
 * Created by KIryshkov on 03.03.2016.
 */
public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
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
  public void testContactModification() {
    List<ContactData> before = app.contact().list();
    int index=before.size() - 1;
    ContactData contact = new ContactData().withId(before.get(index).getId()).withFirstname("Ivan1").withLastname("Ivanov");
    app.contact().modify(index, contact);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }


}
