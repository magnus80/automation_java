package ru.stqa.pft.rest.appmanager;

import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by KIryshkov on 15.04.2016.
 */
public class SoapHelper {
  private ApplicationManager app;

  public SoapHelper(ApplicationManager app) {
    this.app = app;
  }

  public Set<Project> getProjects() throws RemoteException, MalformedURLException, ServiceException {
    MantisConnectPortType mc = getMantisConnect();
    ProjectData[] projects = mc.mc_projects_get_user_accessible(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
    //преобразование полученных данных в объекты project
    return Arrays.asList(projects).stream()
            .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName()))
            .collect(Collectors.toSet());
  }
//для домашнего задания
  public Set<Issue> getIssues() throws RemoteException, MalformedURLException, ServiceException {
    MantisConnectPortType mc = getMantisConnect();
    Project pr=new Project();
    IssueData issues = mc.mc_issue_get( app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"), BigInteger.valueOf(pr.getId()));
    //преобразование полученных данных в объекты project
    return Arrays.asList(issues).stream()
            .map((i) -> new Issue().withId(i.getId().intValue()).withSummary(i.getSummary()).withDescription(i.getDescription()))
            .collect(Collectors.toSet());
  }


  private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
    return new MantisConnectLocator().
            getMantisConnectPort(new URL(app.getProperty("soap.URL")));
  }

  public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    String[] categories = mc.mc_project_get_categories(app.getProperty("web.adminLogin"),
            app.getProperty("web.adminPassword"), BigInteger.valueOf(issue.getProject().getId()));
    IssueData issueData = new IssueData();
    issueData.setSummary(issue.getSummary());
    issueData.setDescription(issue.getDescription());
    issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
    issueData.setCategory(categories[0]);
    BigInteger issueId = mc.mc_issue_add(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"), issueData);
    IssueData createdIssueData = mc.mc_issue_get(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"), issueId);
    return new Issue().withId(createdIssueData.getId().intValue())
            .withSummary(createdIssueData.getSummary()).withDescription(createdIssueData.getDescription())
            .withProject(new Project().withId(createdIssueData.getProject().getId().intValue())
                    .withName(createdIssueData.getProject().getName()));
  }
}
