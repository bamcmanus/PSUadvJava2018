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
		calleeNum = "not implemented.";
    }

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

    private boolean verifyTime(String num) {
         //define the regex of a phone number
        String singleDigitPattern = "\\d{1}:\\d{2}";
        String doubleDigitPattern = "\\d{2}:\\d{2}";
		//create pattern object with pattern regex
		Pattern singlePat = Pattern.compile(singleDigitPattern);
		Pattern doublePat = Pattern.compile(doubleDigitPattern);
		//create matcher with pattern
		Matcher singleMatcher = singlePat.matcher(num);
		Matcher doubleMatcher = doublePat.matcher(num);
		if (singleMatcher.matches() || doubleMatcher.matches())
			return true;
		return false;
    }

    private boolean verifyDate(String num) {
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
		Matcher singleMatcher = singlePat.matcher(num);
		Matcher doubleMatcher = doublePat.matcher(num);
		Matcher singleSingleMatcher = singleSingle.matcher(num);
		Matcher doubleSingleMatcher = doubleSingle.matcher(num);
		if (singleMatcher.matches() || doubleMatcher.matches() || singleSingleMatcher.matches() || doubleSingleMatcher.matches())
			return true;
		return false;
	}

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

    @Override
    public String getCaller() {
  	    return this.callerNum;
    }

    @Override
    public String getCallee() {
  	    return this.calleeNum;
    }

    @Override
    public String getStartTimeString() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public String getEndTimeString() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
}