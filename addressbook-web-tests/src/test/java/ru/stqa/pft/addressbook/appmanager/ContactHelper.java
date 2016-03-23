package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

/**
 * Created by Homer-PC on 01.03.2016.
 */
public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    wd.findElement(By.xpath("//div[@id='content']/form/input[21]")).click();
  }

  public void fillContactForm(ContactData contactData) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("nickname"), contactData.getNickname());
    type(By.name("title"), contactData.getTitle());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("fax"), contactData.getFax());
    type(By.name("email"), contactData.getEmail());
  }

  public void initContactCreation() {
    wd.findElement(By.linkText("add new")).click();
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void editContact() {
    click(By.cssSelector("img[title='Edit']"));
  }

  public void submitContactModification() {
    click(By.xpath("//div[@id='content']/form[1]/input[22]"));
  }

  public void deleteSelectedContact() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    wd.switchTo().alert().accept();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContact();
    contactCache=null;
    //не нужно возвращаться
    //returnToContactPage();
  }

  private void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" +id+ "']")).click();
  }

  public void create(ContactData contact) {
    initContactCreation();
    fillContactForm(contact);
    submitContactCreation();
    contactCache=null;
    returnToContactPage();
  }

  public void returnToContactPage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.linkText("home page"));
  }

  public void modify(ContactData contact) {
    //не нужно выделять сразу редактировать
    //selectContactById(contact.getId());
    editContact();
    fillContactForm(contact);
    submitContactModification();
    contactCache=null;
    returnToContactPage();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  private Contacts contactCache=null;

  public Contacts all() {
    if (contactCache!=null){
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> rows = wd.findElements(By.name("entry"));
    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("id"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String allPhones=cells.get(5).getText();
      String Address=cells.get(3).getText();
      String allEmails=cells.get(4).getText();
      //String[] phones = cells.get(5).getText().split("\n");

      //contactCache.add(new ContactData().withId(id).withLastname(lastname).withFirstname(firstname).withHomePhone(phones[0]).
      //                  withMobilePhone(phones[1]).withWorkPhone(phones[2]));
      contactCache.add(new ContactData().withId(id).withLastname(lastname).withFirstname(firstname).withAddress(Address).withAllEmails(allEmails).withAllPhones(allPhones));
    }
    return new Contacts(contactCache);
  }


  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname=wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname=wd.findElement(By.name("lastname")).getAttribute("value");
    String home=wd.findElement(By.name("home")).getAttribute("value");
    String mobile=wd.findElement(By.name("mobile")).getAttribute("value");
    String work=wd.findElement(By.name("work")).getAttribute("value");
    String address=wd.findElement(By.cssSelector("textarea[name='address']")).getAttribute("value");
    String email1=wd.findElement(By.cssSelector("input[name='email']")).getAttribute("value");
    String email2=wd.findElement(By.cssSelector("input[name='email2']")).getAttribute("value");
    String email3=wd.findElement(By.cssSelector("input[name='email3']")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname).
            withAddress(address).withEmail(email1).withEmail2(email2).withEmail3(email3).
            withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
  }

  private void initContactModificationById(int id) {
    //WebElement checkbox=
    //wd.findElement(By.xpath(String.format("input[value='%s']./../..td[8]/a",id))).click();
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']",id))).click();
    //WebElement row=checkbox.findElement(By.xpath("./../.."));
    //List<WebElement> cells=row.findElements(By.tagName("td"));
    //cells.get(7).findElement(By.tagName("a")).click();
  }
}
