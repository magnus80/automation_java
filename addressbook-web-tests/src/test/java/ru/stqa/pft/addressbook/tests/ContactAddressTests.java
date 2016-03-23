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
public class ContactAddressTests extends TestBase {
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
    public void testContactAddress() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
    }

/*    private String mergeAddresses(ContactData contactInfoFromEditForm) {
        return Arrays.asList(contactInfoFromEditForm.getAddress())
                .stream().filter((s)->!s.equals("")) //фильтрация от пустых значений
                .map(ContactAddressTests::cleanedAddresses)   //очистка от лишних символов
                .collect(Collectors.joining("")); //склейка
    }

    public static String cleanedAddresses (String address){
        return address.replaceAll("\\s","").replaceAll("[-(,)]","");
    }*/
}
