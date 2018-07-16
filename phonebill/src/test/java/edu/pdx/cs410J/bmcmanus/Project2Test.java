package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project2} main class.
 */
public class Project2Test extends InvokeMainTestCase {
  String name = "name";
  String caller = "123-456-7890";
  String callee = "234-567-8901";
  String startDate = "07/04/2018";
  String startTime = "6:24";
  String endDate = "07/04/2018";
  String endTime = "6:48";

  /**
   * Invokes the main method of {@link Project2} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain( Project2.class, args );
  }

  @Test
  public void testFileHappyPath() {
    var result = invokeMain("-textFile","file",name,callee,caller,startDate,startTime,
        endDate,endTime);
    assertThat(result.getExitCode(),equalTo(0));
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
    MainMethodResult result = invokeMain("My Customer", caller, callee, startDate, startTime,
        endDate);
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(
        "Missing command line arguments"));
  }

  @Test
  public void testTooManyArgs() {
    MainMethodResult result = invokeMain("My Customer", caller, callee, startDate, startTime,
        endDate, endTime, endTime);
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(
        "Too many command line arguments"));
  }

  @Test
  public void dashReadmeOptionPrintsOnlyReadme() {
    MainMethodResult result = invokeMain("-README");
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(), equalTo(Project2.README + "\n"));
    assertThat(result.getTextWrittenToStandardError(), equalTo(""));
  }

  @Test
  public void dashPrintOptionsPrintsNewlyCreatedPhoneCall() {
    MainMethodResult result =
        invokeMain("-print", "My Customer", caller, callee, startDate, startTime, endDate,
            endTime);

    assertThat(result.getExitCode(), equalTo(0));
    String phoneCallToString = String.format("Phone call from %s to %s from %s %s to %s %s",
        caller, callee, startDate, startTime, endDate, endTime);
    assertThat(result.getTextWrittenToStandardOut(), equalTo(phoneCallToString + "\n"));
  }

  @Test
  public void validCommandLineWithNoDashPrintOptionPrintsNothingToStandardOut() {
    MainMethodResult result =
        invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);

    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
    assertThat(result.getTextWrittenToStandardError(), equalTo(""));

  }

}