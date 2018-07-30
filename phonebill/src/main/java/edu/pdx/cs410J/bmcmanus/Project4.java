package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the Phone Bill server using
 * REST.
 */
public class Project4 {

  public static final String MISSING_ARGS = "Missing command line arguments";

  public static void main(String... args) {
    String hostName = null;
    String portString = null;
    String word = null;
    String definition = null;

    for (String arg : args) {
      if (hostName == null) {
        hostName = arg;

      } else if (portString == null) {
        portString = arg;

      } else if (word == null) {
        word = arg;

      } else if (definition == null) {
        definition = arg;

      } else {
        usage("Extraneous command line argument: " + arg);
      }
    }

    if (hostName == null) {
      usage(MISSING_ARGS);

    } else if (portString == null) {
      usage("Missing port");
    }

    int port;
    try {
      port = Integer.parseInt(portString);

    } catch (NumberFormatException ex) {
      usage("Port \"" + portString + "\" must be an integer");
      return;
    }

    PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

    String message;
    try {
      //Print customer phone bill
      String customerName = "Customer";
      message = client.getPrettyPhoneBill(customerName);
    } catch (IOException ex) {
      error("While contacting server: " + ex);
      return;
    }

    System.out.println(message);

    System.exit(0);
  }

  /**
   * Makes sure that the give response has the expected HTTP status code
   *
   * @param code The expected status code
   * @param response The response from the server
   */
  private static void checkResponseCode(int code, HttpRequestHelper.Response response) {
    if (response.getCode() != code) {
      error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
          response.getCode(), response.getContent()));
    }
  }

  private static void error(String message) {
    PrintStream err = System.err;
    err.println("** " + message);

    System.exit(1);
  }

  /**
   * Prints usage information for this program and exits
   *
   * @param message An error message to print
   */
  private static void usage(String message) {
    PrintStream err = System.err;
    err.println("** " + message);
    err.println();
    err.println("usage: java edu.pdx.cs410J.<login-id>.Project4 [options] <args>");
    err.println("  options(may appear in any order):");
    err.println("    -host hostname   Host computer on which the server runs");
    err.println("    -port port       Port on which the server is listening");
    err.println("    -search          Phone calls should be searched for");
    err.println("    -print           Prints a description of the new phone call");
    err.println("    -README          Prints a README for this project and exits");
    err.println("  args (in this order):");
    err.println("    customer         Person whose phone bill we’re modeling");
    err.println("    callerNumber     Phone number of caller");
    err.println("    calleeNumber     Phone number of person who was called");
    err.println("    startTime        Date and time call began");
    err.println("    endTime          Date and time call ended");
    err.println("  Date and time should be in the format: mm/dd/yyyy hh:mm am/pm");
    err.println();
    err.println("This simple program posts phone bills and their calls");
    err.println("to the server.");
    err.println("If no phone call is added then the customer's entire bill");
    err.println("is printed.");
    err.println("If no customer is specified, all customer names will be displayed");
    err.println();

    System.exit(1);
  }
}