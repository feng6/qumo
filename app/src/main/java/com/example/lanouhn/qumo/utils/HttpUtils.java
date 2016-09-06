package com.example.lanouhn.qumo.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络请求工具类
 */
public class HttpUtils {

    /**
     * 使用Get方法获取网络数据
     *
     * @param path 网络请求地址
     * @return 请求结果
     */
    public static String doGet(String path) {

        String result = "";
        try {
            URL url = new URL(path);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                InputStreamReader ir = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(ir);
                String line = "";// 临时存储每行数据
                // 存储读的结果
                result = new String();
                // 循环读
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                //关闭流
                is.close();
                ir.close();

                if (connection != null)
                    connection.disconnect();
            } else {
                result = "请求失败,错误码:" + connection.getResponseCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     *              <p/>
     *              app_id=123456&id=556&abc=28093&djw=sakh....
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String doPost(String url, String param) {


        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


}
