package edu.pdx.cs410J.bmcmanus.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Text;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {
  private final Alerter alerter;
  private final PhoneBillServiceAsync phoneBillService;
  private final Logger logger;

  private final DeckPanel deck = new DeckPanel();
  
  private Date endTime = null;
  private Date startTime = null;

  private String customerName = null;
  private String callerNum = null;
  private String calleeNum = null;
  private final Label phoneBillDisplay = new Label();

  public PhoneBillGwt() {
    this(Window::alert);
  }

  @VisibleForTesting
  PhoneBillGwt(Alerter alerter) {
    this.alerter = alerter;
    this.phoneBillService = GWT.create(PhoneBillService.class);
    this.logger = Logger.getLogger("phoneBill");
    Logger.getLogger("").setLevel(Level.INFO);  // Quiet down the default logging
  }

  private void alertOnException(Throwable throwable) {
    Throwable unwrapped = unwrapUmbrellaException(throwable);
    StringBuilder sb = new StringBuilder();
    sb.append(unwrapped.toString());
    sb.append('\n');

    for (StackTraceElement element : unwrapped.getStackTrace()) {
      sb.append("  at ");
      sb.append(element.toString());
      sb.append('\n');
    }

    this.alerter.alert(sb.toString());
  }

  private Throwable unwrapUmbrellaException(Throwable throwable) {
    if (throwable instanceof UmbrellaException) {
      UmbrellaException umbrella = (UmbrellaException) throwable;
      if (umbrella.getCauses().size() == 1) {
        return unwrapUmbrellaException(umbrella.getCauses().iterator().next());
      }

    }
    return throwable;
  }

  /*private void throwClientSideException() {
    logger.info("About to throw a client-side exception");
    throw new IllegalStateException("Expected exception on the client side");
  }

  private void showUndeclaredException() {
    logger.info("Calling throwUndeclaredException");
    phoneBillService.throwUndeclaredException(new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Void aVoid) {
        alerter.alert("This shouldn't happen");
      }
    });
  }

  private void showDeclaredException() {
    logger.info("Calling throwDeclaredException");
    phoneBillService.throwDeclaredException(new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Void aVoid) {
        alerter.alert("This shouldn't happen");
      }
    });
  }*/

  private void showPhoneBill() {
    if (customerName == null) {
      alerter.alert("The customer field is empty");
      return;
    }
    logger.info("Calling getPhoneBill");
    phoneBillService.getPhoneBill(this.customerName, new AsyncCallback<PhoneBill>() {

      @Override
      public void onFailure(Throwable ex) {
        alerter.alert("No phone bill was found for that customer");
        //alertOnException(ex);
      }

      @Override
      public void onSuccess(PhoneBill phoneBill) {
        StringBuilder sb = new StringBuilder(phoneBill.toString());
        Collection<PhoneCall> calls = phoneBill.getPhoneCalls();
        for (PhoneCall call : calls) {
          sb.append(call);
          sb.append("\n");
        }
        alerter.alert(sb.toString());
      }
    });
  }

  private void showRange() {
    if (customerName == null) {
      alerter.alert("The customer field is empty");
      return;
    } else if (startTime == null) {
      alerter.alert("The start time field is empty");
      return;
    } else if (endTime == null) {
      alerter.alert("The end time field is empty");
      return;
    } else if(startTime.after(endTime)) {
      alerter.alert("The end time is before the start time");
      return;
    }

    logger.info("Calling getPhoneBill");
    phoneBillService.getPhoneBill(this.customerName, new AsyncCallback<PhoneBill>() {

      @Override
      public void onFailure(Throwable ex) {
        alerter.alert("No phone bill was found for that customer");
        //alertOnException(ex);
      }

      @Override
      public void onSuccess(PhoneBill phoneBill) {
        StringBuilder sb = new StringBuilder();
        Collection<PhoneCall> calls = phoneBill.getPhoneCalls();
        sb.append("Calls on " + customerName + "'s phone bill between " +
            DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a").format(startTime) + " and " +
            DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a").format(endTime) + ":\n");
        for (PhoneCall call : calls) {
          Date callStartTime = call.getStartTime();
          if ((callStartTime.after(startTime) || callStartTime.equals(startTime)) &&
              callStartTime.before(endTime) || callStartTime.equals(endTime)) {
            sb.append(call);
            sb.append("\n");
          }
        }
        phoneBillDisplay.setText(sb.toString());
        //alerter.alert(sb.toString());
      }
    });
  }

  private void addCall() {
    PhoneCall call = null;
    if (customerName == null) {
      alerter.alert("The customer field is empty");
    }

    try{
      call = new PhoneCall(callerNum,calleeNum,startTime,endTime);
    } catch (IllegalArgumentException ex) {
      alerter.alert(ex.getLocalizedMessage());
    }

    phoneBillService.addPhoneCall(this.customerName, call, new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable ex) {
      }

      @Override
      public void onSuccess(Void aVoid) {
        alerter.alert("Call added successfully");
        resetValues();
      }
    });
  }

  @Override
  public void onModuleLoad() {
    setUpUncaughtExceptionHandler();

    // The UncaughtExceptionHandler won't catch exceptions during module load
    // So, you have to set up the UI after module load...
    Scheduler.get().scheduleDeferred(this::setupUI);
  }

  private void setupUI() {
    RootPanel rootPanel = RootPanel.get();
    rootPanel.add(deck);
    deck.add(createMainMenu());
    deck.add(createDisplayCard());
    deck.add(createAddCallCard());
    deck.add(createReadMeCard());

    deck.showWidget(0);
  }

  private VerticalPanel createAddCallCard() {
    VerticalPanel panel = new VerticalPanel();

    Label welcome = new Label("Add a phone call to a phone bill.\n\n");
    welcome.getElement().getStyle().setProperty("whiteSpace","pre");

    Button addButton = new Button("Add Call");
    addButton.addClickHandler(clickEvent -> addCall());
    addButton.setWidth("140px");

    panel.add(welcome);
    panel.add(createHorizontalCustomerPanel());
    panel.add(createHorizontalCallerPanel());
    panel.add(createHorizontalCalleePanel());
    panel.add(createHorizontalStartTimePanel());
    panel.add(createHorizontalEndTimePanel());
    panel.add(addButton);
    panel.add(mainMenuButton());

    return panel;
  }

  private VerticalPanel createMainMenu() {
    VerticalPanel mainMenu = new VerticalPanel();
    deck.add(mainMenu);

    Button showPhoneBillButton = new Button("Display Phone Bill");
    showPhoneBillButton.setWidth("140px");
    showPhoneBillButton.addClickHandler(clickEvent -> deck.showWidget(1));

    Button showAddPhoneCallButton = new Button("Add Phone Call");
    showAddPhoneCallButton.setWidth("140px");
    showAddPhoneCallButton.addClickHandler(clickEvent -> deck.showWidget(2));

    Button showReadMeButton = new Button("README");
    showReadMeButton.setWidth("140px");
    showReadMeButton.addClickHandler(clickEvent -> deck.showWidget(3));

    mainMenu.add(showPhoneBillButton);
    mainMenu.add(showAddPhoneCallButton);
    mainMenu.add(showReadMeButton);
    return mainMenu;
  }

  private VerticalPanel createDisplayCard() {
    VerticalPanel panel = new VerticalPanel();

    Label welcome = new Label("Display a phone bill or calls in the given range.\n\n");
    welcome.getElement().getStyle().setProperty("whiteSpace","pre");

    Button displayButton = new Button("Display Bill");
    displayButton.setWidth("140px");
    displayButton.addClickHandler(clickEvent -> showPhoneBill());

    Button displayRangeButton = new Button("Display calls in Range");
    displayRangeButton.setWidth("140px");
    displayRangeButton.addClickHandler(clickEvent -> showRange());

    phoneBillDisplay.getElement().getStyle().setProperty("whiteSpace","pre");

    panel.add(welcome);
    panel.add(createHorizontalCustomerPanel());
    panel.add(createHorizontalStartTimePanel());
    panel.add(createHorizontalEndTimePanel());
    panel.add(displayButton);
    panel.add(displayRangeButton);
    panel.add(mainMenuButton());
    panel.add(phoneBillDisplay);

    return panel;
  }

  private VerticalPanel createReadMeCard() {
    VerticalPanel readme = new VerticalPanel();
    Label welcome = new Label("Project 5 ReadMe");
    Label text = new Label(
        "\nThis program implements a GUI to manage phone bills and their calls. The main menu\n"
            + "allows you the option to view this readMe, add a phone call or display a phone bill.\n"
            + "On the add phone call tab you must enter the customer's name, caller number, callee\n"
            + "number, and the start and end date/time (in the format mm/dd/yyyy hh:mm am/pm. The\n"
            + "customer will have a phone bill created if one does not already exist.  The call will\n"
            + "be added to the existing phone bill otherwise.  Lastly, the display phone bill option\n"
            + "allows for the display of the entire phone bill or all calls made within a given time\n"
            + "frame. There are fillable fields with the same format requirements discussed earlier.\n\n"
    );
    text.getElement().getStyle().setProperty("whiteSpace","pre");

    readme.add(welcome);
    readme.add(text);
    readme.add(mainMenuButton());

    return readme;
  }

  private HorizontalPanel createHorizontalCustomerPanel() {
    HorizontalPanel customerPanel = new HorizontalPanel();

    Label customerLabel = new Label("Customer Name:");
    customerLabel.setWidth("220px");

    TextBox customerField = new TextBox();
    customerField.addChangeHandler(changeEvent -> customerName = customerField.getText());

    customerPanel.add(customerLabel);
    customerPanel.add(customerField);

    return customerPanel;
  }

  private HorizontalPanel createHorizontalStartTimePanel() {
    HorizontalPanel startTimePanel = new HorizontalPanel();

    Label startTimeLabel = new Label("Start Time (mm/dd/yyyy hh:mm am/pm):");
    startTimeLabel.setWidth("220px");

    TextBox startTimeField = new TextBox();
    startTimeField.addChangeHandler(changeEvent -> this.startTime = handleTime(startTimeField));

    startTimePanel.add(startTimeLabel);
    startTimePanel.add(startTimeField);

    return startTimePanel;
  }

  private HorizontalPanel createHorizontalEndTimePanel() {
    HorizontalPanel endTimePanel = new HorizontalPanel();

    Label endTimeLabel = new Label("End Time (mm/dd/yyyy hh:mm am/pm):");
    endTimeLabel.setWidth("220px");

    TextBox endTimeField = new TextBox();
    endTimeField.addChangeHandler(changeEvent -> this.endTime = handleTime(endTimeField));

    endTimePanel.add(endTimeLabel);
    endTimePanel.add(endTimeField);

    return endTimePanel;
  }

  private HorizontalPanel createHorizontalCallerPanel() {
    HorizontalPanel panel = new HorizontalPanel();

    Label caller = new Label("Caller Phone Number (###-###-####):");
    caller.setWidth("220px");

    TextBox callerField = new TextBox();
    callerField.addChangeHandler(changeEvent -> this.callerNum = handlePhoneNum(callerField));

    panel.add(caller);
    panel.add(callerField);

    return panel;
  }

  private HorizontalPanel createHorizontalCalleePanel() {
    HorizontalPanel panel = new HorizontalPanel();

    Label callee = new Label("Callee Phone Number (###-###-####):");
    callee.setWidth("220px");

    TextBox calleeField = new TextBox();
    calleeField.addChangeHandler(changeEvent -> this.calleeNum = handlePhoneNum(calleeField));

    panel.add(callee);
    panel.add(calleeField);

    return panel;
  }

  private Date handleTime(TextBox timeField) {
    String text = timeField.getText();
    DateTimeFormat format = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
    Date date = null;
    try {
      date = format.parseStrict(text);
    } catch (IllegalArgumentException ex) {
      alerter.alert("Invalid date: " + text + "\nMust be in the format MM/dd/yyyy hh:mm am/pm");
    }
    return date;
  }

  private String handlePhoneNum(TextBox numField) {
    String text = numField.getText();
    String number = null;
    if (verifyPhoneNumber(text)){
      number = text;
    }
    else{
      alerter.alert("The number entered was not in a valid format (###-###-####)");
    }
    return number;
  }

  private Button mainMenuButton() {
    Button mainMenu = new Button("Main Menu");
    mainMenu.setWidth("140px");
    mainMenu.addClickHandler(clickEvent -> {
      phoneBillDisplay.setText("");
      deck.showWidget(0);
    });
    return mainMenu;
  }

  private void setUpUncaughtExceptionHandler() {
    GWT.setUncaughtExceptionHandler(this::alertOnException);
  }

  @VisibleForTesting
  interface Alerter {
    void alert(String message);
  }

  /**
   * Verifies that a phone number is correctly formatted by comparing to a regular expression
   *
   * @param num String containing a phone number; format: ###-###-####
   * @return <code>true</code> when phone number is formatted correctly
   * <code>false</code> otherwise
   */
  private boolean verifyPhoneNumber(String num) {
    String phoneNumPattern = "\\d{3}-\\d{3}-\\d{4}";
    RegExp pattern = RegExp.compile(phoneNumPattern);
    MatchResult match = pattern.exec(num);
    return match != null;
  }

  private void resetValues() {
    customerName = null;
    callerNum = null;
    calleeNum = null;
    startTime = null;
    endTime = null;
  }

}
