package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.AbstractPhoneBill;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

    public static void main(String[] args) {
        PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
        if(args.length <= 0)
		    System.err.println("Missing command line arguments Usage\nUsage: " +
            "java edu.pdx.cs410J.<login-id>.Project1 [options] <args> \n" +
            "args in order: customer callerNumber calleeNumber startDate startTime endDate endTime\n" +
            "options: -print, prints description of new phone call, -README prints a project README and exits");
        for (String arg : args)
            System.out.println(arg);
        System.exit(1);
    }

}