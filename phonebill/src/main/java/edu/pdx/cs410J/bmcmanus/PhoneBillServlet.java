package edu.pdx.cs410J.bmcmanus;

import com.google.common.annotations.VisibleForTesting;

import edu.pdx.cs410J.web.HttpRequestHelper.Response;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple bills of words and their definitions.
 */
public class PhoneBillServlet extends HttpServlet {

  static final String CUSTOMER_PARAMETER = "customer";
  static final String CALLER_PARAMETER = "caller";
  static final String CALLEE_PARAMETER = "callee";
  static final String END_TIME_PARAMETER = "endTime";
  static final String START_TIME_PARAMETER = "startTime";

  private final Map<String, PhoneBill> bills = new HashMap<>();

  /**
   * Handles an HTTP GET request from a client by writing the definition of the word specified in
   * the "word" HTTP parameter to the HTTP response.  If the "word" parameter is not specified, all
   * of the entries in the bills are written to the HTTP response.
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setContentType("text/plain");

    String customer = getParameter(CUSTOMER_PARAMETER, request);
    writePrettyPhoneBill(customer,response);
  }

  private void writePrettyPhoneBill(String customer, HttpServletResponse response)
      throws IOException {
    var bill = getPhoneBill(customer);
    if (bill == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } else {
      PrintWriter writer = response.getWriter();
      writer.println(bill.getCustomer());
      bill.getPhoneCalls().forEach((call) -> writer.println(call.toString()));
      response.setStatus(HttpServletResponse.SC_OK);
    }
  }

  /**
   * Handles an HTTP POST request by storing the bills entry for the "word" and "definition"
   * request parameters.  It writes the bills entry to the HTTP response.
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/plain");

    String customer = getParameter(CUSTOMER_PARAMETER, request);

    if (customer == null) {
      missingRequiredParameter(response, CUSTOMER_PARAMETER);
      return;
    }

    var bill = getPhoneBill(customer);
    if (bill == null) {
      bill = new PhoneBill(customer);
      addPhoneBill(bill);
    }

    String caller = getParameter(CALLER_PARAMETER, request);
    String callee = getParameter(CALLEE_PARAMETER, request);
    String startTime = getParameter(START_TIME_PARAMETER, request);
    String endTime = getParameter(END_TIME_PARAMETER, request);

    Date startDate = new Date(Long.parseLong(startTime));
    Date endDate = new Date(Long.parseLong(endTime));

    var call = new PhoneCall(caller,callee,startDate,endDate);
    bill.addPhoneCall(call);

    response.setStatus(HttpServletResponse.SC_OK);
  }

  /**
   * Handles an HTTP DELETE request by removing all bills entries.  This behavior is exposed
   * for testing purposes only.  It's probably not something that you'd want a real application to
   * expose.
   */
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/plain");

    this.bills.clear();

    PrintWriter pw = response.getWriter();
    pw.println(Messages.allDictionaryEntriesDeleted());
    pw.flush();

    response.setStatus(HttpServletResponse.SC_OK);

  }

  /**
   * Writes an error message about a missing parameter to the HTTP response.
   *
   * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
   */
  private void missingRequiredParameter(HttpServletResponse response, String parameterName)
      throws IOException {
    String message = Messages.missingRequiredParameter(parameterName);
    response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
  }

  /**
   * Returns the value of the HTTP request parameter with the given name.
   *
   * @return <code>null</code> if the value of the parameter is
   * <code>null</code> or is the empty string
   */
  private String getParameter(String name, HttpServletRequest request) {
    String value = request.getParameter(name);
    if (value == null || "".equals(value)) {
      return null;

    } else {
      return value;
    }
  }

  @VisibleForTesting
  PhoneBill getPhoneBill(String customer) {
    return this.bills.get(customer);
  }

  @VisibleForTesting
  public void addPhoneBill(PhoneBill bill) {
    this.bills.put(bill.getCustomer(),bill);
  }
}
