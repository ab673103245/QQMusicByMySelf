package qianfeng.qqmusicbymyself.showmusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.tencent.qq.QQ;
import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.util.LoginUtil;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        // 点击了登录按钮要做什么事？
        QQ qq = new QQ(this);
        Toast.makeText(LoginActivity.this, "已经点击了嘛，等待跳转就行", Toast.LENGTH_SHORT).show();

        if (qq.isClientValid()) {// 如果平台已经对这个Activity授权了
            PlatformDb platformDb = qq.getDb();

            LoginUtil.savePlatformDb(platformDb, this); // 这个方法有点鸡肋
        } else {
            // 如果没有授权
            qq.showUser(null);
        }

        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                // 登录成功时，回调,为什么又需要把数据存进sp中呢？
                // 平台这个不用自己new了
                PlatformDb platformDb = platform.getDb();
                LoginUtil.savePlatformDb(platformDb, LoginActivity.this); // 登录成功后，把数据存进sp中,其实这步才是核心。
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish(); // 点击了登录之后，会跳到别的界面，只要这个授权登陆的页面不是在最顶层，就finish掉
    }
}
