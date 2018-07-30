package edu.pdx.cs410J.bmcmanus;

import java.text.ParseException;
import java.util.Date;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static edu.pdx.cs410J.bmcmanus.PhoneBillServlet.CALLEE_PARAMETER;
import static edu.pdx.cs410J.bmcmanus.PhoneBillServlet.CALLER_PARAMETER;
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
  PhoneBillServlet servlet = new PhoneBillServlet();
  String customer = "customer";
  String caller = "123-456-7890";
  String callee = "000-867-5309";

  long startTime = System.currentTimeMillis();
  long endTime = startTime + 100000L;

  @Test
  public void initiallyServletContainsNoPhoneBills() throws ServletException, IOException, ParseException {
    PhoneBillServlet servlet = new PhoneBillServlet();

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
  public void addPhoneBill() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

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
    PhoneBillServlet servlet = new PhoneBillServlet();
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
    verify(mockPw).println(customer);
    verify(mockPw).println(call.toString());
  }
}
