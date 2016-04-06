package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.assertEquals;

/**
 * Created by KIryshkov on 02.03.2016.
 */
public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size()==0) {
      app.goTo().groupPage();
      //if (app.group().all().size() == 0) {
        app.group().createGroup(new GroupData().withName("TestGroup100"));
      //}
    }
  }

  @Test
  public void testGroupModification() {
    Groups before =app.db().groups();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId()).withName("test81").withHeader("test1").withFooter("test1");
    app.goTo().groupPage();
    app.group().modifyGroup(group);
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after =app.db().groups();
    assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
    verifyGroupListInUI();
  }


}
