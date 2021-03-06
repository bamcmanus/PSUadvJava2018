package edu.pdx.cs410J.bmcmanus.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * A GWT remote service that returns a dummy Phone Bill
 */
@RemoteServiceRelativePath("phoneBill")
public interface PhoneBillService extends RemoteService {

  /**
   * Returns a Phone Bill
   * @param customerName  customer name of the bill requested
   */
  PhoneBill getPhoneBill(String customerName);

  /**
   * adds call to phone bill
   * @param customerName  name of the customer whose bill the call will be added
   * @param call          the call to be added
   */
  void addPhoneCall(String customerName, PhoneCall call);

}
