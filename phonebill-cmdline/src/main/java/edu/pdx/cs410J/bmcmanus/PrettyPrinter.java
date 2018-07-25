package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.PhoneBillDumper;
import java.io.IOException;
import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {

  private BufferedWriter writer;

  PrettyPrinter() {
  }

  PrettyPrinter(String filename) throws IOException {
    Path logFile = Paths.get(filename);
    Charset charset = Charset.forName("US-ASCII");
    try {
      writer = Files.newBufferedWriter(logFile, charset);
    } catch (IOException e) {
      throw new IOException("There was an issue writing to the file");
    }
  }

  @Override
  public void dump(PhoneBill phoneBill) throws IOException {
    try {
      Collection<PhoneCall> list = phoneBill.getPhoneCalls();
      long eTime, sTime, diff;
      writer.write("Phone Bill for: " + phoneBill.customer + "\n\n");
      writer.write("Call From:          To:                 Start Time:           End Time:             Duration(minutes):\n");
      for (PhoneCall call : list) {
        writer.write(call.callerNum + "        " + call.calleeNum + "        ");
        writer.write(new SimpleDateFormat("mm/dd/yy hh:mm a").format(call.getStartTime())
            + "     ");
        writer.write(new SimpleDateFormat("mm/dd/yy hh:mm a").format(call.getEndTime())
            + "     ");
        eTime = call.getEndTime().getTime();
        sTime = call.getStartTime().getTime();
        diff = eTime - sTime;
        writer.write(TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS) + "");
        writer.newLine();
      }
      writer.close();
    } catch (IllegalArgumentException e) {
      throw new IOException("There was an error writing the file.");
    }
  }

  void stdOut(PhoneBill phoneBill) {
    Collection<PhoneCall> list = phoneBill.getPhoneCalls();
    long eTime, sTime, diff;
    System.out.println("Phone Bill for: " + phoneBill.customer + "\n");
    System.out.println("Call From:          To:                 Start Time:           End Time:             Duration(minutes):\n");
    for (PhoneCall call : list) {
      System.out.print(call.callerNum + "        " + call.calleeNum + "        ");
      System.out.print(new SimpleDateFormat("mm/dd/yy hh:mm a").format(call.getStartTime())
          + "     ");
      System.out.print(new SimpleDateFormat("mm/dd/yy hh:mm a").format(call.getEndTime())
          + "     ");
      eTime = call.getEndTime().getTime();
      sTime = call.getStartTime().getTime();
      diff = eTime - sTime;
      System.out.println(TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS) + "");
    }
  }

  public static void main(String[] args) throws ParseException, IOException {
    var bill = new PhoneBill("Brent");
    var call = new PhoneCall("123-456-7890", "098-765-4321", "12/12/18", "12:12", "AM", "12/12/18", "12:13", "AM");
    bill.addPhoneCall(call);
    call = new PhoneCall("123-456-7890", "098-765-4321", "12/13/18", "12:12", "AM", "12/13/18", "12:13", "AM");
    bill.addPhoneCall(call);
    call = new PhoneCall("023-456-7890", "098-765-4321", "12/12/18", "12:12", "AM", "12/12/18", "12:13", "AM");
    bill.addPhoneCall(call);

    var printer = new PrettyPrinter("pretty.txt");
    printer.dump(bill);

  }
}
