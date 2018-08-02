package edu.pdx.cs410J.bmcmanus;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for formatting messages on the server side.  This is mainly to enable test methods that
 * validate that the server returned expected strings.
 */
public class Messages {

  public static String formatBillCount(int count) {
    return String.format("Records on server contains %d phone bills for customer", count);
  }

  public static String formatBillEntry(String word, String definition) {
    return String.format("  %s :\n %s", word, definition);
  }

  public static String missingRequiredParameter(String parameterName) {
    return String.format("The required parameter \"%s\" is missing", parameterName);
  }

  public static String definedWordAs(String customer, String callString) {
    return String.format("Added call %s to %s's phone bill", callString, customer);
  }

  public static String allPhoneBillEntriesDeleted() {
    return "All phone bill entries have been deleted";
  }

  public static Map.Entry<String, String> parseDictionaryEntry(String content) {
    Pattern pattern = Pattern.compile("\\s*(.*) : (.*)");
    Matcher matcher = pattern.matcher(content);

    if (!matcher.find()) {
      return null;
    }

    return new Map.Entry<>() {
      @Override
      public String getKey() {
        return matcher.group(1);
      }

      @Override
      public String getValue() {
        String value = matcher.group(2);
        if ("null".equals(value)) {
          value = null;
        }
        return value;
      }

      @Override
      public String setValue(String value) {
        throw new UnsupportedOperationException("This method is not implemented yet");
      }
    };
  }

  public static void formatDictionaryEntries(PrintWriter pw, Map<String, String> dictionary) {
    pw.println(Messages.formatBillCount(dictionary.size()));

    for (Map.Entry<String, String> entry : dictionary.entrySet()) {
      pw.println(Messages.formatBillEntry(entry.getKey(), entry.getValue()));
    }
  }

  public static Map<String, String> parseDictionary(String content) {
    Map<String, String> map = new HashMap<>();

    String[] lines = content.split("\n");
    for (int i = 1; i < lines.length; i++) {
      String line = lines[i];
      Map.Entry<String, String> entry = parseDictionaryEntry(line);
      map.put(entry.getKey(), entry.getValue());
    }

    return map;
  }

}
