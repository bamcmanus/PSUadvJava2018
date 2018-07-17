package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.PhoneBillDumper;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.io.File;

public class TextDumper implements PhoneBillDumper<PhoneBill> {
  private PrintWriter printWriter;

  TextDumper(File filename) throws IOException {
    printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
  }

  @Override
  public void dump(PhoneBill phoneBill) throws IOException {
    try {
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
    } catch (IllegalArgumentException e) {
      throw new IOException("There was an error writing the file.");
    }
  }
}
