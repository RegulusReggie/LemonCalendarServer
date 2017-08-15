package Util;

import java.util.HashMap;
import java.util.Map;

public class JSONObject {
    private Map<String, String> fields;

    public JSONObject() {
        fields = new HashMap<>();
    }

    public void putField(String key, String value) {
        fields.put(key, value);
    }

    public String getField(String key) {
        return fields.get(key);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String key : fields.keySet()) {
            builder = builder.append(key).append('=').append(fields.get(key)).append('&');
        }
        builder = builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
