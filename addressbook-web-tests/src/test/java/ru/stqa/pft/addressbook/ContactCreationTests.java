package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {


    @Test
    public void testContactCreation() {
        initContactCreation();
        fillContactForm(new ContactData("Ivan", "I", "Ivanov", "Ivy", "QA Ingeneer", "Best", "Московская, 90", "56-90-90", "968-253-36-36", "56-56-56", "56-65-56", "ivan.ivanovi.@best.com"));
        submitContactCreation();
        gotoHomePage();
    }

}
