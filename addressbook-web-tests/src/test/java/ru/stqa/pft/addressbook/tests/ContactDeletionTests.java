package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

/**
 * Created by KIryshkov on 03.03.2016.
 */
public class ContactDeletionTests extends TestBase{

  @Test
  public void testContactDeletion(){
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContact();
  }
}
