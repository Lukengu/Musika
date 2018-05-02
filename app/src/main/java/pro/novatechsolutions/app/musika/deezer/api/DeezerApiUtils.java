package pro.novatechsolutions.app.musika.deezer.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DeezerApiUtils {
    public enum  JSONTYPE {JSON_ARRAY, JSON_OBJECT, NOT_VALID_JSON}

    public static JSONTYPE isJSONValid(String test) {
        try {
            new JSONObject(test);
            return JSONTYPE.JSON_OBJECT;
        } catch (JSONException | NullPointerException ex) {
            try {
                new JSONArray(test);
                return JSONTYPE.JSON_ARRAY;
            } catch (JSONException | NullPointerException ex1) {

            }
        }
        return JSONTYPE.NOT_VALID_JSON;
    }
}
