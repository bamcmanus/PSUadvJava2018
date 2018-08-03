package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.InvokeMainTestCase;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.IOException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Tests the {@link Project4} class by invoking its main method with various arguments
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Project4IT extends InvokeMainTestCase {

  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");
  private String customer = "customer";
  private String caller = "123-456-7890";
  private String callee = "000-867-5309";
  private String startDate = "8/1/2018";
  private String startTime = "2:09";
  private String startAmPm = "pm";
  private String endDate = "8/1/2018";
  private String endTime = "2:30";
  private String endAmPm = "pm";

  @Test
  public void test0RemoveAllMappings() throws IOException {
    PhoneBillRestClient client = new PhoneBillRestClient(HOSTNAME, Integer.parseInt(PORT));
    client.removeAllPhoneBills();
  }

  @Test
  public void test1NoCommandLineArguments() {
    MainMethodResult result = invokeMain(Project4.class);
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_ARGS));
  }

  @Test
  public void test2Readme() {
    MainMethodResult result = invokeMain(Project4.class, "-README");
    assertThat(result.getExitCode(),equalTo(0));
  }

  @Test
  public void test3MissingPort() {
    MainMethodResult result = invokeMain(Project4.class,"-host",HOSTNAME);
    assertThat(result.getExitCode(),equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),containsString("Missing port"));
  }

  @Test
  public void test6AddPhoneCall() throws ParseException {
    var call = new PhoneCall(caller,callee,startDate,startTime,startAmPm,endDate,endTime,endAmPm);

    MainMethodResult result = invokeMain(Project4.class, "-host",HOSTNAME,"-port", PORT, customer, caller,callee,startDate,startTime,startAmPm,endDate,endTime,endAmPm);
    assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));

    result = invokeMain(Project4.class, "-host",HOSTNAME,"-port", PORT, customer);
    String out = result.getTextWrittenToStandardOut();
    assertThat(result.getExitCode(),equalTo(0));
    assertThat(out, containsString(customer));
    assertThat(out, containsString(caller));
    assertThat(out, containsString(callee));
    assertThat(out,containsString(String.valueOf(new SimpleDateFormat("MM/dd/yy hh:mm a").format(call.getStartTime()))));
    assertThat(out,containsString(String.valueOf(new SimpleDateFormat("MM/dd/yy hh:mm a").format(call.getEndTime()))));
  }

  @Test
  public void test7searchPrintsInRange() throws ParseException {
    var call = new PhoneCall(caller,callee,startDate,startTime,startAmPm,endDate,endTime,endAmPm);

    MainMethodResult result = invokeMain(Project4.class, "-host",HOSTNAME,"-port", PORT, "-search",customer,startDate,startTime,startAmPm,endDate,endTime,endAmPm);
    String out = result.getTextWrittenToStandardOut();
    assertThat(result.getExitCode(),equalTo(0));
    assertThat(out, containsString(customer));
    assertThat(out, containsString(caller));
    assertThat(out, containsString(callee));
    assertThat(out,containsString(String.valueOf(new SimpleDateFormat("MM/dd/yy hh:mm a").format(call.getStartTime()))));
    assertThat(out,containsString(String.valueOf(new SimpleDateFormat("MM/dd/yy hh:mm a").format(call.getEndTime()))));
  }

  @Test
  public void test8searchPrintsNothingIfOutOfRange() {
    startDate = "8/1/2018";
    startTime = "2:10";
    startAmPm = "pm";
    endDate = "8/1/2018";
    endTime = "2:30";
    endAmPm = "pm";
    MainMethodResult result = invokeMain(Project4.class, "-host",HOSTNAME,"-port", PORT, "-search",customer,startDate,startTime,startAmPm,endDate,endTime,endAmPm);
    String out = result.getTextWrittenToStandardOut();
    assertThat(result.getExitCode(),equalTo(0));
    assertThat(out, containsString("Phone Bill for: " + customer));
    assertThat(out, containsString("No calls were found in that range"));
  }

  @Test
  public void test9CustomerNotFound() {
    customer = "Not there";
    MainMethodResult result = invokeMain(Project4.class, "-host",HOSTNAME,"-port", PORT, customer);
    String out = result.getTextWrittenToStandardError();
    assertThat(out, containsString("Customer not found"));
  }

  @Test
  public void test90CustomerNotFoundSearch() {
    customer = "no such customer";
    MainMethodResult result = invokeMain(Project4.class, "-host",HOSTNAME,"-port", PORT, "-search",customer,startDate,startTime,startAmPm,endDate,endTime,endAmPm);
    String out = result.getTextWrittenToStandardError();
    assertThat(out, containsString("Customer not found"));
  }
}