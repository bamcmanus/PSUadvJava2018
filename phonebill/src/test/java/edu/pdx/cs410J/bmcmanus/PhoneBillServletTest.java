package edu.pdx.cs410J.bmcmanus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static edu.pdx.cs410J.bmcmanus.PhoneBillServlet.CUSTOMER_PARAMETER;
import static edu.pdx.cs410J.bmcmanus.PhoneBillServlet.END_TIME_PARAMETER;
import static edu.pdx.cs410J.bmcmanus.PhoneBillServlet.START_TIME_PARAMETER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class PhoneBillServletTest {
  private PhoneBillServlet servlet = new PhoneBillServlet();
  private String customer = "customer";
  private String caller = "123-456-7890";
  private String callee = "000-867-5309";

  private long startTime = System.currentTimeMillis();
  private long endTime = startTime + 100000L;

  @Test
  public void initiallyServletContainsNoPhoneBills() throws IOException {
    servlet = new PhoneBillServlet();

    HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    PrintWriter mockPw = mock(PrintWriter.class);

    String customer = "customer";

    when(mockResponse.getWriter()).thenReturn(mockPw);

    when(mockRequest.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);

    servlet.doGet(mockRequest, mockResponse);

    verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
  }

  @Test
  public void addPhoneBill() throws IOException {
    servlet = new PhoneBillServlet();

    HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    when(mockRequest.getParameter("customer")).thenReturn(customer);
    when(mockRequest.getParameter("caller")).thenReturn(caller);
    when(mockRequest.getParameter("callee")).thenReturn(callee);
    when(mockRequest.getParameter("startTime")).thenReturn(String.valueOf(startTime));
    when(mockRequest.getParameter("endTime")).thenReturn(String.valueOf(endTime));

    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(mockRequest, response);

    verify(response).setStatus(HttpServletResponse.SC_OK);

    var bill = servlet.getPhoneBill(customer);
    assertThat(bill, not(nullValue()));
    assertThat(bill.getCustomer(), equalTo(customer));

    var calls = bill.getPhoneCalls();
    assertThat(calls.size(), equalTo(1));

    var call = calls.iterator().next();
    assertThat(call.getCaller(),equalTo(caller));
    assertThat(call.getCallee(),equalTo(callee));
    assertThat(call.getStartTime(),equalTo(new Date(startTime)));
    assertThat(call.getEndTime(),equalTo(new Date(endTime)));
  }

  @Test
  public void getReturnsPrettyPhoneBill() throws IOException {
    servlet = new PhoneBillServlet();
    PhoneBill bill = new PhoneBill(customer);
    var call = new PhoneCall(caller,callee,new Date(startTime),new Date(endTime));

    bill.addPhoneCall(call);
    servlet.addPhoneBill(bill);

    HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    PrintWriter mockPw = mock(PrintWriter.class);

    String customer = "customer";

    when(mockResponse.getWriter()).thenReturn(mockPw);

    when(mockRequest.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);

    servlet.doGet(mockRequest, mockResponse);

    verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
    verify(mockPw).println("Phone Bill for: " + customer);
    verify(mockPw).println("Call From:          To:                 Start Time:           End Time:             Duration(minutes):\n");
    verify(mockPw).print(call.callerNum + "        " + call.calleeNum + "        ");
    verify(mockPw).print(new SimpleDateFormat("MM/dd/yy hh:mm a").format(call.getStartTime())
          + "     ");
    verify(mockPw).print(new SimpleDateFormat("MM/dd/yy hh:mm a").format(call.getEndTime())
          + "     ");
    long eTime,sTime,diff;
    eTime = call.getEndTime().getTime();
    sTime = call.getStartTime().getTime();
    diff = eTime - sTime;
    verify(mockPw).println(TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS) + "");
  }

  @Test
  public void getReturnsPrettyInRangePhoneBill() throws IOException {
    servlet = new PhoneBillServlet();
    PhoneBill bill = new PhoneBill(customer);
    var call = new PhoneCall(caller,callee,new Date(startTime),new Date(endTime));

    bill.addPhoneCall(call);
    servlet.addPhoneBill(bill);

    HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    PrintWriter mockPrintWriter = mock(PrintWriter.class);

    String customer = "customer";

    when(mockResponse.getWriter()).thenReturn(mockPrintWriter);
    when(mockRequest.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
    when(mockRequest.getParameter(START_TIME_PARAMETER)).thenReturn(String.valueOf(startTime));
    when(mockRequest.getParameter(END_TIME_PARAMETER)).thenReturn(String.valueOf(endTime));

    servlet.doGet(mockRequest, mockResponse);

    verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
    verify(mockPrintWriter).println("Phone Bill for: " + customer);
    verify(mockPrintWriter).println("Call From:          To:                 Start Time:           End Time:             Duration(minutes):\n");
    verify(mockPrintWriter).print(call.callerNum + "        " + call.calleeNum + "        ");
    verify(mockPrintWriter).print(new SimpleDateFormat("MM/dd/yy hh:mm a").format(call.getStartTime())
        + "     ");
    verify(mockPrintWriter).print(new SimpleDateFormat("MM/dd/yy hh:mm a").format(call.getEndTime())
        + "     ");
    long eTime,sTime,diff;
    eTime = call.getEndTime().getTime();
    sTime = call.getStartTime().getTime();
    diff = eTime - sTime;
    verify(mockPrintWriter).println(TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS) + "");
  }

  @Test
  public void getReturnsPrettyNothingInRangePhoneBill() throws IOException {
    servlet = new PhoneBillServlet();
    PhoneBill bill = new PhoneBill(customer);
    var call = new PhoneCall(caller,callee,new Date(startTime),new Date(endTime));

    bill.addPhoneCall(call);
    servlet.addPhoneBill(bill);

    HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    PrintWriter mockPrintWriter = mock(PrintWriter.class);

    String customer = "customer";

    when(mockResponse.getWriter()).thenReturn(mockPrintWriter);
    when(mockRequest.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
    when(mockRequest.getParameter(START_TIME_PARAMETER)).thenReturn(String.valueOf(startTime+100000));
    when(mockRequest.getParameter(END_TIME_PARAMETER)).thenReturn(String.valueOf(endTime));

    servlet.doGet(mockRequest, mockResponse);

    verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
    verify(mockPrintWriter).println("Phone Bill for: " + customer);
    verify(mockPrintWriter).println("No calls were found in that range");
  }
}
