package ru.stqa.pft.addressbook.tests;

        import org.testng.Assert;
        import org.testng.annotations.Test;
        import ru.stqa.pft.addressbook.model.ContactData;

        import java.util.HashSet;
        import java.util.List;

public class ContactCreationTests extends TestBase {


    @Test
    public void testContactCreation() {
        app.getNavigationHelper().gotoHomePage();
        List<ContactData> before=app.getContactHelper().getContactList();
        app.getContactHelper().initContactCreation();
        ContactData contact=new ContactData("Ivan", "I", "Ivanov", "Ivy", null, null, "Московская, 90", "56-90-90", "968-253-36-36", "56-56-56", "56-65-56", "ivan.ivanovi.@best.com");
        app.getContactHelper().fillContactForm(contact);
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().returnToHomePage();
        List<ContactData> after=app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() + 1);

      int max=0;
      for (ContactData g:after){
        if (g.getId()>max){
          max=g.getId();
        }
      }
      contact.setId(max);
      before.add(contact);
      Assert.assertEquals(new HashSet<Object>(before),new HashSet<Object>(after));
    }

}
