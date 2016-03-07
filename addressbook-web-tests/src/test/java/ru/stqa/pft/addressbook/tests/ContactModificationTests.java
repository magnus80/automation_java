package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by KIryshkov on 03.03.2016.
 */
public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification(){
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().selectContact();
    app.getContactHelper().editContact();
    app.getContactHelper().fillContactForm(new ContactData("Ivan1", "I", "Ivano1v", "Ivy1", "QA Ingeneer", "Best1", "Московская, 90", "56-90-90", "968-253-36-36", "56-56-56", "56-65-56", "ivan.ivanovi.@best.com"));
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnToHomePage();
  }
}