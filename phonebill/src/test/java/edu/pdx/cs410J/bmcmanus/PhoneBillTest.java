package edu.pdx.cs410J.bmcmanus;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Unit tests for the {@link PhoneCall} class.
 */
public class PhoneBillTest {
    String name = "Bill";

    @Test
    public void customerHasNameGiven() {
        var bill = new PhoneBill(name);
        assertThat(bill.getCustomer(), equalTo(name));
    }

    @Test
    public void phoneCallListStartsEmpty () {
        var bill = new PhoneBill(name);
        Collection<PhoneCall> calls = bill.getPhoneCalls();
        assertThat(calls.size(), equalTo(0));
    }

    @Test
    public void phoneCallListSizeAfterAddIsCorrect() {
        var bill = new PhoneBill(name);
        var call = new PhoneCall();
        bill.addPhoneCall(call);
        Collection<PhoneCall> calls = bill.getPhoneCalls();
        assertThat(calls.size(), equalTo(1));
    }

    @Test
    public void phoneCallListContainsCorrectCall() {
        var bill = new PhoneBill(name);
        var call = new PhoneCall("000-000-0000","000-000-0000","00/00/0000","00:00","00/00/0000","00:00");
        bill.addPhoneCall(call);
        Collection<PhoneCall> calls = bill.getPhoneCalls();
        assertThat("List has proper call", calls.contains(call));
    }
}
