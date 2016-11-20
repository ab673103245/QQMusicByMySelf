package qianfeng.qqmusicbymyself.util;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/10/26 0026.
 */
public class NetUtil {

    public static String QUERYBYSONGNAME = "http://route.showapi.com/213-1?showapi_appid=26344&showapi_sign=ebf49d86b304478e86b71c48eb085db5&keyword=%s&page=1&";

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(5 * 1000, TimeUnit.SECONDS).build();

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

//    public static byte[] HttpConnection(String http) {
//        HttpURLConnection con = null;
//        try {
//            URL url = new URL(http);
//            ByteArrayOutputStream baos = null;
//            con = (HttpURLConnection) url.openConnection();
//            con.setConnectTimeout(5 * 1000);
//            con.connect();
//            if (con.getResponseCode() == 200) {
//                InputStream inputStream = con.getInputStream();
//                baos = new ByteArrayOutputStream();
//                int len = 0;
//                byte[] b = new byte[1024];
//                while ((len = inputStream.read(b)) != -1) {
//                    baos.write(b,0,b.length);
//                    baos.flush();
//                }
//                return baos.toByteArray();
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//
//    }

}
