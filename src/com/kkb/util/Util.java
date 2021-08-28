package com.kkb.util;

import com.kkb.bean.Message;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Util {

    /**
     * 读取网址数据
     *
     * @return
     */
    private static String getString(String url) {
        //1. 创建一个网址的 抽象表示（对象）
        try {
            URL u = new URL(url);
            //2. 打开链接
            URLConnection conn = u.openConnection();
            //3. 获取传输数据的通道（字节输入流）
            InputStream is = conn.getInputStream();
            //4. 将字节输入流，装饰为能一次读取一行文字的缓冲字符输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            //5. 读取一行行的数据，并汇总
            StringBuffer sb = new StringBuffer();
            String text = null;
            while ((text = br.readLine()) != null) {
                sb.append(text);
            }
            //6. 将读取的数据返回给调用者
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询天气
     *
     * @param city 要查询的城市
     * @return 天气结果
     */
    private static String getWeather(String city) {
        try {
            String json = getString("https://itdage.cn/hw/weather?city=" + URLEncoder.encode(city, "utf-8"));
            return json;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String sendSms(String name, String phoneNumber, String s1, String s2, String s3) {
        try {
            name = URLEncoder.encode(name, "utf-8");
            s1 = URLEncoder.encode(s1, "utf-8");
            s2 = URLEncoder.encode(s2, "utf-8");
            s3 = URLEncoder.encode(s3, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String json = getString("https://itdage.cn/hw/hwSms?name=" + name + "&phoneNumber=" + phoneNumber + "&s1=" + s1 + "&s2=" + s2 + "&s3=" + s3);
        return json;
    }

    public static String send(String phoneNumber, String name, String city) {
        //1. 根据城市查天气
        String json = getWeather(city);
        //2. 根据天气的JSON信息，解析出天气、温度区间、温馨提示
        ParseJSON obj = new ParseJSON(json);
        //天气信息
        String s1 = obj.getWeather();
        //获取并组装了温度区间
        HashMap<String, String> temp = obj.getTemp();
        String lowTemp = temp.get("low");
        String highTemp = temp.get("high");
        String s2 = lowTemp + "-" + highTemp + "°";

        //获取指数
        Map<String, String> tips = obj.getTips();
        String s3 = tips.values().toArray()[0].toString();

        //进行短信数据的逻辑处理，以及组装存储一个message
        Message msg = Message.getInstance();
        //更新信息
        //更新1. 未来五天温度信息
        HashMap<String, ArrayList<String>> temps = obj.getTemps();
        msg.setTemp(temps);
        //更新2. 更新此次短信发送的温馨提示数据类型
        msg.addSmsType(tips.keySet().toArray()[0].toString());
        //更新3. 更新温度类型出现的数量
        int tempType = -1;
        if (Integer.parseInt(lowTemp) < 0) {
            //0类天气
            tempType = 0;
        } else if (Integer.parseInt(lowTemp) >= 0 && Integer.parseInt(lowTemp) < 10) {
            //1类天气
            tempType = 1;
        } else if (Integer.parseInt(lowTemp) >= 10 && Integer.parseInt(lowTemp) < 20) {
            //2类天气
            tempType = 2;
        } else if (Integer.parseInt(lowTemp) >= 20 && Integer.parseInt(lowTemp) < 30) {
            //3类天气
            tempType = 3;
        } else {
            //4类天气
            tempType = 4;
        }
        msg.addTempTypeCount(tempType);
        //更新4. 这次短信的风向
        msg.addWindirect(obj.getWinddirect());
        //更新5. 这次短信 五天内夜间风力 和 白天风力
        msg.setWindpower(obj.getWindpowers());
        //更新6. 更新天气情况的次数
        msg.addWeatherTypeCount(s1);
        System.out.println(msg.toJSON());
        //3. 发短信
        return sendSms(name, phoneNumber, s1, s2, s3);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String message = send("18516955565", "宝儿", "杭州");
        System.out.println(message);
    }
}
