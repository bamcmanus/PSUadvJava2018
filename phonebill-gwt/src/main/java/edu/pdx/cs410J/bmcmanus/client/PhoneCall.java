package edu.pdx.cs410J.bmcmanus.client;

import edu.pdx.cs410J.AbstractPhoneCall;
import java.util.Date;

/**
 * Phone call class encapsulates all of the data associated with a single phone call.  Stores the
 * number calling and called.  Stores the start date in the format MM/DD/YYYY and start time in 12hr
 * format.  Stores end date in the format MM/DD/YYYY and end time in 24hr format.
 *
 * @author Brent McManus
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {
  /**
   * Field for caller phone number
   */
  private String callerNum;

  /**
   * Field for person called's phone number
   */
  private String calleeNum;

  /**
   * Field for the start time of the call in 12hr format
   */
  private Date startTime;

  /**
   * Field for the end time of the call in 12hr format
   */
  private Date endTime;

  /**
   * Must have un-paramaterized constructor for GWT
   */
  PhoneCall() { }

  /**
   * constructor for phone call using date for gwt, checks that end time is after start time
   * @param callerNumber  caller number in the format ###-###-####
   * @param calleeNumber  callee number in the format ###-###-####
   * @param startTime     date with the start time of the call
   * @param endTime       date wiht the end time of the call
   */
  PhoneCall(String callerNumber, String calleeNumber, Date startTime, Date endTime) {
    if (startTime.after(endTime)) {
      throw new IllegalArgumentException("The end time was after the start time.");
    }
    callerNum = callerNumber;
    calleeNum = calleeNumber;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  /**
   * Gets the phone number of the caller
   *
   * @return String with the caller's phone number
   */
  @Override
  public String getCaller() {
    return this.callerNum;
  }

  /**
   * Gets the phone number of the person being called
   *
   * @return String with the customer's phone number
   */
  @Override
  public String getCallee() {
    return this.calleeNum;
  }

  /**
   * Gets the date and time the call started
   *
   * @return String containing the date and time the call started
   */
  @Override
  public String getStartTimeString() {
    return startTime.toString();
  }

  /**
   * Gets the date and time the call ended
   *
   * @return String containing the date and time the call ended
   */
  @Override
  public String getEndTimeString() {
    return endTime.toString();
  }

  /**
   * Getter for the start time of a call
   * @return  Date with start time
   */
  @Override
  public Date getStartTime() {
    return startTime;
  }

  /**
   * Getter for the end time of a call
   * @return  Date with end time
   */
  @Override
  public Date getEndTime() {
    return endTime;
  }

  /**
   * Compare function for sorting
   * @param call phonecall to be sorted
   * @return  0 if same, or the result of .compareTo() on startTime followed by .compareTo() caller
   */
  @Override
  public int compareTo(PhoneCall call) {
    int diff = this.startTime.compareTo(call.startTime);
    if (diff == 0) {
      return this.callerNum.compareTo(call.callerNum);
    } else {
      return diff;
    }
  }
}