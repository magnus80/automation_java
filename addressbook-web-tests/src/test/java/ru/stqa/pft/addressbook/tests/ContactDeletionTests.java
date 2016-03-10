package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

/**
 * Created by KIryshkov on 03.03.2016.
 */
public class ContactDeletionTests extends TestBase{

  @Test
  public void testContactDeletion(){
    app.getContactHelper().gotoHomePage();
    if (!app.getContactHelper().isThereAContact()){
      if (!app.getGroupHelper().isThereAGroup()){
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().createGroup(new GroupData("TestGroup1", null, null));
      }
      //app.getContactHelper().gotoHomePage();
      app.getContactHelper().createContact(new ContactData("Ivan3", "I", "Ivanov", "Ivy", null, null, "Московская, 90", "56-90-90", "968-253-36-36", "56-56-56", "56-65-56", "ivan.ivanovi.@best.com"));
    }
    app.getContactHelper().gotoHomePage();
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContact();
  }
}
