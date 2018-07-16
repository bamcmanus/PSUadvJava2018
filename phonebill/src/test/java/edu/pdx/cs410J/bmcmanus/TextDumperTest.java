package edu.pdx.cs410J.bmcmanus;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class TextDumperTest {
  String valid = "valid.txt";

  @Test (expected = IllegalArgumentException.class)
  public void verifyConstructorThrowsException() {
    var test = new TextDumper("not.valid");
  }

  @Test
  public void verifyTxtAllowsValid() {
    var test = new TextDumper(valid);
    assertThat(test.checkFileNameFormat("valid.txt"), equalTo(true));
  }

  @Test
  public void verifyTxtAllowsUnderScore() {
    var test = new TextDumper(valid);
    assertThat(test.checkFileNameFormat("still_valid.txt"), equalTo(true));
  }

  @Test
  public void verifyTxtDoesntAllowPeriodInPrefix() {
    var test = new TextDumper(valid);
    assertThat(test.checkFileNameFormat("in.valid.txt"), equalTo(false));
  }

}
