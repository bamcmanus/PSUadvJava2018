package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.PhoneBillDumper;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.io.File;
import java.nio.file.*;
import java.nio.charset.*;

/**
 * Text Dumper class is responsible for writing the contents of a phone bill to .txt
 */
public class TextDumper implements PhoneBillDumper<PhoneBill> {

  /**
   * Field for a print writer used for file IO
   */
  //private PrintWriter printWriter;
  private BufferedWriter writer;
  /**
   * Constructor initializes the print writer with a buffered writer given the path
   * @param filename The path of a file
   * @throws IOException when the file is not found or corrupted
   */
  TextDumper(String filename) throws IOException {
    Path logFile = Paths.get(filename);
    Charset charset = Charset.forName("US-ASCII");
    try {
      //printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
      //printWriter = new BufferedWriter(new FileWriter(filename));
      writer = Files.newBufferedWriter(logFile, charset);
    } catch (IOException e) {
      throw new IOException("There was an issue writing to the file");
    }
  }

  /**
   * Writes the contents of a phone bill to a .txt
   * @param phoneBill The phone bill that is written to the .txt
   * @throws IOException when there is an issue writing the file
   */
  @Override
  public void dump(PhoneBill phoneBill) throws IOException {
    try {
      writer.write(phoneBill.customer);
      writer.newLine();
      Collection<PhoneCall> list = phoneBill.getPhoneCalls();
      Iterator<PhoneCall> iterator = list.iterator();
      PhoneCall call;
      while (iterator.hasNext()) {
        call = iterator.next();
        writer.write(call.callerNum);
        writer.newLine();
        writer.write(call.calleeNum);
        writer.newLine();
        writer.write(call.startDate);
        writer.newLine();
        writer.write(call.startTime);
        writer.newLine();
        writer.write(call.endDate);
        writer.newLine();
        writer.write(call.endTime);
        writer.newLine();
      }
      writer.close();
    } catch (IllegalArgumentException e) {
      throw new IOException("There was an error writing the file.");
    }
  }
}
