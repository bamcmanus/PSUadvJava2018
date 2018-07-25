package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.InvokeMainTestCase;
import java.text.DateFormat;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
public class Project3Test extends InvokeMainTestCase {
  private String name = "name";
  private String caller = "123-456-7890";
  private String callee = "234-567-8901";
  private String startDate = "12/12/2001";
  private String startTime = "12:12";
  private String endDate = "12/12/2001";
  private String endTime = "12:13";
  private String endAmPm = "am";
  private String startAmPm = "am";

  /**
   * Invokes the main method of {@link Project3} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain( Project3.class, args );
  }

  @Test
  public void prettyHappyPath() {
    var result = invokeMain("-pretty","pretty.txt",name,callee,caller,startDate,startTime,
        startAmPm,endDate,endTime,endAmPm);
    assertThat(result.getExitCode(),equalTo(0));
  }

  @Test
  public void noFileAfterPretty () {
    var result = invokeMain("-pretty", "-print",name,callee,caller,startDate,startTime,
        startAmPm,endDate,endTime,endAmPm);
    assertThat(result.getExitCode(),equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),containsString(
        "No file was found after the -pretty option"));
  }

  @Test
  public void testFileHappyPath() {
    var result = invokeMain("-textFile","file.txt",name,callee,caller,startDate,startTime,
        startAmPm,endDate,endTime,endAmPm);
    assertThat(result.getExitCode(),equalTo(0));
    assertThat(result.getTextWrittenToStandardError(),equalTo(""));
  }

  @Test
  public void testOptionAfterFileOption() {
    MainMethodResult result = invokeMain("-textFile","-print");
    assertThat(result.getExitCode(),equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),
        containsString("No file was found after the -textFile option"));
  }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(
        "Missing command line arguments"));
  }

  @Test
  public void testUnknownOption() {
    MainMethodResult result = invokeMain("-unknown");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(
        "Not a valid option"));
  }

  @Test
  public void testTooFewArgs() {
    MainMethodResult result = invokeMain("My Customer",callee,caller,startDate,startTime,
        startAmPm,endDate,endTime);
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(
        "Missing command line arguments"));
  }

  @Test
  public void testTooManyArgs() {
    MainMethodResult result = invokeMain("My Customer", name,callee,caller,startDate,startTime,
        startAmPm,endDate,endTime,endAmPm, "extra");
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(
        "Too many command line arguments"));
  }

  @Test
  public void dashReadmeOptionPrintsOnlyReadme() {
    MainMethodResult result = invokeMain("-README");
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(), equalTo(Project3.README + "\n"));
    assertThat(result.getTextWrittenToStandardError(), equalTo(""));
  }

  @Test
  public void dashPrintOptionsPrintsNewlyCreatedPhoneCall() {
    MainMethodResult result =
        invokeMain("-print", "My Customer",caller,callee,startDate,startTime,
            startAmPm,endDate,endTime,endAmPm);

    assertThat(result.getExitCode(), equalTo(0));
    String phoneCallToString = String.format("Phone call from %s to %s from 12/12/01, 12:12 AM to 12/12/01, 12:13 AM",
        caller, callee);
    assertThat(result.getTextWrittenToStandardOut(), equalTo(phoneCallToString + "\n"));
  }

  @Test
  public void validCommandLineWithNoDashPrintOptionPrintsNothingToStandardOut() {
    MainMethodResult result =
        invokeMain("My Customer",callee,caller,startDate,startTime,
            startAmPm,endDate,endTime,endAmPm);

    //assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
    assertThat(result.getTextWrittenToStandardError(), equalTo(""));
  }

}