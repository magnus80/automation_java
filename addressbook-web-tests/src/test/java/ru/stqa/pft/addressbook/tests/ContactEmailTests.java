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
 * Created by Homer-PC on 23.03.2016.
 */
public class ContactEmailTests extends TestBase {
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
    public void testContactEmails() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    }

    private String mergeEmails(ContactData contactInfoFromEditForm) {
        return Arrays.asList(contactInfoFromEditForm.getEmail(), contactInfoFromEditForm.getEmail2(), contactInfoFromEditForm.getEmail3())
                .stream().filter((s) -> !s.equals("")) //фильтрация от пустых значений
                .map(ContactEmailTests::cleanedEmails)   //очистка от лишних символов
                .collect(Collectors.joining("\n")); //склейка
    }

    public static String cleanedEmails(String email) {
        return email.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
