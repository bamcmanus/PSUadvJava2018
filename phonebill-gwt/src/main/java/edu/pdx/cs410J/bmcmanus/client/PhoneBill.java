package edu.pdx.cs410J.bmcmanus.client;

import edu.pdx.cs410J.AbstractPhoneBill;
import java.util.Collection;
import java.util.Objects;
import java.util.TreeSet;

/**
 * PhoneBill encapsulates all of the data associated with a single phone bill.  It has a customer
 * name and a list of calls made associated with that customer.
 *
 * @author Brent McManus
 * @see PhoneCall
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {

  /**
   * Field for the name of a customer
   */
  private String customer;

  /**
   * Field for a list of phoneCall objects
   */
  private TreeSet<PhoneCall> calls = new TreeSet<>();
  /* Comparator.comparing(PhoneCall::getStartTime).thenComparing(PhoneCall::getCaller)*/

  /**
   * default constructor required for gwt
   */
  PhoneBill() {
  }

  /**
   * Creates a new phone bill associated with the name argument
   *
   * @param customerName The phone bill customer's name
   * @throws IllegalArgumentException when the argument is the empty string
   */
  public PhoneBill(String customerName) {
    if (Objects.equals(customerName, "")) {
      throw new IllegalArgumentException("Name cannot be empty string.");
    }
    this.customer = customerName;
  }

  /**
   * Gets the name of the customer
   *
   * @return String with containing the customer's name
   */
  @Override
  public String getCustomer() {
    return this.customer;
  }

  /**
   * Adds a phone call to the phone bill
   *
   * @param call Phone call to be added to the bill
   */
  @Override
  public void addPhoneCall(PhoneCall call) {
    this.calls.add(call);
  }

  /**
   * Gets the entire phone call list associated with the phone bill
   *
   * @return Collection of phone calls
   */
  @Override
  public Collection<PhoneCall> getPhoneCalls() {
    return this.calls;
  }
}
