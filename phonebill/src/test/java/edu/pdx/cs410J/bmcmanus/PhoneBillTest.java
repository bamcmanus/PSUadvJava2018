package edu.pdx.cs410J.bmcmanus;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneCall} class.
 */
public class PhoneBillTest {
    @Test
    public void customerHasNameGiven() {
        var bill = new PhoneBill("Bill");
        assertThat(bill.getCustomer(), equalTo("Bill"));
    }
}
