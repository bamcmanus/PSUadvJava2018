package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.io.File;
import java.text.ParseException;



/**
 * The main class for the CS410J Phone Bill Project 1
 *
 * @author Brent McManus
 * @see PhoneCall
 * @see PhoneBill
 */
public class Project3 {

  /**
   * Field for the README message for project 3
   */
  private static final String usage =       "Usage: java edu.pdx.cs410J.bmcmanus.Project3 [options] <args> \n" +
      "args are (in this order):\n" +
      "   customer          Person whose phone bill we’re modeling\n" +
      "   callerNumber      Phone number of caller\n" +
      "   calleeNumber      Phone number of person who was called\n" +
      "   startTime         Date and time (am/pm) call began\n" +
      "   endTime           Date and time (am/pm) call ended\n" +
      "options (may appear in any order:\n" +
      "   -pretty   file    Pretty print the phone bill to a text file or standard out (file -).\n" +
      "   -textFile file 		Read/write the phone bill to file argument\n" +
      "   -print    				Prints a description of the new phone call\n" +
      "   -README   				Prints a project README and exits" +
      "  Date and time should be in the format: mm/dd/yyyy hh:mm";

  /**
   * Field for the usage message for project 3
   */
  static final String README = "Author: Brent McManus; Assignment: Homework #1\n\n" +
      "This program takes information about a call and a customer's name.  A phone bill is created\n"
      +
      "and associated with the customer's name and a phone call is created and added to the phone\n"
      +
      "bill. The class allows an option to save and load to a .txt.\n\n" + usage;

  public static void main(String[] args) {
    int i = 0;
    boolean print = false;
    boolean file = false;
    String filename = null;
    boolean prettyPrint = false;
    String prettyFile = null;
    boolean prettySout = false;


    while (i < args.length && args[i].startsWith("-")) {
      switch (args[i]) {
        case "-README":
          System.out.println(README);
          System.exit(0);
          break;
        case "-print":
          print = true;
          break;
        case "-textFile":
          if (args[i + 1].startsWith("-") || (i + 1) >= args.length) {
            System.err.println(("No file was found after the -textFile option"));
            System.exit(1);
          }
          filename = args[i + 1];
          file = true;
          ++i;
          break;
        case "-pretty":
          if (args[i + 1].equalsIgnoreCase("-")) {
            prettyPrint = true;
            prettySout = true;
          } else if (args[i + 1].startsWith("-") || (i + 1) >= args.length) {
            System.err.println(("No file was found after the -pretty option"));
            System.exit(1);
          } else {
            prettyFile = args[i + 1];
            prettyPrint = true;
          }
          ++i;
          break;
        default:
          System.err.println("Not a valid option\nAllowable options: -README, -print, -textFile");
          System.exit(1);
          break;
      }
      ++i;
    }

    if (args.length - i < 9) {
      System.err.println("Missing command line arguments\n" + usage);
      System.exit(1);
    }

    if (args.length - i > 9) {
      System.err.println("Too many command line arguments\n" + usage);
      System.exit(1);
    }

    try {
      PhoneBill bill;
      File f;

      if (file && (f = new File(filename)).exists()) { //file option was invoked and the file exists
        var parser = new TextParser(f);  //create text parser
        bill = parser.parse(); //attempt to parse the file

        if (!bill.customer.equalsIgnoreCase(args[i])) { //name in bill doesn't match arg name
          System.err
              .println("The customer name in the file does not match the customer name arg.");
          System.exit(1);
        }
      } else { //if no file option create new bill
        bill = new PhoneBill(args[i]);
      }

      //create the new call
      var call = new PhoneCall(args[i + 1], args[i + 2], args[i + 3], args[i + 4], args[i + 5],
          args[i + 6], args[i + 7],args[i + 8]);
      bill.addPhoneCall(call);  //add the call to the phone bill

      if (print) {  //print option was invoked
        System.out.println(call.toString());
      }

      if (file) { //file option was invoked write the bill to the file
        var dump = new TextDumper(filename);
        dump.dump(bill);
      }

      if (prettyPrint) {
        if (prettySout) {
          var pPrint = new PrettyPrinter();
          pPrint.stdOut(bill);
        } else {
          var pPrint = new PrettyPrinter(prettyFile);
          pPrint.dump(bill);
        }
      }

    } catch (IllegalArgumentException | ParseException | ParserException | IOException e) {
      System.err.println("Error: " + e.getLocalizedMessage());
      System.exit(1);
    }

    System.exit(0);
  }
}