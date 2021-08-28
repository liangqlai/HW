package com.kkb.bean;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Message {
    private static Message msg = null;

    public static Message getInstance() {
        if (msg == null) {
            msg = new Message(System.currentTimeMillis());
        }
        return msg;
    }

    public static void setNull() {
        msg = null;
    }

    private Message(long time) {
        this.time = time;
        this.smsType = new HashMap<>();
        this.smsType.put("names", new ArrayList<>());
        this.smsType.put("values", new ArrayList<>());

        this.temp = new HashMap<>();
        this.temp.put("low", new ArrayList<>());
        this.temp.put("high", new ArrayList<>());

        tempType = new ArrayList<>();
        tempType.add(0);
        tempType.add(0);
        tempType.add(0);
        tempType.add(0);
        tempType.add(0);

        winddirect = new HashMap<>();
        winddirect.put("names", new ArrayList());
        winddirect.put("values", new ArrayList());

        windpower = new HashMap<>();
        windpower.put("low", new ArrayList<>());
        windpower.put("high", new ArrayList<>());

        weatherType = new ArrayList<>();
    }

    //--------  上面的内容， 是为了单例设计模式

    private int status;
    private long time;
    private HashMap<String, ArrayList> smsType;

    public void addSmsType(String type) {
        ArrayList<String> names = smsType.get("names");
        ArrayList<Integer> values = smsType.get("values");

        int index = names.indexOf(type);
        if (index != -1) {
            values.set(index, values.get(index) + 1);
        } else {
            names.add(type);
            values.add(1);
        }
    }

    //未来五天温度
    private HashMap<String, ArrayList<String>> temp;

    public HashMap<String, ArrayList<String>> getTemp() {
        return temp;
    }

    public void setTemp(HashMap<String, ArrayList<String>> temp) {
        this.temp = temp;
    }

    //存储了5个温度区间的数量
    private ArrayList<Integer> tempType;

    public void addTempTypeCount(int index) {
        tempType.set(index, tempType.get(index) + 1);
    }

    //风向以及数量
    private HashMap<String, ArrayList> winddirect;

    public HashMap<String, ArrayList> getWinddirect() {
        return winddirect;
    }

    public void setWinddirect(HashMap<String, ArrayList> winddirect) {
        this.winddirect = winddirect;
    }

    /**
     * 添加风向数量
     */
    public void addWindirect(String type) {
        ArrayList<String> names = winddirect.get("names");
        ArrayList<Integer> values = winddirect.get("values");

        int index = names.indexOf(type);
        if (index != -1) {
            values.set(index, values.get(index) + 1);
        } else {
            names.add(type);
            values.add(1);
        }
    }

    //风力
    private HashMap<String, ArrayList<String>> windpower;

    public HashMap<String, ArrayList<String>> getWindpower() {
        return windpower;
    }

    public void setWindpower(HashMap<String, ArrayList<String>> windpower) {
        this.windpower = windpower;
    }

    //天气信息以及数量
    private ArrayList<HashMap> weatherType;

    public void addWeatherTypeCount(String type) {
        int index = -1;
        for (int i = 0; i < weatherType.size(); i++) {
            if (weatherType.get(i).get("name").equals(type)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            HashMap map = new HashMap();
            map.put("name", type);
            map.put("value", 1);
            weatherType.add(map);
        } else {
            Integer count = (Integer) weatherType.get(index).get("value");
            weatherType.get(index).put("value", count + 1);
        }
    }

    public static Message getMsg() {
        return msg;
    }

    public static void setMsg(Message msg) {
        Message.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public HashMap<String, ArrayList> getSmsType() {
        return smsType;
    }

    public void setSmsType(HashMap<String, ArrayList> smsType) {
        this.smsType = smsType;
    }

    public ArrayList<Integer> getTempType() {
        return tempType;
    }

    public void setTempType(ArrayList<Integer> tempType) {
        this.tempType = tempType;
    }

    public ArrayList<HashMap> getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(ArrayList<HashMap> weatherType) {
        this.weatherType = weatherType;
    }

    public String toJSON() {
        return JSONObject.toJSONString(this);
    }

}
