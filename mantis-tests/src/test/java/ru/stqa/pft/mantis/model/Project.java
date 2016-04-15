package ru.stqa.pft.mantis.model;

import java.math.BigInteger;

/**
 * Created by KIryshkov on 15.04.2016.
 */
public class Project {
  public int getId() {
    return id;
  }

  public Project withId(int id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public Project withName(String name) {
    this.name = name;
    return this;
  }

  private int id;
  private String name;
}
