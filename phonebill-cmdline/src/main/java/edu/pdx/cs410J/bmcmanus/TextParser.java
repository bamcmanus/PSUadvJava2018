package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

/**
 * Text parser class loads information into a phone bill from a .txt file path
 */
public class TextParser implements PhoneBillParser<PhoneBill> {

  /**
   * Field for the buffered reader used for file IO
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

  /**
   * Reads the contents of the .txt file and creates a phone bill object with the contents
   * @return  The phone bill object created from the .txt
   * @throws ParserException  When there is an error reading the file
   */
  @Override
  public PhoneBill parse() throws ParserException {
    String name;
    PhoneBill bill;
    String call;
    try {
      name = buffReader.readLine();
      bill = new PhoneBill(name);
      while ((call = buffReader.readLine()) != null) {
        String [] pieces = call.split(" ");
        var phoneCall = new PhoneCall(pieces[0], pieces[1], pieces[2], pieces[3], pieces[4],
            pieces[5],pieces[6],pieces[7]);
        bill.addPhoneCall(phoneCall);
      }
    } catch (IOException | NullPointerException | ParseException e) {
      throw new ParserException("There was an error reading the file");
    }
    return bill;
  }
}
