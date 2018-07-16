package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.PhoneBillDumper;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextDumper implements PhoneBillDumper<PhoneBill> {
  String filename;
  FileWriter writer;
  PrintWriter printWriter;

  boolean checkFileNameFormat(String fileName) {
    String txtPattern = "(^\\w+\\.txt$)";
    Pattern pattern = Pattern.compile(txtPattern);
    Matcher matcher = pattern.matcher(fileName);
    return matcher.matches();
  }

  public TextDumper(String fileName) {
    if (checkFileNameFormat(fileName)) {
      filename = fileName;
      try {
        writer = new FileWriter(filename);
        printWriter = new PrintWriter(writer);
      } catch (IOException e) {
        System.out.println("Error when writing file.");
        System.exit(0);
      }
    } else
      throw new IllegalArgumentException("Invalid file name. Must be alpha numeric followed by .txt");
  }

  @Override
  public void dump(PhoneBill phoneBill) throws IOException {
    printWriter.println(phoneBill.customer);
    Collection<PhoneCall> list = phoneBill.getPhoneCalls();
    Iterator<PhoneCall> iterator = list.iterator();
    PhoneCall call;
    while (iterator.hasNext()) {
      call = iterator.next();
      printWriter.println(call.callerNum);
      printWriter.println(call.calleeNum);
      printWriter.println(call.startDate);
      printWriter.println(call.startTime);
      printWriter.println(call.endDate);
      printWriter.println(call.endTime);
    }
    printWriter.close();
  }
}
