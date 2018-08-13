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

  @Override
  public PhoneBill getPhoneBill(String customerName) {
    PhoneBill phonebill = getBill(customerName);
    if (phonebill == null) {
      throw new NoSuchElementException("No bill for that customer name");
    }
    return phonebill;
  }

  @Override
  public void throwUndeclaredException() {
    throw new IllegalStateException("Expected undeclared exception");
  }

  @Override
  public void throwDeclaredException() throws IllegalStateException {
    throw new IllegalStateException("Expected declared exception");
  }

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

  @VisibleForTesting
  private PhoneBill getBill(String customer) {
    return this.bills.get(customer);
  }
}
