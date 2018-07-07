package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import java.util.Collection;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

    public static void main(String[] args) {
        int numArgs = args.length;
        if (numArgs == 1 || numArgs == 7 || numArgs == 8 || numArgs == 9) {
            if (args[0].equalsIgnoreCase("-README") || args[1].equalsIgnoreCase("-README")) {
                String README = "Author: Brent McManus; Assignment: Homework #1\n" +
                        "This program takes information about a call and a customer's name.  A phone bill is created\n" +
                        "and associated with the customer's name and a phone call is created and added to the phone\n" +
                        "bill.\n" +
                        "usage: java edu.pdx.cs410J.bmcmanus.Project1 [options] <args>\n" +
                        "  args are (in this order):\n" +
                        "    customer     Person whose phone bill we’re modeling\n" +
                        "    callerNumber Phone number of caller\n" +
                        "    calleeNumber Phone number of person who was called\n" +
                        "    startTime    Date and time call began (24-hour time)\n" +
                        "    endTime      Date and time call ended(24-hour time)\n" +
                        "  options are (options may appear in any order):\n" +
                        "    -print       Prints a description of the new phone call\n" +
                        "    -README      Prints a README for this project and exits\n" +
                        "  Date and time should be in the format: mm/dd/yyyy hh:mm";
                System.out.println(README);
                System.exit(0);
            }

            if (args[0].equalsIgnoreCase("-print")) {
                try {
                    var call = new PhoneCall(args[2], args[3], args[4], args[5], args[6], args[7]);
                    var bill = new PhoneBill(args[1]);
                    bill.addPhoneCall(call);
                    System.out.println(call.toString());
                } catch (IllegalArgumentException error) {
                    System.out.println(error);
                    System.exit(1);
                }
            } else {
                try {
                    var call = new PhoneCall(args[1], args[2], args[3], args[4], args[5], args[6]);
                    var bill = new PhoneBill(args[0]);

                    bill.addPhoneCall(call);
                    Collection<PhoneCall> PhoneCalls = bill.getPhoneCalls();
                } catch (IllegalArgumentException error) {
                    System.out.println(error);
                    System.exit(1);
                }
            }
        } else {
            System.err.println("Missing command line arguments Usage\nUsage: " +
                    "java edu.pdx.cs410J.<login-id>.Project1 [options] <args> \n" +
                    "args in order: customer callerNumber calleeNumber startDate startTime endDate endTime\n" +
                    "options: -print, prints description of new phone call, -README prints a project README and exits");
            System.exit(1);
        }
        System.exit(0);
    }
}