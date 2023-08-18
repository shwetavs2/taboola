import java.util.*;

public class JSONParser {

    private static final char OBJECT_OPEN = '{';
    private static final char OBJECT_CLOSE = '}';
    private static final char ARRAY_OPEN = '[';
    private static final char ARRAY_CLOSE = ']';
    private static final char QUOTE = '\"';
    private static final char COLON = ':';
    private static final char COMMA = ',';

    public static Map<String, Object> parseObject(String json) {
        Map<String, Object> resultMap = new HashMap<>();
        json = json.trim().substring(1, json.length() - 1); // Remove {}

        List<String> keyValuePairs = splitOutsideBraces(json, COMMA);
        for (String pair : keyValuePairs) {
            List<String> keyValue = splitOutsideBraces(pair, COLON);
            if (keyValue.size() == 2) {
                String key = keyValue.get(0).trim().replace("\"", "");
                Object value = parseValue(keyValue.get(1).trim());
                resultMap.put(key, value);
            }
        }
        return resultMap;
    }

    public static List<Object> parseArray(String json) {
        List<Object> resultList = new ArrayList<>();
        json = json.trim().substring(1, json.length() - 1); // Remove []

        List<String> values = splitOutsideBraces(json, COMMA);
        for (String value : values) {
            resultList.add(parseValue(value.trim()));
        }
        return resultList;
    }

    public static Object parseValue(String value) {
        if (value.startsWith(String.valueOf(QUOTE)) && value.endsWith(String.valueOf(QUOTE))) {
            return value.substring(1, value.length() - 1);  // Remove quotes
        } else if (value.startsWith(String.valueOf(OBJECT_OPEN))) {
            return parseObject(value);
        } else if (value.startsWith(String.valueOf(ARRAY_OPEN))) {
            return parseArray(value);
        } else if (value.equals("true") || value.equals("false")) {
            return Boolean.valueOf(value);
        } else {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                try {
                    return Double.parseDouble(value);
                } catch (NumberFormatException ex) {
                    return value;
                }
            }
        }
    }

    public static List<String> splitOutsideBraces(String str, char delimiter) {
        List<String> parts = new ArrayList<>();
        int start = 0, openCount = 0;
        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == OBJECT_OPEN || chars[i] == ARRAY_OPEN) {
                openCount++;
            } else if (chars[i] == OBJECT_CLOSE || chars[i] == ARRAY_CLOSE) {
                openCount--;
            } else if (chars[i] == delimiter && openCount == 0) {
                parts.add(str.substring(start, i));
                start = i + 1;
            }
        }

        parts.add(str.substring(start));
        return parts;
    }

    public static void main(String[] args) {
/*
String input = "{" +
    "\"area-items\": {" +
    "\"2\": [" +
      "{" +
        "\"id\": -250384452623414200," +
        "\"title\": \"Probe Agency NIA Convicts 5 Members Of Banned Outfit In Bijnor Blast Case\"," +
        "\"url\": \"https://www.ndtv.com/india-news/nia-court-convicts-5-simi-members-in-bijnor-ied-blast-case-3120426\"," +
        "\"description\": \"A special National Investigation Agency (NIA) court in Lucknow has convicted five members of the banned organisation SIMI to commit terrorist acts and sentenced them to rigorous imprisonment in the 2014 Bijnor blast case\"," +
        "\"publishEpochMillis\": 1656742080000," +
        "\"expirationEpochMillis\": 1656914880000," +
        "\"modifiedEpochMillis\": null," +
        "\"tags\": [" +
          "\"simi\"," +
          "\"national investigation agency\"," +
          "\"bijnor blast case\"" +
        "]," +
        "\"categories\": [" +
          "\"news\"" +
        "]," +
        "\"flags\": []," +
        "\"blocked\": false," +
        "\"thumbnail-url\": \"https://c.ndtvimg.com/2022-06/8jh8j9f8_police-generic-_625x300_01_June_22.jpg\"," +
        "\"publish-date\": \"2022-07-02 11:38:00\"," +
        "\"expiration-date\": \"2022-07-04 11:38:00\"," +
        "\"modified-date\": null," +
        "\"flag-update-time\": null" +
      "}" +
    "]" +
  "}" +
"}"; 
*/

String input = "{\"debug\" : \"on\", \"window\" : {\"title\" : \"sample\", \"size\": 500}}";

Map<String, Object> output = parseObject(input);
        System.out.println(output);
        assert output.get("debug").equals("on");
        assert ((Map<String, Object>) output.get("window")).get("title").equals("sample");
     assert ((Integer) ((Map<String, Object>) output.get("window")).get("size")).equals(500);
    
    }
}
