package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Homer-PC on 24.03.2016.
 */
public class ContactComparisonDataTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.goTo().groupPage();
      if (app.group().all().size() == 0) {
        app.group().createGroup(new GroupData().withName("TestGroup100"));
      }
      app.goTo().homePage();
      app.contact().createContact(new ContactData().withFirstname("Ivan100").withMiddlename("I").withLastname("Ivanov").withNickname("Ivy"));
    }
  }

  @Test
  public void testContactComparisonData() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFIOFromEditForm(contact);
    ContactData contactInfoFromDetailsForm = app.contact().infoFromDetailsForm(contact);
    assertThat(contactInfoFromDetailsForm.getAllData(), equalTo(mergeData(contactInfoFromEditForm)));
  }

  private String mergeData(ContactData contact) {
    return Arrays.asList(contact.getFio(), contact.getNickname(), contact.getAddress(), contact.getHomePhone(), contact.getMobilePhone()
            , contact.getWorkPhone(), contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s) -> !s.equals("")) //фильтрация от пустых значений
            //.map(ContactComparisonDataTest::fio) //очистка от лишних символов
            //.map(ContactComparisonDataTest::cleanedEmails)//очистка от лишних символов
            .collect(Collectors.joining("\n")); //склейка
  }

}
