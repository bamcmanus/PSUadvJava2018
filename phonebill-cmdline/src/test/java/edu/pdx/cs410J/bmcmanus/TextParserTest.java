package edu.pdx.cs410J.bmcmanus;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import edu.pdx.cs410J.ParserException;
import java.io.File;
import org.junit.Test;

public class TextParserTest {
  String valid = "valid.txt";

  /*@Test (expected = ParserException.class)
  public void verifyConstructorThrowsException() {
    var test = new TextParser(new File("not.valid"));
  }*/

  @Test
  public void verifyConstructorHappyPath() {

  }
}
