package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents a phone bill for homework 1
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    private String customer;
    private Collection<PhoneCall> calls = new ArrayList<>();

    /**
     * Creates a new phone bill
     * @param customerName The phone bill customer's name
     */
    public PhoneBill(String customerName) {
        this.customer = customerName;
    }

    /**
     * Gets the name of the customer
     * @return String
     */
    @Override
    public String getCustomer() {
        return this.customer;
    }

    /**
     * Adds a phone call to the phone bill
     * @param call Phone call to bea added to the bill
     */
    @Override
    //adds a phone call to this phone bill
    public void addPhoneCall(PhoneCall call) {
        this.calls.add(call);
    }

    /**
     * Gets teh entire phone call list associated with the phone bill
     * @return Collection of phone calls
     */
    @Override
    //returns all of the phone calls (as of instaces of {@link AbstractPhoneCall}) in this phone bill
    public Collection<PhoneCall> getPhoneCalls() {
        return this.calls;
    }
}
