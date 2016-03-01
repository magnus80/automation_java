package ru.stqa.pft.addressbook;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Created by KIryshkov on 01.03.2016.
 */
public class TestBase {

    protected final ApplicationManager app = new ApplicationManager();

    @BeforeMethod
  public void setUp() throws Exception {
      app.init();
  }

    @AfterMethod
  public void tearDown() {

      app.stop();
  }

}
