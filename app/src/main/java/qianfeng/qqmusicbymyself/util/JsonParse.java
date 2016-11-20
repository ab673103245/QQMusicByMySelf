package qianfeng.qqmusicbymyself.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import qianfeng.mylibrary.bean.LrcBean;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;

/**
 * Created by Administrator on 2016/10/26 0026.
 */
public class JsonParse {

    public static List<MusicBean> parse(String str)
    {
        List<MusicBean> list = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(str).getJSONObject("showapi_res_body").getJSONObject("pagebean");
            String keyword = jsonObject.getString("w");


            JSONArray jsonArray = jsonObject.getJSONArray("contentlist");
            for(int i = 0; i < jsonArray.length(); i++)
            {
                // m4a就是url啊！
                JSONObject data = jsonArray.getJSONObject(i);
                String albumid = data.getString("albumid");
                String albumname = data.getString("albumname");
                String albumpic_big = data.getString("albumpic_big");
                String albumpic_small = data.getString("albumpic_small");
                String downUrl = data.getString("downUrl");
                String m4a = null; // m4a这个可能会无法播放啊！
                if (data.has("m4a")) {
                    m4a = data.getString("m4a");
                }else
                {
                    m4a = "http://ws.stream.qqmusic.qq.com/101126152.m4a?fromtag=46";
                }
                String singerid = data.getString("singerid");
                String singername = data.getString("singername");
                int songid = data.getInt("songid");
                String songname = data.getString("songname");

                MusicBean musicBean = new MusicBean();
                musicBean.setAlbumid(Integer.parseInt(albumid));
                musicBean.setAlbumpic_small(albumpic_big);
                musicBean.setDownUrl(downUrl);
                musicBean.setUrl(m4a);//!
                musicBean.setSingerid(Integer.parseInt(singerid));
                musicBean.setSingername(singername);
                musicBean.setSongid(songid);
                musicBean.setSongname(songname);
                musicBean.setKeyword(keyword);
                musicBean.setAlbumname(albumname);
                list.add(musicBean);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }


    public static List<MusicBean> parseJson2List(String json) {
        List<MusicBean> list = new ArrayList<>();
        try {
            JSONArray songList = new JSONObject(json).getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("songlist");
            for (int i = 0; i < songList.length(); i++) {
                JSONObject data = songList.getJSONObject(i);
                String songname = data.getString("songname");
                int seconds = data.getInt("seconds");
                String albummid = data.getString("albummid");
                String albumpic_big = data.getString("albumpic_big");
                String albumpic_small = data.getString("albumpic_small");
                String downUrl = data.getString("downUrl");
                String url = data.getString("url");
                String singername = data.getString("singername");

                int songid = data.getInt("songid");
                int singerid = data.getInt("singerid");
                int albumid = data.getInt("albumid");
                list.add(new MusicBean(songname, seconds, albummid, songid, singerid, albumpic_big, albumpic_big, downUrl, url, singername, albumid));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    /*  或者参考《海阔天空》的歌词
  "[ti:慢慢] [ar:张学友] [al:忘记你我做不到] [by:] [offset:0] [00:00.36]慢慢 - 张学友 [00:00.57]词：白进法 [00:00.75]曲：林隆璇 [00:00.93]编曲：吴庆隆 [00:01.39]
  [00:29.94]心慢慢疼慢慢冷 [00:33.83]
  [00:35.06]慢慢等不到爱人 [00:37.72]
  [00:40.22]付出一生收回几成 [00:45.45] [00:48.12]情不能分不能恨 [00:51.94]
  [00:53.12]不能太轻易信任 [00:55.29][00:56.47]真爱一回尽是伤痕 [00:59.86] [01:02.77]泪慢慢流慢慢收 [01:06.42]
  [01:07.67]慢慢变成了朋友 [01:10.57] [01:12.87]寂寞的夜独自承受 [01:18.41] [01:21.06]爱不能久不能够 [01:24.67]
   [01:25.96]不能太容易拥有 [01:28.25] [01:29.13]伤人的爱不堪回首 [01:34.85] [01:35.58]慢慢慢慢没有感觉 [01:38.18]
    [01:39.15]慢慢慢慢我被忽略 [01:41.94] [01:42.76]你何忍看我憔悴 [01:44.60] [01:46.39]没有一点点安慰 [01:48.52]
    [01:49.82]慢慢慢慢心变成铁 [01:52.97] [01:53.57]慢慢慢慢我被拒绝 [01:56.38] [01:57.39]你何忍远走高飞 [01:59.11]
    [02:00.87]要我如何收拾这爱的残缺 [02:06.96] [02:42.82]泪慢慢流慢慢收 [02:46.22] [02:47.58]慢慢变成了朋友 [02:50.14]
     [02:52.70]寂寞的夜独自承受 [02:58.06] [03:00.82]爱不能久不能够 [03:04.50] [03:05.76]不能太容易拥有 [03:08.11]
      [03:09.02]伤人的爱不堪回首 [03:14.74] [03:15.45]慢慢慢慢没有感觉 [03:18.04] [03:18.95]慢慢慢慢我被忽略 [03:21.57]
      [03:22.61]你何忍看我憔悴 [03:24.85] [03:26.02]没有一点点安慰 [03:28.39] [03:29.72]慢慢慢慢心变成铁 [03:32.66]
      [03:33.47]慢慢慢慢我被拒绝 [03:36.25] [03:37.22]你何忍远走高飞 [03:39.22] [03:40.79]要我如何收拾这爱的残缺 [03:46.76]
      [03:48.22]慢慢慢慢没有感觉 [03:50.71] [03:51.56]慢慢慢慢我被忽略 [03:54.29] [03:55.25]你何忍看我憔悴 [03:57.34]
      [03:58.88]没有一点点安慰 [04:01.38] [04:02.51]慢慢慢慢心变成铁 [04:05.17] [04:06.09]慢慢慢慢我被拒绝 [04:08.85]
      [04:09.80]你何忍远走高飞 [04:11.86] [04:13.35]要我如何收拾这爱的残缺",
   */
    public static List<LrcBean> parseLru2List(String json) {
        List<LrcBean> list = new ArrayList<>();

        try {
            String lruText = new JSONObject(json).getJSONObject("showapi_res_body").getString("lyric")
                    // 其实处理来自HTML上的字符串的转义问题方法很简单，把所有转义字符全部用replaceAll来解决就可以，转义成java的ASCII码格式
                    // 你可以字符串打印一下，看出来的转义字符是哪些，然后根据网上百度的转义字符表，把他们都打印出来
                    .replaceAll("&#58;", ":")
                    .replaceAll("&#10;", "\n")
                    .replaceAll("&#46;", ".")
                    .replaceAll("&#32;", " ")
                    .replaceAll("&#45;", "-")
                    .replaceAll("&#39;","'")
                    .replaceAll("&#13;", "\r");

            Log.d("google-my:", "parseLru2List: " + lruText);

            String[] split = lruText.split("\n");

            for (int i = 0; i < split.length; i++) {
                if (split[i].contains(".")) {
                    String min = split[i].substring(split[i].indexOf("[") + 1, split[i].indexOf("[") + 3);
                    String seconds = split[i].substring(split[i].indexOf(":") + 1, split[i].indexOf(":") + 3);
                    String mills = split[i].substring(split[i].indexOf(".") + 1, split[i].indexOf(".") + 3);

                    String text = split[i].substring(split[i].indexOf("]") + 1);

                    if(text == null || "".equals(text))
                    {
                        text = "music";
                    }

                    long startTime = Long.valueOf(min) * 60 * 1000 + Long.valueOf(seconds) * 1000 + Long.valueOf(mills) * 10;

                    LrcBean lruBean = new LrcBean();
                    lruBean.setStart(startTime);
                    lruBean.setText(text);

                    list.add(lruBean);
                    if (list.size() > 1) {
                        list.get(list.size() - 2).setEnd(startTime);
                    }
                    if (i == split.length - 1) // 最后一句歌词的最后时间是在json拿不到的，所以要自己设置，这里是加上100000毫秒。
                    {
                        list.get(list.size()-1).setEnd(startTime + 100000);
                    }
                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }




}
