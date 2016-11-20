package qianfeng.qqmusicbymyself.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import qianfeng.qqmusicbymyself.showmusic.view.IMainActivityView;
import qianfeng.qqmusicbymyself.util.Consts;
import qianfeng.qqmusicbymyself.util.PlayerUtil;

/**
 * Created by Administrator on 2016/10/31 0031.
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Consts.BROADRECEIVER1))
        {
            ((IMainActivityView) context).updataUI(PlayerUtil.CURRENT_MUSICBEAN);
        }else if(intent.getAction().equals(Consts.BROADRECEIVER2))
        {
            ((IMainActivityView) context).updataUI(PlayerUtil.CURRENT_MUSICBEAN);
        }else if(intent.getAction().equals(Consts.BROADRECEIVER3))
        {
            ((IMainActivityView) context).updataUIBottom();
        }

    }
}
