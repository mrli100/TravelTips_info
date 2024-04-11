package net.maku.travel.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WeixinPushUtils {

    public static void main(String[] args) {

        String ret = scSend("主人服务器宕机了", "第一行\n\n第二行", "SCT238054TPyTUSKThvhO86baI9dzVchcH");
        System.out.println(ret);
    }


    public static String scSend(String text, String desp, String key) {
        try {
            String url = "https://sctapi.ftqq.com/" + key + ".send";
            String postData = "text=" + URLEncoder.encode(text, "UTF-8") + "&desp=" + URLEncoder.encode(desp, "UTF-8");


            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postData);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
