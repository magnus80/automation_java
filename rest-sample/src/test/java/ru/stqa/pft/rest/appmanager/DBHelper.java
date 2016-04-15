package ru.stqa.pft.rest.appmanager;


import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.mantis.model.MantisUsers;
import ru.stqa.pft.mantis.model.UserData;

import java.util.List;

/**
 * Created by KIryshkov on 01.04.2016.
 */
public class DBHelper {

  private final org.hibernate.SessionFactory sessionFactory;

  public DBHelper() {
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
  }

  public MantisUsers users() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<UserData> result = session.createQuery("from UserData").list();
    session.getTransaction().commit();
    session.close();
    return new MantisUsers();
  }

}
