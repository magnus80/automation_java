package ru.stqa.pft.rest.appmanager;

import org.apache.commons.net.ftp.FTPClient;
import ru.stqa.pft.mantis.tests.TestBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * Created by KIryshkov on 08.04.2016.
 */
public class FtpHelper extends TestBase {

  private FTPClient ftp;
  private final ApplicationManager app;

  public FtpHelper(ApplicationManager app) {
    this.app = app;
    ftp = new FTPClient();
  }

  public void upload(File file, String target, String backup) throws IOException {
    ftp.connect(app.getProperty("ftp.host"));
    ftp.login(app.getProperty("ftp.login"),app.getProperty("ftp.password"));
    ftp.deleteFile(backup);
    ftp.rename(target,backup);
    ftp.enterLocalPassiveMode();
    ftp.storeFile(target,new FileInputStream(file));
    ftp.disconnect();
  }

  public void restore(String target, String backup) throws IOException {
    ftp.connect(app.getProperty("ftp.host"));
    ftp.login(app.getProperty("ftp.login"),app.getProperty("ftp.password"));
    ftp.deleteFile(target);
    ftp.rename(backup,target);
    ftp.disconnect();
  }
}
