// Online Java Compiler
// Use this editor to write, compile and run your Java code online

import java.util.*;

public class JSONParser {
    public static Map<String, Object> parse(String json) {
        Map<String, Object> resultMap = new HashMap<>();
        json = json.trim();

        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1);
            String[] keyValuePairs = json.split(",");
            
            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim().replace("\"", "");
                    String value = keyValue[1].trim();
                    
                    if (value.startsWith("{") && value.endsWith("}")) {
                        resultMap.put(key, parse(value));  // Recursive call for nested objects
                    } else if (value.startsWith("\"") && value.endsWith("\"")) {
                        resultMap.put(key, value.substring(1, value.length() - 1));  // Remove quotes
                    } else if (value.equals("true") || value.equals("false")) {
                        resultMap.put(key, Boolean.valueOf(value));
                    } else {
                        try {
                            resultMap.put(key, Integer.parseInt(value));
                        } catch (NumberFormatException e) {
                            // If value is not an integer, treat it as a string
                            resultMap.put(key, value);
                        }
                    }
                }
            }
        }

        return resultMap;
    }

    public static void main(String[] args) {
        String input = "{\"debug\" : \"on\", \"window\" : {\"title\" : \"sample\", \"size\": 500}}";
        Map<String, Object> output = JSONParser.parse(input);
        assert output.get("debug").equals("on");
        assert ((Map<String, Object>) output.get("window")).get("title").equals("sample");
        assert ((Integer) ((Map<String, Object>) output.get("window")).get("size")).equals(500);
}
}
