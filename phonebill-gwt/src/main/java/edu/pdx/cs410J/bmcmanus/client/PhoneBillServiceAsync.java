package edu.pdx.cs410J.bmcmanus.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The client-side interface to the phone bill service
 */
public interface PhoneBillServiceAsync {

  /**
   * gets a phone bill from the server
   */
  void getPhoneBill(String customerName, AsyncCallback<PhoneBill> async);

  /**
   * Always throws an exception so that we can see how to handle uncaught
   * exceptions in GWT.
   */
  void throwUndeclaredException(AsyncCallback<Void> async);

  /**
   * Always throws a declared exception so that we can see GWT handles it.
   */
  void throwDeclaredException(AsyncCallback<Void> async);

  /**
   * adds a call to a phone bill on the server
   */
  void addPhoneCall(String customerName, PhoneCall call, AsyncCallback<Void> call_added_successfully);
}
