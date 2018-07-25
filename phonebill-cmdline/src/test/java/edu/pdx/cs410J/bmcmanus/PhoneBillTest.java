package edu.pdx.cs410J.bmcmanus;

import java.text.ParseException;
import java.util.ArrayList;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;

/**
 * Unit tests for the {@link PhoneBill} class.
 */
public class PhoneBillTest {

  private String name = "Bill";

  @Test(expected = IllegalArgumentException.class)
  public void verifyNameCannotBeEmpty() {
    var bill = new PhoneBill("");
  }

  @Test
  public void customerHasNameGiven() {
    var bill = new PhoneBill(name);
    assertThat(bill.getCustomer(), equalTo(name));
  }

  @Test
  public void phoneCallListStartsEmpty() {
    var bill = new PhoneBill(name);
    Collection<PhoneCall> calls = bill.getPhoneCalls();
    assertThat(calls.size(), equalTo(0));
  }

  @Test
  public void phoneCallListSizeAfterHasOne() {
    var bill = new PhoneBill(name);
    var call = new PhoneCall();
    bill.addPhoneCall(call);
    Collection<PhoneCall> calls = bill.getPhoneCalls();
    assertThat(calls.size(), equalTo(1));
  }

  @Test
  public void phoneCallListSizeAfterHasTwo() throws ParseException {
    var bill = new PhoneBill(name);
    var call = new PhoneCall("123-456-7890","123-456-7890","12/12/2000",
        "12:00","am","12/12/2000","12:12","am");
    bill.addPhoneCall(call);
    //call = new PhoneCall("123-456-7890","123-456-7890","12/12/2000",
        //"12:00","am","12/12/2000","12:12","am");
    bill.addPhoneCall(call);
    Collection<PhoneCall> calls = bill.getPhoneCalls();
    assertThat(calls.size(), equalTo(2));
  }

  @Test
  public void phoneCallListContainsCorrectCall() throws ParseException {
    var bill = new PhoneBill(name);
    var call = new PhoneCall("000-000-0000", "000-000-0000", "12/12/2000", "12:00", "am", "12/12/2000",
        "12:00", "pm");
    bill.addPhoneCall(call);
    Collection<PhoneCall> calls = bill.getPhoneCalls();
    assertThat("List has proper call", calls.contains(call));
  }
}
