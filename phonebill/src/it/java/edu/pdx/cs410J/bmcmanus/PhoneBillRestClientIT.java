package edu.pdx.cs410J.bmcmanus;

import edu.pdx.cs410J.web.HttpRequestHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Integration test that tests the REST calls made by {@link PhoneBillRestClient}
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhoneBillRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private PhoneBillRestClient newPhoneBillRestClient() {
    int port = Integer.parseInt(PORT);
    return new PhoneBillRestClient(HOSTNAME, port);
  }

  @Test
  public void test0RemoveAllPhoneBills() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    client.removeAllPhoneBills();
  }

  @Test (expected = NoSuchPhoneBillException.class)
  public void test1EmptyServerThrowsNoSuchPhoneBillException() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    client.getPrettyPhoneBill("No such customer");
  }

  @Test
  public void test2AddOnePhoneCall() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    var callerNumber = "123-456-7890";
    var calleeNumber = "098-765-4321";
    var startTime = new Date(System.currentTimeMillis());
    var endTime = new Date(System.currentTimeMillis() + 100000);
    var call = new PhoneCall(callerNumber,calleeNumber,startTime,endTime);

    String customer = "customer";
    client.addPhoneCall(customer,call);

    client.addPhoneCall(customer,call);
    String pretty = client.getPrettyPhoneBill(customer);
    assertThat(pretty, containsString(customer));
    assertThat(pretty, containsString(callerNumber));
    assertThat(pretty, containsString(calleeNumber));
    assertThat(pretty,containsString(String.valueOf(new SimpleDateFormat("MM/dd/yy hh:mm a").format(call.getStartTime()))));
    assertThat(pretty,containsString(String.valueOf(new SimpleDateFormat("MM/dd/yy hh:mm a").format(call.getEndTime()))));
  }

  @Test
  public void test4MissingRequiredParameterReturnsPreconditionFailed() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    HttpRequestHelper.Response response = client.postToMyURL();
    assertThat(response.getContent(), containsString(Messages.missingRequiredParameter("customer")));
    assertThat(response.getCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
  }

  @Test
  public void test5getPrettyInRange() {

  }

}
