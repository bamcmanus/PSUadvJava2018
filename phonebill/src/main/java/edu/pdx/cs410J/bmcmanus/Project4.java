package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the Phone Bill server using
 * REST.
 */
public class Project4 {

  static final String MISSING_ARGS = "Missing command line arguments";

  public static void main(String... args) {
    String hostName = null;
    String portString = null;


    boolean print = false;
    boolean search = false;

    int i = 0;
    while (i < args.length && args[i].startsWith("-")) {
      switch (args[i]) {
        case "-README":
          readme();
          break;
        case "-print":
          print = true;
          break;
        case "-search":
          search = true;
          break;
        case "-host":
          hostName = args[i + 1];
          ++i;
          break;
        case "-port":
          portString = args[i + 1];
          ++i;
          break;
        default:
          System.err.println(
              "Not a valid option\nAllowable options: -README, -print, -search, -host hostname, - port port");
          System.exit(1);
          break;
      }
      ++i;
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

    int argsLength = args.length - i;
    if (argsLength != 9 && argsLength != 1 && argsLength != 7) {
      usage("Incorrect number of command line args");
    }

    PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);
    String message = null;

    if (search) {
      if (argsLength != 7) {
        usage("Incorrect number of command line args");
      }
      try {
        var call = new PhoneCall("111-111-1111", "111-111-1111", args[i + 1], args[i + 2], args[i + 3],
            args[i + 4], args[i + 5], args[i + 6]);
        Date sDate = call.getStartTime();
        Date eDate = call.getEndTime();
        message = client.getPrettyBetweenDates(args[i], sDate, eDate);
      } catch (IOException | ParseException e) {
        usage(e.getLocalizedMessage());
      } catch (NoSuchPhoneBillException e) {
        System.err.println("Customer not found");
      }
    } else {
      switch (argsLength) {
        case 1:
          try {
            message = client.getPrettyPhoneBill(args[i]);
          }catch (NoSuchPhoneBillException e) {
            System.err.println("Customer not found");
          } catch (IOException e) {
            usage(e.getLocalizedMessage());
          }
          break;
        case 9:
          try {
            var call = new PhoneCall(args[i + 1], args[i + 2], args[i + 3], args[i + 4],
                args[i + 5],
                args[i + 6], args[i + 7], args[i + 8]);
            client.addPhoneCall(args[i], call);
            if (print) {  //print option was invoked
              System.out.println(call.toString());
            }
          } catch (ParseException | IOException e) {
            usage(e.getLocalizedMessage());
          }
          break;
        default:
          usage("Incorrect number of command line args");
          break;
      }
    }

    if (message != null)
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

  private static void readme() {
    PrintStream out = System.out;
    out.println("usage: java edu.pdx.cs410J.<login-id>.Project4 [options] <args>");
    out.println("  options(may appear in any order):");
    out.println("    -host hostname   Host computer on which the server runs");
    out.println("    -port port       Port on which the server is listening");
    out.println("    -search          Phone calls should be searched for");
    out.println("    -print           Prints a description of the new phone call");
    out.println("    -README          Prints a README for this project and exits");
    out.println("  args (in this order):");
    out.println("    customer         Person whose phone bill we’re modeling");
    out.println("    callerNumber     Phone number of caller");
    out.println("    calleeNumber     Phone number of person who was called");
    out.println("    startTime        Date and time call began");
    out.println("    endTime          Date and time call ended");
    out.println("  Date and time should be in the format: mm/dd/yyyy hh:mm am/pm");
    out.println();
    out.println("This simple program posts phone bills and their calls");
    out.println("to the server.");
    out.println("If no phone call is added then the customer's entire bill");
    out.println("is printed.");
    out.println("If no customer is specified, all customer names will be displayed");
    out.println();
    System.exit(0);
  }
}