package edu.pdx.cs410J.bmcmanus;

import java.text.ParseException;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/** Unit tests for the {@link PhoneCall} class.*/
public class PhoneCallTest {
	private String validDate = "12/30/2000";
	private String validTime = "12:00";
	private String validNum = "123-456-7890";
	private String amPm = "AM";

	@Test
    public void verifyTimeAllowsCorrectFormat() throws ParseException {
	    var call = new PhoneCall(validNum,validNum,validDate,validTime,amPm,validDate,validTime,amPm);
    }

	@Test (expected = IllegalArgumentException.class)
	public void verifyTimeCatchesLetters() throws ParseException {
		var call = new PhoneCall(validNum, validNum, validDate, "00:0a",amPm, validDate,
				validTime,amPm);
	}

	@Test (expected = IllegalArgumentException.class)
	public void verifyTimeCatchesTooShort() throws ParseException {
		var call = new PhoneCall(validNum, validNum, validDate, "00:0",amPm, validDate,
				validTime,amPm	);
	}

	@Test (expected = IllegalArgumentException.class)
	public void verifyTimeCatchesTooLong() throws ParseException {
		var call = new PhoneCall(validNum, validNum, validDate, "00:000",amPm, validDate,
				validTime,amPm);
	}

	@Test
	public void verifyDateAllowsSingleDigitMonth() throws ParseException {
		var call = new PhoneCall(validNum, validNum, "1/12/2000", validTime,amPm, validDate,
				validTime,amPm);
	}

	@Test
	public void verifyDateAllowsSingleDigitDay() throws ParseException {
		var call = new PhoneCall(validNum,validNum,"10/1/2000",validTime,amPm,validDate,
				validTime,amPm);
	}

	@Test (expected = IllegalArgumentException.class)
	public void verifyDateCatchesWrongFormat() throws ParseException {
		var call = new PhoneCall(validNum, validNum, "00000000", validTime,amPm, validDate,
				validTime,amPm);
	}

	@Test (expected = IllegalArgumentException.class)
	public void verifyDateCatchesLetters() throws ParseException {
		var call = new PhoneCall(validNum, validNum, "00/00/000a", validTime,amPm, validDate,
				validTime,amPm);
	}

	@Test (expected = IllegalArgumentException.class)
	public void verifyDateCatchesTooShort() throws ParseException {
		var call = new PhoneCall(validNum, validNum, "00/00/000", validTime,amPm, validDate,
				validTime,amPm);
	}

    @Test (expected = IllegalArgumentException.class)
    public void verifyDateCatchesDatesTooLong() throws ParseException {
			var call = new PhoneCall(validNum,validNum,"00/00/00000",validTime,amPm,validDate,
						validTime,amPm);
		}

    @Test (expected = IllegalArgumentException.class)
    public void verifyNumCatchesLetters() {
			try {
				var call = new PhoneCall("123-456-789b",validNum,validDate,validTime,amPm,validDate,
						validTime,amPm);
			} catch (ParseException e) { }
		}

    @Test (expected = IllegalArgumentException.class)
    public void verifyNumCatchesTooLongNum() {
			try {
				var call = new PhoneCall("123-456-7890123", validNum, validDate, validTime,amPm,
						validDate, validTime,amPm);
			} catch (ParseException e) { }
		}

    @Test (expected = IllegalArgumentException.class)
    public void verifyNumCatchesToooShortNum() {
			try {
				var call = new PhoneCall("123-456-789",validNum, validDate, validTime,amPm,
						validDate, validTime,amPm);
			} catch (ParseException e) { }
		}

    @Test
    public void getStartTimeContainsDate() throws ParseException {
	    var call = new PhoneCall(validNum,validNum,validDate,validTime,amPm,validDate,validTime,amPm);
	    assertThat(call.getStartTimeString(), containsString("12/30/00"));
    }

	@Test
	public void getEndTimeContainsDate() throws ParseException {
		var call = new PhoneCall(validNum,validNum,validDate,validTime,amPm,validDate,validTime,amPm);
		assertThat(call.getEndTimeString(), containsString("12/30/00"));
	}

	@Test
	public void getStartTimeContainsTime() throws ParseException {
		var call = new PhoneCall(validNum,validNum,validDate,validTime,amPm,validDate,validTime,amPm);
		assertThat(call.getStartTimeString(),containsString(validTime));
	}

	@Test
	public void getEndTimeContainsTime() throws ParseException {
		var call = new PhoneCall(validNum,validNum,validDate,validTime,amPm,validDate,validTime,amPm);
		assertThat(call.getEndTimeString(),containsString(validTime));
	}

    @Test
	public void getStartTimeStringReturnsCorrectArg() throws ParseException {
		var call = new PhoneCall(validNum,validNum,"01/01/1999","12:00",amPm,
				validDate,validTime,amPm);
		assertThat(call.getStartTimeString(), equalTo("1/1/99, 12:00 AM"));
	}

	@Test
	public void getEndTimeStingReturnsCorrectArg() throws ParseException {
		var call = new PhoneCall(validNum,validNum,validDate,validTime,amPm, "01/01/2002",
				"12:00",amPm);
		assertThat(call.getEndTimeString(), equalTo("1/1/02, 12:00 AM"));
	}

    @Test
    public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
        var call = new PhoneCall();
        assertThat(call.getStartTime(), is(nullValue()));
    }

     @Test
    public void getCallerReturnsCaller() throws ParseException {
		var call = new PhoneCall(validNum,validNum,validDate,validTime,amPm,validDate,validTime,amPm);
		assertThat(call.getCaller(), equalTo(validNum));
	  }

    @Test
    public void getCalleeReturnsCaller() throws ParseException {
	    var call = new PhoneCall(validNum,validNum,validDate,validTime,amPm,validDate,validTime,amPm);
		assertThat(call.getCallee(), equalTo(validNum));
    }
}