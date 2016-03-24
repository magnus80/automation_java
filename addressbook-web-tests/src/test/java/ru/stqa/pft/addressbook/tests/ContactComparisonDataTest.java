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

// ясно, значит все что видно в списке на главной + миддлнейм - это обязательно, остальное - на усмотрение
// сравнивать ФИО, адрес, телефоны (3), мыло (3)
//  заведите в классе ContactData атрибут fio и в его get-методе сразу соберите ФИО с пробелами....если пробелы убрать, то можно не отловить ситуацию:
//  Иван Иванов
//  Ива нИванов
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
  public void testContactComparisonData() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    ContactData contactInfoFromDetailsForm = app.contact().infoFromDetailsForm(contact);
    assertThat(contactInfoFromEditForm, equalTo(mergeData(contactInfoFromDetailsForm)));
  }

  private String mergeData(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
            .stream().filter((s) -> !s.equals("")) //фильтрация от пустых значений
            .map(ContactComparisonDataTest::cleanedPhones)
            .map(ContactComparisonDataTest::cleanedEmails)  //очистка от лишних символов
            .collect(Collectors.joining("\n")); //склейка
  }

  public static String cleanedPhones(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }

  public static String cleanedEmails(String email) {
    return email.replaceAll("\\s", "").replaceAll("[-()]", "");
  }
}
