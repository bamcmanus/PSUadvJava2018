package edu.pdx.cs410J.bmcmanus;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneCall} class.
 */
public class PhoneCallTest {
	private String validDate = "12/30/2000";
	private String validTime = "00:00";
	private String validNum = "123-456-7890";

	@Test
    public void verifyTimeAllowsCorrectFormat() {
	    var call = new PhoneCall(validNum,validNum,validDate,validTime,validDate,validTime);
    }

	@Test (expected = IllegalArgumentException.class)
	public void verifyTimeCatchesLetters() {
		var call = new PhoneCall(validNum, validNum, validDate, "00:0a", validDate, validTime);
	}

	@Test (expected = IllegalArgumentException.class)
	public void verifyTimeCatchesTooShort() {
		var call = new PhoneCall(validNum, validNum, validDate, "00:0", validDate, validTime);
	}

	@Test (expected = IllegalArgumentException.class)
	public void verifyTimeCatchesTooLong() {
		var call = new PhoneCall(validNum, validNum, validDate, "00:000", validDate, validTime);
	}

	@Test
	public void verifyDateAllowsSingleDigitMonth() {
		var call = new PhoneCall(validNum, validNum, "1/12/2000", validTime, validDate, validTime);
	}

	@Test
	public void verifyDateAllowsSingleDigitDay() {
		var call = new PhoneCall(validNum,validNum,"10/1/2000",validTime,validDate,validTime);
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

    @Test
    public void initiallyAllPhoneCallsHaveTheSameCallee() {
        var call = new PhoneCall();
        assertThat(call.getCallee(), containsString("not implemented"));
    }

    @Test
    public void getStartTimeContainsDate() {
	    var call = new PhoneCall(validNum,validNum,validDate,validTime,validDate,validTime);
	    assertThat(call.getStartTimeString(), containsString(validDate));
    }

	@Test
	public void getEndTimeContainsDate() {
		var call = new PhoneCall(validNum,validNum,validDate,validTime,validDate,validTime);
		assertThat(call.getEndTimeString(), containsString(validDate));
	}

	@Test
	public void getStartTimeContainsTime() {
		var call = new PhoneCall(validNum,validNum,validDate,validTime,validDate,validTime);
		assertThat(call.getStartTimeString(),containsString(validTime));
	}

	@Test
	public void getEndTimeContainsTime() {
		var call = new PhoneCall(validNum,validNum,validDate,validTime,validDate,validTime);
		assertThat(call.getEndTimeString(),containsString(validTime));
	}

    @Test
	public void getStartTimeStringReturnsCorrectArg() {
		var call = new PhoneCall(validNum,validNum,"01/01/1999","00:00",validDate,validTime);
		assertThat(call.getStartTimeString(), equalTo("01/01/1999 00:00"));
	}

	@Test
	public void getEndTimeStingReturnsCorrectArg() {
		var call = new PhoneCall(validNum,validNum,validDate,validTime, "01/01/1999","00:00");
		assertThat(call.getEndTimeString(), equalTo("01/01/1999 00:00"));
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