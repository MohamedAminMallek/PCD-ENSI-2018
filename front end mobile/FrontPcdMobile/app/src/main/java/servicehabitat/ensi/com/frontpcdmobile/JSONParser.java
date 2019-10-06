package servicehabitat.ensi.com.frontpcdmobile;

import android.util.Log;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Aylar-HP on 12/06/2016.
 */
public class JSONParser {




    String test(String msg)
    {

      String src = msg;
      StringBuffer result = new StringBuffer();
      if(src!=null && src.length()!=0) {
        int index = -1;
        char c = (char)0;
        String chars= "àâäéèêëîïôöùûüç";
        String replace= "aaaeeeeiioouuuc";
        for(int j=0; j<src.length(); j++) {
          c = src.charAt(j);
          if( (index=chars.indexOf(c))!=-1 )
            result.append(replace.charAt(index));
          else
            result.append(c);
        }
      }
      return result.toString();
    }

    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result;
    URL urlObj;
    JSONObject jObj = null;
    StringBuilder sbParams;
    String paramsString;

    public JSONObject makeHttpRequest(String url, String method,HashMap<String, String> params) {

        sbParams = new StringBuilder();
        int i = 0;
        String aux = sbParams.toString();

      sbParams.append('{');
        for (String key : params.keySet()) {
            try {
                if (i != 0){
                    sbParams.append(",");
                }
                sbParams.append("\""+key).append("\" : \"")
                        .append(URLEncoder.encode(test(params.get(key)), charset)).append("\"");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }
        sbParams.append('}');

        if (method.equals("POST")) {
            // request method is POST
            try {
                urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.connect();
                paramsString = sbParams.toString();



                /*DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
                writer.write(paramsString);
                writer.flush();
                writer.close();
                */
                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("GET")){
            // request method is GET
            sbParams = new StringBuilder();
            i = 0;
            for (String key : params.keySet()) {
              try {
                if (i != 0){
                  sbParams.append("&");
                }
                sbParams.append(key).append("=")
                  .append(URLEncoder.encode(params.get(key), charset));

              } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
              }
              i++;
            }
            if (sbParams.length() != 0) {
                url += "?" + sbParams.toString();
            }

            try {
                urlObj = new URL(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoOutput(false);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept-Charset", charset);
                conn.setConnectTimeout(15000);
                conn.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            //Receive the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("JSON Parser", "result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();




        // try parse the string to a JSON object
        try {

            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON Object
        return jObj;
    }
    ArrayList<String> decomposeDesObjectsJson(String objets)
    {
      ArrayList<String> list = new ArrayList<String>();
      int nbO = 0;
      String ob="";
      for(int i=1;i<objets.length()-1;i++)
      {

        if(objets.charAt(i) == '{')
        {
          nbO++;
          ob+=objets.charAt(i);

        }else
          if(objets.charAt(i)=='}')
          {
            ob+=objets.charAt(i);
            nbO--;
            if(nbO==0)
            {
              list.add(ob);
              ob = "";
              i++;
            }
            }else
            {
              /*if(objets.charAt(i) == '[')
                objets = objets.substring(0,i)+'{'+objets.substring(i+1);
              else
              if(objets.charAt(i) == ']')
                objets = objets.substring(0,i)+'}'+objets.substring(i+1);
              else*/
                ob+=objets.charAt(i);
            }

      }
      return list;
    }
  public ArrayList<JSONObject> makeHttpRequestManyObject(String url, String method,HashMap<String, String> params) {

    sbParams = new StringBuilder();
    int i = 0;
    String aux = sbParams.toString();

    sbParams.append('{');
    for (String key : params.keySet()) {
      try {
        if (i != 0){
          sbParams.append(",");
        }
        sbParams.append("\""+key).append("\" : \"")
          .append(URLEncoder.encode(params.get(key), charset)).append("\"");

      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      i++;
    }
    sbParams.append('}');
    if (method.equals("POST")) {
      // request method is POST
      try {
        urlObj = new URL(url);
        conn = (HttpURLConnection) urlObj.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.connect();
        paramsString = sbParams.toString();

        wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(paramsString);
        wr.flush();
        wr.close();

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    else if(method.equals("GET")){
      // request method is GET
      sbParams = new StringBuilder();
      i = 0;
      for (String key : params.keySet()) {
        try {
          if (i != 0){
            sbParams.append("&");
          }
          sbParams.append(key).append("=")
            .append(URLEncoder.encode(params.get(key), charset));

        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
        i++;
      }
      if (sbParams.length() != 0) {
        url += "?" + sbParams.toString();
      }

      try {
        urlObj = new URL(url);
        conn = (HttpURLConnection) urlObj.openConnection();
        conn.setDoOutput(false);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept-Charset", charset);
        conn.setConnectTimeout(15000);
        conn.connect();

      } catch (IOException e) {
        e.printStackTrace();
      }

    }

    try {
      //Receive the response from the server
      InputStream in = new BufferedInputStream(conn.getInputStream());
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      result = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        result.append(line);
      }

      Log.d("JSON Parser", "result: " + result.toString());

    } catch (IOException e) {
      e.printStackTrace();
    }

    conn.disconnect();

    ArrayList<JSONObject> res = new ArrayList<JSONObject>();

    try {
      String auuux =result.toString().replaceAll("\\[]","{}");
      ArrayList<String> list = decomposeDesObjectsJson(auuux/*result.toString()*/);
      for (String s : list) {

        s.replaceAll("\\[","{");
        s.replaceAll("\\]","}");
        System.out.println();
        res.add(new JSONObject(s));
      }
    }catch (JSONException e) {
      Log.e("JSON Parser", "Error parsing data " + e.toString());
    }

    return res;

  }

}
