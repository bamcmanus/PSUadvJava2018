package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import java.io.IOException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main class for the CS410J Phone Bill Project 1
 *
 * @author Brent McManus
 * @see PhoneCall
 * @see PhoneBill
 */
public class Project2 {

  /**
   * Field for the README message for project 1
   */
  static final String README = "Author: Brent McManus; Assignment: Homework #1\n\n" +
      "This program takes information about a call and a customer's name.  A phone bill is created\n"
      +
      "and associated with the customer's name and a phone call is created and added to the phone\n"
      +
      "bill. The class allows an option to save and load to a .txt.\n\n" +
      "Usage: java edu.pdx.cs410J.bmcmanus.Project2 [options] <args> \n" +
      "  args in order: customer callerNumber calleeNumber startDate startTime endDate endTime\n" +
      "  options: -textFile file 		Read/write the phone bill to file argument\n" +
      "           -print    				Prints a description of the new phone call\n" +
      "           -README   				Prints a project README and exits" +
			"  Date and time should be in the format: mm/dd/yyyy hh:mm";

  public static void main(String[] args) {
    int i = 0;
    boolean print = false;
    boolean file = false;
    String fileName = null;

    while (i < args.length && args[i].startsWith("-")) {
      switch (args[i]) {
        case "-README":
          System.out.println(README);
          System.exit(0);
          break;

        case "-textFile":
          if (args[i + 1].startsWith("-") || (i+1) >= args.length) {
            System.err.println(("No file was found after the -textFile option"));
            System.exit(1);
          }
          fileName = args[i+1];
          file = true;
          ++i;
          break;

        case "-print":
          print = true;
          break;

        default:
          System.err.println("Not a valid option\nAllowable options: -README, -print, -textFile");
          System.exit(1);
          break;
      }
      ++i;
    }

    if (args.length - i < 7) {
      System.err.println("Missing command line arguments\nUsage: " +
          "java edu.pdx.cs410J.bmcmanus.Project2 [options] <args> \n" +
          "  args in order: customer callerNumber calleeNumber startDate startTime endDate endTime\n"
          +
          "  options: -textFile file 		Read/write the phone bill to file argument\n" +
          "           -print    				Prints a description of the new phone call\n" +
          "           -README   				Prints a project README and exits");
      System.exit(1);
    }

    if (args.length - i > 7) {
      System.err.println("Too many command line arguments\nUsage: " +
          "java edu.pdx.cs410J.bmcmanus.Project2 [options] <args> \n" +
          "  args in order: customer callerNumber calleeNumber startDate startTime endDate endTime\n"
          +
          "  options: -textFile file 		Read/write the phone bill to file argument\n" +
          "           -print    				Prints a description of the new phone call\n" +
          "           -README   				Prints a project README and exits");
      System.exit(1);
    }

    try {
      var call = new PhoneCall(args[i + 1], args[i + 2], args[i + 3], args[i + 4], args[i + 5],
          args[i + 6]);
      var bill = new PhoneBill(args[i]);
      bill.addPhoneCall(call);

      if (print) {
        System.out.println(call.toString());
      }
      
      if (file) {
        var dump = new TextDumper(fileName);
        try {
          dump.dump(bill);
        } catch (IOException e) {
          System.err.println("Error: " + e.getLocalizedMessage());
          System.exit(1);
        }

      }
    } catch (IllegalArgumentException error) {
      System.err.println("Error: " + error.getLocalizedMessage());
      System.exit(1);
    }

    System.exit(0);
  }
}