package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.AbstractPhoneCall;
import java.util.regex.*;
import javax.print.DocFlavor;

public class PhoneCall extends AbstractPhoneCall {
    private String callerNum;
    private String calleeNum;
    private String startTimeDate;
    private String startTime;
    private String endTimeDate;
    private String endTime;

    public PhoneCall() {
        this.calleeNum = "not implemented.";
    }

    /**
     * Constructor for phone call class
     * @param callerNum String with number of the person calling
     * @param calleeNum String with number of the customer being called
     * @param startTimeDate String with start date of the call in MM/DD/YYYY format
     * @param startTime String with start time of the call in 24hr format
     * @param endTimeDate String with end date of the call in MM/DD/YYYY format
     * @param endTime String with end time of the call in 24hr format
     */
    public PhoneCall(String callerNum, String calleeNum, String startTimeDate, String startTime, String endTimeDate, String endTime) {
  	    if(!verifyPhoneNumber(callerNum) || !verifyPhoneNumber(calleeNum))
  		    throw new IllegalArgumentException("Phone numbers must be in the format ###-###-####");
  	    if(!verifyDate(startTimeDate) || ! verifyDate(endTimeDate))
  		    throw new IllegalArgumentException("Dates must be in the format MM/DD/YYY");
  	    if(!verifyTime(startTime) || !verifyTime(endTime))
  	    	throw new IllegalArgumentException("Time must be in 24hr format 00:00");
        this.callerNum = callerNum;
        this.calleeNum = calleeNum;
        this.startTimeDate = startTimeDate;
        this.startTime = startTime;
        this.endTimeDate = endTimeDate;
        this.endTime = endTime;
    }

    /**
     * Verifies that the time is in 24hr format
     * @param time String for time in the format HH:MM
     * @return True if valid otherwise false
     */
    private boolean verifyTime(String time) {
         //define the regex of a phone number
        String singleDigitPattern = "\\d{1}:\\d{2}";
        String doubleDigitPattern = "\\d{2}:\\d{2}";
		//create pattern object with pattern regex
		Pattern singlePat = Pattern.compile(singleDigitPattern);
		Pattern doublePat = Pattern.compile(doubleDigitPattern);
		//create matcher with pattern
		Matcher singleMatcher = singlePat.matcher(time);
		Matcher doubleMatcher = doublePat.matcher(time);
		if (singleMatcher.matches() || doubleMatcher.matches())
			return true;
		return false;
    }

    /**
     * verifies that the date is in an acceptable format: MM/DD/YYYY, M/DD/YYYY, M/D/YYYY, MM/D/YYYY
     * @param date String containing date
     * @return True if valid otherwise false
     */
    private boolean verifyDate(String date) {
        //define the regex of a Date, support ##/##/####, #/##/####, #/#/####, ##/#/####
        String singleDigitPattern = "\\d{1}/\\d{2}/\\d{4}";
        String doubleDigitPattern = "\\d{2}/\\d{2}/\\d{4}";
        String singleSinglePattern = "\\d{1}/\\d{1}/\\d{4}";
        String doubleSinglePattern = "\\d{2}/\\d{1}/\\d{4}";
		//create pattern object with pattern regex
		Pattern singlePat = Pattern.compile(singleDigitPattern);
		Pattern doublePat = Pattern.compile(doubleDigitPattern);
		Pattern singleSingle = Pattern.compile(singleSinglePattern);
		Pattern doubleSingle = Pattern.compile(doubleSinglePattern);
		//create matcher with pattern
		Matcher singleMatcher = singlePat.matcher(date);
		Matcher doubleMatcher = doublePat.matcher(date);
		Matcher singleSingleMatcher = singleSingle.matcher(date);
		Matcher doubleSingleMatcher = doubleSingle.matcher(date);
		if (singleMatcher.matches() || doubleMatcher.matches() || singleSingleMatcher.matches() || doubleSingleMatcher.matches())
			return true;
		return false;
	}

    /**
     * Verifies that a phone number is in the format ###-###-####
     * @param num String containing the phone number
     * @return Boolean, true if valid otherwise false
     */
	private boolean verifyPhoneNumber(String num){
		//define the regex of a phone number
		String pattern = "\\d{3}-\\d{3}-\\d{4}";
		//create pattern object with pattern regex
		Pattern pat = Pattern.compile(pattern);
		//create matcher with pattern
		Matcher matcher = pat.matcher(num);
		//return if num matches regex
		return matcher.matches();
    }

    /**
     * gets the phone number of the caller
     * @return String with the caller's phone number
     */
    @Override
    public String getCaller() {
  	    return this.callerNum;
    }

    /**
     * Gets the phone number of the person being called
     * @return String with the customer's phone number
     */
    @Override
    public String getCallee() {
  	    return this.calleeNum;
    }

    /**
     * Gets the date and time the call started
     * @return String containing the date and time the call started
     */
    @Override
    public String getStartTimeString() {
        return this.startTimeDate + " " + this.startTime;
    }

    /**
     * Gets the date and time the call ended
     * @return String containing the date and time the call ended
     */
    @Override
    public String getEndTimeString() {
        return this.endTimeDate + " " + this.endTime;
    }
}