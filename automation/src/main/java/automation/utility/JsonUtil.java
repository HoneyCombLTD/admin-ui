package automation.utility;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtil {
	private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static String convertJavaToJson(Object object) {

        String jsonResult = "";
        try {
            jsonResult = mapper.writeValueAsString(object);
        } catch (JsonParseException e) {
            logger.error("", e);
        } catch (JsonMappingException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return jsonResult;
    }

    public static <T> T convertJsonintoJava(String jsonString, Class<T> cls) {
        T payload = null;
        try {
            payload = mapper.readValue(jsonString, cls);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return payload;
    }

    public static String JsonObjSimpleParsing(String jsonIdentity, String idfield)
            throws Exception {
        String val =null;
        JSONObject json = new JSONObject(jsonIdentity);

        JSONObject identity = json.getJSONObject(PropertiesUtil.getKeyValue("jsonObjName"));

        JSONArray identityitems = identity.getJSONArray(idfield);

        for (int i = 0, size = identityitems.length(); i < size; i++) {
            JSONObject idItem = identityitems.getJSONObject(i);
            String lang = idItem.getString("language");
             val = idItem.getString("value");
            if (lang.equalsIgnoreCase(PropertiesUtil.getKeyValue(idfield)))
            	return val;
        }
        return "sin";
    }

    public static LinkedHashMap<String, String> JsonObjSimpleParsingWithCode(String jsonIdentity, String idfield)
            throws Exception {
        LinkedHashMap<String, String> mapLang = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> mapLangorder = new LinkedHashMap<String, String>();
        JSONObject json = new JSONObject(jsonIdentity);

        JSONObject identity = json.getJSONObject(PropertiesUtil.getKeyValue("jsonObjName"));

        JSONArray identityitems = identity.getJSONArray(idfield);

        for (int i = 0, size = identityitems.length(); i < size; i++) {
            JSONObject idItem = identityitems.getJSONObject(i);
            String lang = idItem.getString("language");
            String val = idItem.getString("value");
            String code = idItem.getString("code");
            String valcode = val + "@@" + code;
            mapLang.put(lang, valcode);
        }
        String[] listLang = PropertiesUtil.getKeyValue("langcode").split("@@");
        Set<String> keys = mapLang.keySet();

        for (String list : listLang) {
            for (String ky : keys) {
                if (list.equals(ky)) {
                    mapLangorder.put(list, mapLang.get(ky));
                }
            }

        }

        return mapLangorder;
    }

    public static LinkedHashMap<String, String> JsonObjSimpleParsingnoTranslate(String jsonIdentity, String idfield)
            throws Exception {
        LinkedHashMap<String, String> mapLang = new LinkedHashMap<String, String>();
        JSONObject json = new JSONObject(jsonIdentity);

        JSONObject identity = json.getJSONObject(PropertiesUtil.getKeyValue("jsonObjName"));

        JSONArray identityitems = identity.getJSONArray(idfield);

        for (int i = 0, size = identityitems.length(); i < size; i++) {
            JSONObject idItem = identityitems.getJSONObject(i);
            String lang = idItem.getString("language");
            String val = idItem.getString("value");
            String[] listLang = PropertiesUtil.getKeyValue("langcode").split("@@");
            for (String list : listLang) {
                if (lang.equals(list)) {
                    mapLang.put(list, val);
                    return mapLang;

                }
            }

        }
        return mapLang;
    }

    /**
     * Direct String
     * 
     * @param json
     * @param jsonObjName
     * @param idfield
     * @return
     * @throws Exception
     */
    public static String JsonObjParsing(String jsonIdentity, String idfield) throws Exception {
        String value = null;
        JSONObject json = new JSONObject(jsonIdentity);
        JSONObject identity = json.getJSONObject(PropertiesUtil.getKeyValue("jsonObjName"));

        value = identity.getString(idfield);

        return value;
    }

    public static double JsonObjDoubleParsing(String jsonIdentity, String idfield) throws Exception {
        double value = 0;
        JSONObject json = new JSONObject(jsonIdentity);
        JSONObject identity = json.getJSONObject(PropertiesUtil.getKeyValue("jsonObjName"));

        value = identity.getDouble(idfield);

        return value;
    }

    public static List<String> JsonObjArrayListParsing(String jsonIdentity, String idfield) throws Exception {
        List<String> list = new LinkedList<String>();
        JSONObject json = new JSONObject(jsonIdentity);

        JSONObject identity = json.getJSONObject(PropertiesUtil.getKeyValue("jsonObjName"));

        JSONArray identityitems = identity.getJSONArray(idfield);
        if (identityitems != null) {
            for (int i = 0; i < identityitems.length(); i++) {
                list.add(identityitems.getString(i));
            }
        }
        return list;

    }
    
    public static String  readJsonFileText(String document) {
        
        String jsonTxt = null;

        try {
            
                File f = new File(System.getProperty("user.dir") + "\\"+document);

                if (f.exists()) {
                    InputStream is = new FileInputStream(f);
                    jsonTxt = IOUtils.toString(is, "UTF-8");
                    System.out.println(jsonTxt);
                    logger.info("readJsonFileText");
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return jsonTxt;
    }


}
