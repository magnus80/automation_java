package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
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

  @Parameter(names = "-c", description = "Contact count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();

    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    save(contacts, new File(file));
  }

  private void save(List<ContactData> contacts, File file) throws IOException {
    System.out.println();
    Writer writer = new FileWriter(file);
    for (ContactData contact : contacts) {
      writer.write(String.format("%s,%s,%s,%s,%s,%s,%s\n", contact.getFirstname(), contact.getMiddlename(), contact.getLastname(),
              contact.getAddress(), contact.getHomePhone(), contact.getWorkPhone(), contact.getMobilePhone()));
    }
    writer.close();
  }

  private List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withFirstname(String.format("%s", i)).withMiddlename(String.format("%s", i)).
              withLastname(String.format("%s", i)).withAddress(String.format("%s", i)).withHomePhone(String.format("%s", i)).
              withHomePhone(String.format("%s", i)).withWorkPhone(String.format("%s", i)).withMobilePhone(String.format("%s", i)));
    }
    return contacts;
  }
}


