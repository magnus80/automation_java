package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {


    @Test
    public void testContactCreation() {
        app.getContactHelper().initContactCreation();
        app.getContactHelper().fillContactForm(new ContactData("Ivan", "I", "Ivanov", "Ivy", "QA Ingeneer", "Best", "Московская, 90", "56-90-90", "968-253-36-36", "56-56-56", "56-65-56", "ivan.ivanovi.@best.com"));
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().gotoHomePage();
    }

}
