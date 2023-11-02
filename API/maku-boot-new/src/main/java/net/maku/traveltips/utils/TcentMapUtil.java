package net.maku.traveltips.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class TcentMapUtil {

    private static final String HOST_URL = "https://apis.map.qq.com";
    private static final String KEY_PRAME = "SDTBZ-XZM6V-M4VP6-5HDMS-XUAT6-4QFYP";
    private static final String SECRET_KEY = "ZIKNcgxysmN3TAfkTuJsADlVLzxm5fzw";


    /**
     * 通过地址获取定位信息
     *
     * @param address
     */
    public static String geocoder_address(String address) {
        String url = "/ws/geocoder/v1?";
        TreeMap params = new TreeMap<>();
        params.put("address", address);
        params.put("key", KEY_PRAME);
        String sigUrl = url + treeMapToURl(params) + SECRET_KEY;
        String s = SecureUtil.md5(sigUrl);
        //是否需要编码处理 开始
        try {
            params.put("address", URLEncoder.encode(address, "UTF-8" ));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        //是否需要编码处理 结束
        String reqUrl = HOST_URL + url + treeMapToURl(params) + "&sig=" + s;
        String result = HttpUtil.get(reqUrl);
        return result;
    }


    /**
     * 通过地址获取定位信息
     *
     * @param location
     * @param get_poi     是否返回周边地点（POI）列表，可选值： 0 不返回(默认) 1 返回
     * @param poi_options poi_options=address_format=short;radius=5000;policy=2
     *                    周边POI（AOI）列表控制参数：
     *                    1 poi_options=address_format=short
     *                    返回短地址，缺省时返回长地址
     *                    2 poi_options=radius=5000
     *                    半径，取值范围 1-5000（米）
     *                    3 poi_options=policy=1/2/3/4/5
     *                    控制返回场景，
     *                    policy=1[默认] 以地标+主要的路+近距离POI为主，着力描述当前位置；
     *                    policy=2 到家场景：筛选合适收货的POI，并会细化收货地址，精确到楼栋；
     *                    policy=3 出行场景：过滤掉车辆不易到达的POI(如一些景区内POI)，增加道路出入口、交叉口、大区域出入口类POI，排序会根据真实API大用户的用户点击自动优化。
     *                    policy=4 社交签到场景，针对用户签到的热门 地点进行优先排序。
     *                    policy=5 位置共享场景，用户经常用于发送位置、位置分享等场景的热门地点优先排序
     *                    4 注：policy=1/2/3最多返回10条周边POI，policy=4/5最多返回20条，
     * @return String
     */
    public static String geocoder_location(String location, int get_poi, String poi_options) {
        String url = "/ws/geocoder/v1?";
        TreeMap params = new TreeMap<>();
        params.put("location", location);
        if (get_poi == 1) {
            params.put("get_poi", get_poi);
            params.put("poi_options", poi_options);
        }
        params.put("key", KEY_PRAME);
        String sigUrl = url + treeMapToURl(params) + SECRET_KEY;
        String s = SecureUtil.md5(sigUrl);
        //是否需要编码处理
        String reqUrl = HOST_URL + url + treeMapToURl(params) + "&sig=" + s;
        String result = HttpUtil.get(reqUrl);
        return result;
    }


    public static String treeMapToURl(TreeMap treeMap) {
        StringBuffer stringBuffer = new StringBuffer();
        // 遍历方法一 hashmap entrySet() 遍历
        Iterator it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            stringBuffer.append(entry.getKey() + "=" + entry.getValue()).append("&" );
        }
        return stringBuffer.toString().substring(0, stringBuffer.length() - 1);
    }

    public static void main(String[] args) {
//        TreeMap params = new TreeMap<>();
//        params.put("b1", "b1");
//        params.put("a1", "c1");
//        params.put("keys", "c1");
//        params.put("kays", "c1");
//        params.put("addaress", "c1");
//        params.put("c1", "c1");
//        try {
//            System.out.println(treeMapToURl(params));
//            System.out.println(params);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        System.out.println(geocoder_address("金塔沙漠胡杨林景区" ));

        System.out.println(geocoder_location("45.136723,124.816696", 1, "address_format=short;radius=1000;policy=1" ));
    }
}
