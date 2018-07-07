package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.regex.*;

/**
 * Phone call class encapsulates all of the data associated with a single phone call.  Stores the number calling and
 * called.  Stores the start date in the format MM/DD/YYYY and start time in 24hr format.  Stores end date in the format
 * MM/DD/YYYY and end time in 24hr format.
 */
public class PhoneCall extends AbstractPhoneCall {
	/**
	 * Field for caller phone number
	 */
	private String callerNum;
	/**
	 * Field for person called's phone number
	 */
	private String calleeNum;
	/**
	 * Field for start date of the call in the format MM/DD/YYYY, M/DD/YYYY/ MM/D/YYYY or M/D/YYYY
	 */
	private String startDate;
	/**
	 * Field for the start time of the call in 24hr format
	 */
	private String startTime;
	/**
	 * Field for the end date of the call in the format MM/DD/YYYY, M/DD/YYYY/ MM/D/YYYY or M/D/YYYY
	 */
	private String endDate;
	/**
	 * Field for the end time of the call in 24hr format
	 */
	private String endTime;

	public PhoneCall() {
		this.calleeNum = "not implemented.";
	}

	/**
	 * Constructor for phone call class
	 *
	 * @param callerNum String with number of the person calling
	 * @param calleeNum String with number of the customer being called
	 * @param startDate String with start date of the call in MM/DD/YYYY format
	 * @param startTime String with start time of the call in 24hr format
	 * @param endDate   String with end date of the call in MM/DD/YYYY format
	 * @param endTime   String with end time of the call in 24hr format
	 * @throws IllegalArgumentException when phone number, date or time are in the incorrect format
	 */
	public PhoneCall(String callerNum, String calleeNum, String startDate, String startTime, String endDate,
	                 String endTime) {
		if (!verifyPhoneNumber(callerNum) || !verifyPhoneNumber(calleeNum)) {
			throw new IllegalArgumentException("Phone numbers must be in the format ###-###-####");
		}
		if (!verifyDate(startDate) || !verifyDate(endDate)) {
			throw new IllegalArgumentException("Dates must be in the format MM/DD/YYY");
		}
		if (!verifyTime(startTime) || !verifyTime(endTime)) {
			throw new IllegalArgumentException("Time must be in 24hr format 00:00");
		}
		this.callerNum = callerNum;
		this.calleeNum = calleeNum;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
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
		String timePattern = "(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])";
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
		String dateRegexPattern = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/(19|20)\\d{2}";
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

	/**
	 * Gets the date and time the call started
	 *
	 * @return String containing the date and time the call started
	 */
	@Override
	public String getStartTimeString() {
		return this.startDate + " " + this.startTime;
	}

	/**
	 * Gets the date and time the call ended
	 *
	 * @return String containing the date and time the call ended
	 */
	@Override
	public String getEndTimeString() {
		return this.endDate + " " + this.endTime;
	}
}