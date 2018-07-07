package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import java.util.Collection;

/**The main class for the CS410J Phone Bill Project*/
public class Project1 {

    public static void main(String[] args) {
	    int numArgs = args.length;
	    int i = 0;
	    boolean print = false;

	    while (i < numArgs && args[i].startsWith("-")) {
		    if (args[i].equalsIgnoreCase("-README")) {
			    String README = "Author: Brent McManus; Assignment: Homework #1\n" +
					    "This program takes information about a call and a customer's name.  A phone bill is created\n"+
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
		    if (args[i].equalsIgnoreCase("-print")) {
			    print = true;
		    }
		    ++i;
	    }
	    if (numArgs - i < 7) {
		    System.err.println("Missing command line arguments Usage\nUsage: " +
				    "java edu.pdx.cs410J.<login-id>.Project1 [options] <args> \n" +
				    "args in order: customer callerNumber calleeNumber startDate startTime endDate endTime\n" +
				    "options: -print, prints description of new phone call, -README prints a project README and exits");
		    System.exit(1);
	    }
	    if (numArgs - i > 7) {
		    System.err.println("Too many command line arguments Usage\nUsage: " +
				    "java edu.pdx.cs410J.<login-id>.Project1 [options] <args> \n" +
				    "args in order: customer callerNumber calleeNumber startDate startTime endDate endTime\n" +
				    "options: -print, prints description of new phone call, -README prints a project README and exits");
		    System.exit(1);
	    }
	    try {
		    var call = new PhoneCall(args[i + 1], args[i + 2], args[i + 3], args[i + 4], args[i + 5], args[i + 6]);
		    var bill = new PhoneBill(args[i]);
		    bill.addPhoneCall(call);
		    if (print) {
			    System.out.println(call.toString());
		    }
	    } catch (IllegalArgumentException error) {
		    System.err.println("Error: " + error.getLocalizedMessage());
		    System.exit(1);
	    }
	    System.exit(0);
    }
}