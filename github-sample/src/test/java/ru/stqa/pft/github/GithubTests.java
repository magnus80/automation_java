package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by Homer-PC on 15.04.2016.
 */
public class GithubTests {

  @Test
  public void testCommits() throws IOException {
    Github github = new RtGithub("de4bc7acf9d55b70aa8d7395ba77c080577d176f");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("magnus80", "automation_java")).commits();
    for (RepoCommit commit:commits.iterate(new ImmutableMap.Builder<String,String>().build()))
    {
      System.out.println(new RepoCommit.Smart(commit).message());
    }

  }
}
