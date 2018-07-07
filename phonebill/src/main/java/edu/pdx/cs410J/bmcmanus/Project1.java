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
            String readme = "This program takes a name, caller number, called number, start date & time and end date & time.\n" +
                    "The information is used to create a phone call.  The phone call is added to a phone bill of a customer\n" +
                    "with the name specified.";

            //checks for readme condition and prints readme and exits program if found.
            if (args[0].equalsIgnoreCase("-README") || args[1].equalsIgnoreCase("-README")) {
                System.out.println(readme);
                System.exit(0);
            }

            if (args[0].equalsIgnoreCase("-print")) {
                var call = new PhoneCall(args[2], args[3], args[4], args[5], args[6], args[7]);  // Refer to one of Dave's classes so that we can be sure it is on the classpath
                var bill = new PhoneBill(args[1]);

                bill.addPhoneCall(call);
                Collection<PhoneCall> PhoneCalls = bill.getPhoneCalls();

                System.out.println(call.toString());
            } else {
                var call = new PhoneCall(args[1], args[2], args[3], args[4], args[5], args[6]);  // Refer to one of Dave's classes so that we can be sure it is on the classpath
                var bill = new PhoneBill(args[0]);

                bill.addPhoneCall(call);
                Collection<PhoneCall> PhoneCalls = bill.getPhoneCalls();
            }

            System.exit(1);

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