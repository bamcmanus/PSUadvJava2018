package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * PhoneBill encapsulates all of the data associated with a single phone bill.  It has a customer name and a list
 * of calls made associated with that customer.
 *
 * @author Brent McManus
 * @see PhoneCall
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
	/**
	 * Field for the name of a customer
	 */
	String customer;
	/**
	 * Field for a list of phoneCall objects
	 */
	private Collection<PhoneCall> calls = new ArrayList<>();

	/**
	 * Creates a new phone bill associated with the name argument
	 *
	 * @param customerName  The phone bill customer's name
	 * @throws IllegalArgumentException when the argument is the empty string
	 */
	public PhoneBill(String customerName) {
		if (customerName == "") {
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
