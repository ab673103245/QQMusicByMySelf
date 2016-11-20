package qianfeng.qqmusicbymyself.showmusic.model;


import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;
import qianfeng.qqmusicbymyself.util.JsonParse;
import qianfeng.qqmusicbymyself.util.NetUtil;

/**
 * Created by Administrator on 2016/10/26 0026.
 */
public class ISingleSongDataImpl implements ISingleSongData {
    @Override
    public void getSearchData(String keyword, final OnDataLoadListener onDataLoadListener) {
        Request request = new Request.Builder().url(String.format(NetUtil.QUERYBYSONGNAME,keyword)).build();
        NetUtil.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 接口如果没有实例化，即new的话，那肯定就是从外面传进来的了,一层一层传递进来
                onDataLoadListener.onFailed(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful())
                {
                    List<MusicBean> list = JsonParse.parse(response.body().string());
                    // 返回来一个list, 注意现在onDataLoadListener还尚未被初始化
                    onDataLoadListener.onSuccessful(list);
                }
            }
        });
    }


}
