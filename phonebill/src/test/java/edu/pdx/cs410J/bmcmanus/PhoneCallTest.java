package edu.pdx.cs410J.bmcmanus;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneCall} class.
 */
public class PhoneCallTest {
	private String validDate = "00/00/0000";
	private String validTime = "00:00";
	private String validNum = "123-456-7890";

	@Test (expected = IllegalArgumentException.class)
	public void veriftyTimeCatchesLetters() {
		var call = new PhoneCall(validNum, validNum, validDate, "00:0a", validDate, validTime);
	}

	@Test (expected = IllegalArgumentException.class)
	public void verifyTimeCatchesTooShort() {
		var call = new PhoneCall(validNum, validNum, validDate, "00:0", validDate, validTime);
	}

	@Test (expected = IllegalArgumentException.class)
	public void veriftyTimeCatchesTooLong() {
		var call = new PhoneCall(validNum, validNum, validDate, "00:000", validDate, validTime);
	}

	@Test
	public void verifyDateAllowsSingleDigitMonth() {
		var call = new PhoneCall(validNum, validNum, "1/00/0000", validTime, validDate, validTime);
	}

	@Test
	public void verifyDateAllowsSingleDigitDay() {
		var call = new PhoneCall(validNum,validNum,"00/1/0000",validTime,validDate,validTime);
	}

	@Test (expected = IllegalArgumentException.class)
	public void verifyDateCatchesWrongFormat() {
		var call = new PhoneCall(validNum, validNum, "00000000", validTime, validDate, validTime);
	}

	@Test (expected = IllegalArgumentException.class)
	public void verifyDateCatchesLetters() {
		var call = new PhoneCall(validNum, validNum, "00/00/000a", validTime, validDate, validTime);
	}

	@Test (expected = IllegalArgumentException.class)
	public void verifyDateCatchesTooShort() {
		var call = new PhoneCall(validNum, validNum, "00/00/000", validTime, validDate, validTime);
	}

    @Test (expected = IllegalArgumentException.class)
    public void verifyDateCatchesDatesTooLong() {
  	    var call = new PhoneCall(validNum,validNum,"00/00/00000",validTime,validDate,validTime);
    }

    @Test (expected = IllegalArgumentException.class)
    public void verifyNumCatchesLetters() {
    	var call = new PhoneCall("123-456-789b", validNum, validDate, validTime, validDate, validTime);
    }

    @Test (expected = IllegalArgumentException.class)
    public void verifyNumCatchesTooLongNum() {
  	    var call = new PhoneCall("123-456-7890123", validNum, validDate, validTime, validDate, validTime);
    }

    @Test (expected = IllegalArgumentException.class)
    public void verifyNumCatchesToooShortNum() {
  	    var call = new PhoneCall("123-456-789",validNum, validDate, validTime, validDate, validTime);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getStartTimeStringNeedsToBeImplemented() {
        var call = new PhoneCall();
        call.getStartTimeString();
    }

    @Test
    public void initiallyAllPhoneCallsHaveTheSameCallee() {
        var call = new PhoneCall();
        assertThat(call.getCallee(), containsString("not implemented"));
    }

    @Test
    public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
        var call = new PhoneCall();
        assertThat(call.getStartTime(), is(nullValue()));
    }

     @Test
    public void getCallerReturnsCaller(){
		var call = new PhoneCall(validNum,validNum, validDate, validTime, validDate, validTime);
		assertThat(call.getCaller(), equalTo(validNum));
	  }

    @Test
    public void getCalleeReturnsCaller(){
	    var call = new PhoneCall(validNum,validNum, validDate, validTime, validDate, validTime);
		assertThat(call.getCallee(), equalTo(validNum));
    }
}