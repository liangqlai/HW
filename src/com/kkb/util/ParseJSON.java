package com.kkb.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ParseJSON {
    private JSONObject data;

    public ParseJSON(String json) {
        this.data = JSONObject.parseObject(json);
        this.data = this.data.getJSONObject("resp");
    }

    /**
     * 获取当天温度区间
     *
     * @return low, high 两个key
     */
    public HashMap<String, String> getTemp() {
        HashMap<String, String> map = new HashMap<>();
        JSONObject yesterday = data.getJSONObject("yesterday");
        String low = yesterday.getString("low_1");
        low = low.substring(3, low.length() - 1);
        String high = yesterday.getString("high_1");
        high = high.substring(3, high.length() - 1);
        map.put("low", low);
        map.put("high", high);
        return map;
    }

    /**
     * 获取用于Message的风力等级集合
     *
     * @return
     */
    public HashMap<String, ArrayList<String>> getWindpowers() {
        JSONArray array = data.getJSONObject("forecast").getJSONArray("weather");
        ArrayList low = new ArrayList();
        ArrayList high = new ArrayList();
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String low1 = obj.getJSONObject("day").getString("fengli");
            String high1 = obj.getJSONObject("night").getString("fengli");
            low1 = low1.substring(0, low1.length() - 1);
            high1 = high1.substring(0, high1.length() - 1);
            low.add(low1);
            high.add(high1);
        }
        HashMap map = new HashMap();
        map.put("low", low);
        map.put("high", high);
        return map;
    }

    /**
     * 获取用于Message的未来五天温度变化
     *
     * @return
     */
    public HashMap<String, ArrayList<String>> getTemps() {
        JSONArray array = data.getJSONObject("forecast").getJSONArray("weather");
        ArrayList low = new ArrayList();
        ArrayList high = new ArrayList();
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String low1 = obj.getString("low");
            String high1 = obj.getString("high");
            low1 = low1.substring(3, low1.length() - 1);
            high1 = high1.substring(3, high1.length() - 1);
            low.add(low1);
            high.add(high1);
        }
        HashMap map = new HashMap();
        map.put("low", low);
        map.put("high", high);
        return map;

    }

    /**
     * 获取当天风力等级
     *
     * @return day，night两个key
     */
    public Map<String, String> getWindpower() {
        HashMap<String, String> map = new HashMap<>();
        JSONObject yesterday = data.getJSONObject("yesterday");
        String day = yesterday.getJSONObject("day_1").getString("fl_1");
        day = day.substring(0, day.length() - 1);
        String night = yesterday.getJSONObject("night_1").getString("fl_1");
        night = night.substring(0, night.length() - 1);
        map.put("day", day);
        map.put("night", night);
        return map;
    }

    /**
     * 获取当天风向
     *
     * @return
     */
    public String getWinddirect() {
        return data.getString("fengxiang");
    }

    /**
     * 获取当天白天天气信息
     *
     * @return
     */
    public String getWeather() {
        JSONObject yesterday = data.getJSONObject("yesterday");
        String day = yesterday.getJSONObject("day_1").getString("type_1");
        return day;
    }

    /**
     * 获取随机的温馨提示
     *
     * @return
     */
    public Map<String, String> getTips() {
        String[] texts = {"你都不知道心疼人的",
                "被一个人牵动着情绪很烦，但也可以很甜蜜。",
                "宝我去输液了输的什么液想你的夜。",
                "我养你啊，笨蛋",
                "你对一个喜欢你关心你担心你的人就这么爱答不理的",
                "孽缘。咱俩真是孽缘。",
                "我他妈不就是你的真命天子?",
                "活到老学到老，我一定会往更好的方向进步。",
                "人非圣贤孰能无过我过而能改还不可吗?",
                "哪儿油了就是喜欢才跟你这样",
                "我这几天都没睡好你知道吗?每天都在想你",
                "别不回我吧，我都道歉了，和好吧我去找你玩吧，想你了",
                "算了，你不想见我我不勉强",
                "让我好好爱你行不?",
                "宝我今天输液了输什么液想你的液.",
                "宝我今天看错了点看的什么点每天爱你多一点",
                "宝我今天买了个盒买的什么盒我们的天作之合",
                "宝我今天要吃药了吃的什么药你最最最最重要",
                "宝我今天逛马路了逛的什么路爱你到没有退路",
                "宝我今天已躺平了躺的什么平得不到你意难平",
                "宝我今天去运动了运的什么动对你每一次心动",
                "宝我今天嗑瓜子了磕的什么子是你的真命天子",
                "宝我今天去上班了上的什么班我爱你的不一般",
                "宝我今天去吃饭了吃的什么饭想让你乖乖就范",
                "宝我今天打疫苗了打的什么庙我爱你的每一秒",
                "宝我今天做核酸了做的什么酸得不到你的心酸",
                "宝我今天去鱼了钓的什么鱼爱你到至死不渝",
                "宝我今天去吃面了吃的什么面突然想见你一面",
                "宝我今天去种地了种的什么地对你的死心塌地"};
        Random r = new Random();
        String tip = texts[r.nextInt(texts.length)];
        ArrayList<String> names = new ArrayList<>();
        names.add("情话");
        names.add("紫外线强度");
        names.add("穿衣指数");
        names.add("护肤指数");
        names.add("感冒指数");
        names.add("户外指数");
        names.add("污染指数");
        int index = r.nextInt(names.size());
        if (index == 0) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("情话", tip);
            return map;
        }
        String name = names.get(index);

        JSONArray array = data.getJSONObject("zhishus").getJSONArray("zhishu");
        for (int i = 0; i < array.size(); i++) {
            if (name.equals(array.getJSONObject(i).getString("name"))) {
                HashMap<String, String> map = new HashMap<>();
                map.put(name, array.getJSONObject(i).getString("detail"));
                return map;
            }
        }
        return null;
    }
}
