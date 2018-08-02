package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {
  /**
   * Writes a PhoneBill object to a file in a pretty, human readable layout
   * @param phoneBill     the PhoneBill to be pretty printed
   * @throws IOException  when passed an illegal argument
   */
  void dump(PhoneBill phoneBill, PrintWriter writer) throws IOException {
    try {
      Collection<PhoneCall> list = phoneBill.getPhoneCalls();
      long eTime, sTime, diff;
      writer.println("Phone Bill for: " + phoneBill.customer);
      writer.println("Call From:          To:                 Start Time:           End Time:             Duration(minutes):\n");
      for (PhoneCall call : list) {
        writer.print(call.callerNum + "        " + call.calleeNum + "        ");
        writer.print(new SimpleDateFormat("MM/dd/yy hh:mm a").format(call.getStartTime())
            + "     ");
        writer.print(new SimpleDateFormat("MM/dd/yy hh:mm a").format(call.getEndTime())
            + "     ");
        eTime = call.getEndTime().getTime();
        sTime = call.getStartTime().getTime();
        diff = eTime - sTime;
        writer.println(TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS) + "");
      }
      writer.close();
    } catch (IllegalArgumentException e) {
      throw new IOException("There was an error writing the file.");
    }
  }

  @Override
  public void dump(PhoneBill bill) {
  }
}
