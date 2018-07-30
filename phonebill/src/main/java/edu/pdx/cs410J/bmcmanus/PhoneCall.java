package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.AbstractPhoneCall;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
  String callerNum;

  /**
   * Field for person called's phone number
   */
  String calleeNum;

  /**
   * Field for the start time of the call in 12hr format
   */
  private Date startTime;

  /**
   * Field for the end time of the call in 12hr format
   */
  private Date endTime;

  PhoneCall() {
  }

  /**
   * Constructor for phone call class
   *
   * @param callerNum String with number of the person calling
   * @param calleeNum String with number of the customer being called
   * @param startDate String with start date of the call in MM/DD/YYYY format
   * @param startTime String with start time of the call in 24hr format
   * @param endDate String with end date of the call in MM/DD/YYYY format
   * @param endTime String with end time of the call in 24hr format
   * @throws IllegalArgumentException when phone number, date or time are in the incorrect format
   */
  PhoneCall(String callerNum, String calleeNum, String startDate, String startTime,
      String startAmPm, String endDate,
      String endTime, String endAmPm) throws ParseException {
    if (!verifyPhoneNumber(callerNum) || !verifyPhoneNumber(calleeNum)) {
      throw new IllegalArgumentException("Phone numbers must be in the format ###-###-####");
    }
    if (!verifyTime(startTime) || !verifyTime(endTime)) {
      throw new IllegalArgumentException("time must be in the format ##:## followed by AM or PM");
    }
    if (!startAmPm.equalsIgnoreCase("am") && !startAmPm.equalsIgnoreCase("pm") &&
        !endAmPm.equalsIgnoreCase("am") && !endAmPm.equalsIgnoreCase("pm")) {
      throw new IllegalArgumentException("time must be followed with am or pm");
    }
    if (!verifyDate(startDate) || !verifyDate(endDate)) {
      throw new IllegalArgumentException("Invlaid date must be formatted MM/dd/yyyy");
    }

    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    format.setLenient(false);

    this.callerNum = callerNum;
    this.calleeNum = calleeNum;

    try {
      this.startTime = format.parse(startDate + " " + startTime + " " + startAmPm);
    } catch (ParseException e) {
      throw new ParseException("The start time was not formatted correctly", e.getErrorOffset());
    }

    try {
      this.endTime = format.parse(endDate + " " + endTime + " " + endAmPm);
    } catch (ParseException e) {
      throw new ParseException("The end time was not formatted correctly", e.getErrorOffset());
    }

    if (this.endTime.before(this.startTime)) {
      throw new IllegalArgumentException("Call end time must be after the call start time");
    }
  }

  public PhoneCall(String callerNumber, String calleeNumber, Date startTime, Date endTime) {
    callerNum = callerNumber;
    calleeNum = calleeNumber;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  /**
   * Verifies that a time is correctly formatted by comparing to a regular expression
   *
   * @param time String containing time; format HH:MM
   * @return <code>true</code> when time matches the correct format
   * <code>false</code> otherwise
   */
  private boolean verifyTime(String time) {
    String timePattern = "(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])";
    Pattern pattern = Pattern.compile(timePattern);
    Matcher matcher = pattern.matcher(time);
    return matcher.matches();
  }

  /**
   * verifies that a date is correctly formatted by comparing to a regular expression
   *
   * @param date String containing date; format: MM/DD/YYYY, M/DD/YYYY, M/D/YYYY, MM/D/YYYY
   * @return <code>true</code> when date matches the correct format
   * <code>false</code> otherwise
   */
  private boolean verifyDate(String date) {
    String dateRegexPattern = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/(19|20)?\\d{2}";
    Pattern pattern = Pattern.compile(dateRegexPattern);
    Matcher matcher = pattern.matcher(date);
    return matcher.matches();
  }

  /**
   * Verifies that a phone number is correctly formatted by comparing to a regular expression
   *
   * @param num String containing a phone number; format: ###-###-####
   * @return <code>true</code> when phone number is formatted correctly
   * <code>false</code> otherwise
   */
  private boolean verifyPhoneNumber(String num) {
    String phoneNumPattern = "\\d{3}-\\d{3}-\\d{4}";
    Pattern pattern = Pattern.compile(phoneNumPattern);
    Matcher matcher = pattern.matcher(num);
    return matcher.matches();
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

  public String formatDate(Date date) {
    return DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG).format(date);
  }
  /**
   * Gets the date and time the call started
   *
   * @return String containing the date and time the call started
   */
  @Override
  public String getStartTimeString() {
    return formatDate(startTime);
  }

  /**
   * Gets the date and time the call ended
   *
   * @return String containing the date and time the call ended
   */
  @Override
  public String getEndTimeString() {
    return formatDate(endTime);
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
   * @param o phonecall to be sorted
   * @return  0 if same, or the result of .compareTo() on startTime followed by .compareTo() caller
   */
  @Override
  public int compareTo(PhoneCall o) {
    int diff = this.startTime.compareTo(o.startTime);
    if (diff == 0) {
      return this.callerNum.compareTo(o.callerNum);
    } else {
      return diff;
    }
  }
}