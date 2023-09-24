package com.rozwork.common.utils.http;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import net.sf.jsqlparser.expression.JsonAggregateFunction;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * description: Tool class for post request using Http Client<p>
 * param:  <p>
 * return:  <p>
 * author: shicong yang <p>
 * date: 2019-07-30 <p>
 */
public class HttpClientUtil {

    private static final String POST = "POST";
    private static final String ENC_UTF8 = "UTF-8";

    /**
     * Default http connection timeout
     */
    private final static int DEFAULT_CONN_TIMEOUT = 10000; // 10s
    /**
     * Default http read timeout
     */
    private final static int DEFAULT_READ_TIMEOUT = 60000; // 60s

    /**
     * HTTP protocol POST request
     *
     * @param url   request url
     * @param param Parameter string
     * @return String
     * @throws
     */
    public static String post(String url, String param) {
        return post(url, param, DEFAULT_CONN_TIMEOUT, DEFAULT_READ_TIMEOUT, ENC_UTF8);
    }

    /**
     * description: HTTP protocol POST request<p>
     * param: [url, param, connOut, readOut, charset] <p>
     * return: java.lang.String <p>
     * author: shicong yang <p>
     * date: 2019-07-30 <p>
     */
    public static String post(String url, String param, int connOut, int readOut, String charset) {
        BufferedReader in = null;
        HttpURLConnection uc = null;
        StringBuffer sb = new StringBuffer();
        try {
            uc = (HttpURLConnection) new URL(url).openConnection();
            uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            uc.setRequestProperty("Content-Type", "application/json;charset=" + charset);
            uc.setRequestProperty("accept", "*/*");
            uc.setDoOutput(true);
            uc.setDoInput(true);
            uc.setRequestMethod(POST);
            uc.setUseCaches(false);
            uc.setConnectTimeout(connOut);
            uc.setReadTimeout(readOut);
            uc.connect();
            DataOutputStream out = new DataOutputStream(uc.getOutputStream());
            out.write(param.getBytes("UTF-8"));
            out.flush();
            out.close();
            in = new BufferedReader(new InputStreamReader(uc.getInputStream(), charset));
            String readLine = null;
            while ((readLine = in.readLine()) != null) {
                sb.append(readLine).append("\n");
            }
        } catch (IOException e) {
            System.out.println("[HttpClient POST]Request exception" + e);
        } finally {
            close(in, uc);
        }
        return sb.toString();
    }

    public static String post(String url, String param, String authorization) {
        BufferedReader in = null;
        HttpURLConnection uc = null;
        StringBuffer sb = new StringBuffer();
        try {
            uc = (HttpURLConnection) new URL(url).openConnection();
            //uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//            uc.setRequestProperty("Content-Type", "application/json;charset=" + ENC_UTF8);
            uc.setRequestProperty("Content-Type", "application/json");
//            uc.setRequestProperty("AppId", appid);
            uc.setRequestProperty("Authorization", authorization);
            //uc.setRequestProperty("accept", "*/*");
            uc.setDoOutput(true);
            uc.setDoInput(true);
            uc.setRequestMethod(POST);
            uc.setUseCaches(false);
            uc.setConnectTimeout(DEFAULT_CONN_TIMEOUT);
            uc.setReadTimeout(DEFAULT_READ_TIMEOUT);
            uc.connect();
            DataOutputStream out = new DataOutputStream(uc.getOutputStream());
//            out.write(param.getBytes("UTF-8"));
            out.write(param.getBytes());
            out.flush();
            out.close();
            in = new BufferedReader(new InputStreamReader(uc.getInputStream(), ENC_UTF8));
            String readLine = null;
            while ((readLine = in.readLine()) != null) {
//                sb.append(readLine).append("\n");
                sb.append(readLine);
            }
        } catch (IOException e) {
            System.out.println("[HttpClient POST]Request exception" + e);
        } finally {
            close(in, uc);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        String param = "{\"source_currency\":\"USD\",\"country\":\"USA\",\"account_type\":\"EMAIL\",\"fee_bear\":\"beneficiary\",\"amount\":\"1.0\",\"method\":\"WALLET\",\"arrival_currency\":\"USD\",\"channel\":\"PayPal\",\"document_id\":\"CUP800691\",\"notify_url\":\"https://api.pay.goldenmob.com/callback/pagsmile/batchResult\",\"phone\":\"1111111\",\"name\":\"g74527048\",\"additional_remark\":\"pagsmile payout\",\"email\":\"g74527048@gmail.com \",\"account\":\"g74527048@gmail.com \",\"custom_code\":\"20230424215308083360777\",\"document_type\":\"RFC\"}";
//        System.out.println(post1("https://transfersmile.com/api/payout",param,"0E2478D1F32040DA9BC8FA18FB5214DB","ea8adc17c90aa61cda4f46093375311131380857efd5260d59f814874b693ba4"));
    }

    /**
     * description: close IO<p>
     * param: [in, uc] <p>
     * return: void <p>
     * author: shicong yang <p>
     * date: 2019-07-30 <p>
     */
    private static void close(BufferedReader in, HttpURLConnection uc) {
        try {
            if (in != null) {
                in.close();
            }
            if (uc != null) {
                uc.disconnect();
            }
        } catch (IOException e) {
            System.out.println("[HttpClient]Close flow exception" + e);
        }
    }

}
