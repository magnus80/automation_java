package ru.stqa.pft.soap.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import net.webservicex.GeoIP;
import net.webservicex.GeoIPService;

import static org.testng.Assert.assertEquals;


/**
 * Created by KIryshkov on 15.04.2016.
 */
public class GeoIpServiceTests {

  @Test
  public void testMyIP(){
    GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("217.118.86.102");
    assertEquals(geoIP.getCountryCode(),"RUS");
  }

  @Test
  public void testInvalidIP(){
    GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("217.118.86.xxx");
    assertEquals(geoIP.getCountryCode(),"RUS");
  }
}
