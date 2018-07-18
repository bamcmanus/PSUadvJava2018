package edu.pdx.cs410J.bmcmanus;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

public class TextDumperTest {
  String valid = "valid.txt";

  @Test
  public void verifyConstructorTxtHappyPath() {
    try {
      var test = new TextDumper(new File(valid));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void verifyConstructorPathHappyPath() {
    try {
      var test = new TextDumper(new File("./valid.txt"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
