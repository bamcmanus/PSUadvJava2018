package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser implements PhoneBillParser<PhoneBill> {
  String fileName;

  public TextParser(String filename) {
    if (checkFileNameFormat(filename))
      fileName = filename;
    else
      throw new IllegalArgumentException("Invalid file name. Must be alpha numeric followed by .txt");
  }

  boolean checkFileNameFormat(String fileName) {
    String txtPattern = "(^\\w+\\.txt$)";
    Pattern pattern = Pattern.compile(txtPattern);
    Matcher matcher = pattern.matcher(fileName);
    return matcher.matches();
  }

  @Override
  public PhoneBill parse() throws ParserException {
    return null;
  }
}
