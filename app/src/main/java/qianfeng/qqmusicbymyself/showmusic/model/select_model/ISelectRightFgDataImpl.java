package qianfeng.qqmusicbymyself.showmusic.model.select_model;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import qianfeng.qqmusicbymyself.showmusic.model.OnDataLoadListener;
import qianfeng.qqmusicbymyself.util.Consts;
import qianfeng.qqmusicbymyself.util.JsonParse;
import qianfeng.qqmusicbymyself.util.NetUtil;

/**
 * Created by Administrator on 2016/11/2 0002.
 */
public class ISelectRightFgDataImpl implements ISelectRightFgData {

    @Override
    public void getNetSong(int id, final OnDataLoadListener onDataLoadListener) {
        final Request request = new Request.Builder().url(String.format(Consts.TOPICIDFINDLIST,id)).build();
        NetUtil.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onDataLoadListener.onFailed(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful())
                {
                    onDataLoadListener.onSuccessful(JsonParse.parseJson2List(response.body().string()));
                }
            }
        });
    }
}
