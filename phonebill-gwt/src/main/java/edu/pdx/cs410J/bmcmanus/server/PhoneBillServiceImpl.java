package edu.pdx.cs410J.bmcmanus.server;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.bmcmanus.client.PhoneBill;
import edu.pdx.cs410J.bmcmanus.client.PhoneCall;
import edu.pdx.cs410J.bmcmanus.client.PhoneBillService;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * The server-side implementation of the Phone Bill service
 */
public class PhoneBillServiceImpl extends RemoteServiceServlet implements PhoneBillService
{
  private final Map<String, PhoneBill> bills = new HashMap<>();

  /**
   * gets the phone bill of a customer
   * @param customerName  customer name of the bill requested
   * @return              the phone bill for the customer
   */
  @Override
  public PhoneBill getPhoneBill(String customerName) {
    PhoneBill phonebill = getBill(customerName);
    if (phonebill == null) {
      throw new NoSuchElementException("No bill for that customer name");
    }
    return phonebill;
  }

  /**
   * Adds a phone call to a bill if a bill already exists else creates a bill and adds the call
   * @param customerName  customer whose bill the call should be added to
   * @param call          call to be added to phone bill
   */
  @Override
  public void addPhoneCall(String customerName, PhoneCall call) {
    PhoneBill bill = getBill(customerName);
    if (bill == null) {
      bill = new PhoneBill(customerName);
      bills.put(customerName,bill);
    }
    bill.addPhoneCall(call);
  }

  /**
   * Log unhandled exceptions to standard error
   *
   * @param unhandled
   *        The exception that wasn't handled
   */
  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }

  /**
   * attempts to fetch a phone bill from the bills container
   * @param customer  customer name for which we are requesting the bill
   * @return          bill if customer exists else null
   */
  @VisibleForTesting
  private PhoneBill getBill(String customer) {
    return this.bills.get(customer);
  }
}
