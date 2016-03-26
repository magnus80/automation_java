package ru.stqa.pft.addressbook.generators;

import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Homer-PC on 27.03.2016.
 */
public class ContactDataGenerator {
  public static void main(String[] args) throws IOException {
    int count=Integer.parseInt(args[0]);
    File file=new File(args[1]);

    List<ContactData> contacts=generateContacts(count);
    save(contacts,file);
  }

  private static void save(List<ContactData> contacts, File file) throws IOException {
    System.out.println();
    Writer writer =new FileWriter(file);
    for (ContactData contact:contacts){
      writer.write(String.format("%s,%s,%s,%s,%s,%s,%s\n",contact.getFirstname(),contact.getMiddlename(),contact.getLastname(),
              contact.getAddress(),contact.getHomePhone(),contact.getWorkPhone(),contact.getMobilePhone()));
    }
    writer.close();
  }

  private static List<ContactData> generateContacts(int count) {
    List<ContactData>contacts=new ArrayList<ContactData>();
    for (int i=0;i<count;i++){
      contacts.add(new ContactData().withFirstname(String.format("%s",i)).withMiddlename(String.format("%s",i)).
              withLastname(String.format("%s",i)).withAddress(String.format("%s",i)).withHomePhone(String.format("%s",i)).
              withHomePhone(String.format("%s",i)).withWorkPhone(String.format("%s",i)).withMobilePhone(String.format("%s",i)));
    }
    return contacts;
  }
}


