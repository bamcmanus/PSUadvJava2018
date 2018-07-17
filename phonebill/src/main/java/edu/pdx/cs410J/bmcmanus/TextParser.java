package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Text parser class loads information into a phone bill from a .txt file path
 */
public class TextParser implements PhoneBillParser<PhoneBill> {

  /**
   * Field for the buffered reader that is used for file IO
   */
  private BufferedReader buffReader;

  /**
   * Constructor initializes the buffered reader
   * @param filename  File object used to create the buffered reader
   */
  TextParser(File filename) throws ParserException {
    try {
      buffReader = new BufferedReader(new FileReader(filename));
    } catch (FileNotFoundException e) {
      throw new ParserException("File not found.");
    }
  }

  @Override
  public PhoneBill parse() throws ParserException {
    String name;
    PhoneBill bill;
    String callerNum;
    try {
      name = buffReader.readLine();
      bill = new PhoneBill(name);
      while ((callerNum = buffReader.readLine()) != null) {
        var calledNum = buffReader.readLine();
        var startDate = buffReader.readLine();
        var startTime = buffReader.readLine();
        var endDate = buffReader.readLine();
        var endTime = buffReader.readLine();
        var call = new PhoneCall(callerNum, calledNum, startDate, startTime, endDate, endTime);
        bill.addPhoneCall(call);
      }
    } catch (IOException | NullPointerException e) {
      throw new ParserException("There was an error reading the file");
    }
    return bill;
  }
}
