package ru.stqa.pft.addressbook.tests;

        import org.testng.Assert;
        import org.testng.annotations.Test;
        import ru.stqa.pft.addressbook.model.ContactData;

        import java.util.List;

public class ContactCreationTests extends TestBase {


    @Test
    public void testContactCreation() {
        app.getNavigationHelper().gotoHomePage();
        int before = app.getContactHelper().getContactCount();
        app.getContactHelper().initContactCreation();
        app.getContactHelper().fillContactForm(new ContactData("Ivan", "I", "Ivanov", "Ivy", null, null, "Московская, 90", "56-90-90", "968-253-36-36", "56-56-56", "56-65-56", "ivan.ivanovi.@best.com"));
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().returnToHomePage();
        int after = app.getContactHelper().getContactCount();
        Assert.assertEquals(after, before + 1);
    }

}
