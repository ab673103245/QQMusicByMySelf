package qianfeng.qqmusicbymyself.showmusic.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.lenve.customshapeimageview.CustomShapeImageView;

import java.util.List;

import cn.sharesdk.tencent.qq.QQ;
import qianfeng.qqmusicbymyself.BaseFragement;
import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.beforemusic.BeforeMusicFragment;
import qianfeng.qqmusicbymyself.localmusic.view.LocalMusicFragment;
import qianfeng.qqmusicbymyself.showmusic.LoginActivity;
import qianfeng.qqmusicbymyself.showmusic.MainActivity;
import qianfeng.qqmusicbymyself.showmusic.model.bean.StringLoveBean;
import qianfeng.qqmusicbymyself.showmusic.model.bean.UserBean;
import qianfeng.qqmusicbymyself.showmusic.presenter.MineFgPresenter;
import qianfeng.qqmusicbymyself.showmusic.view.IMineFgView;
import qianfeng.qqmusicbymyself.showmusic.view.adapter.MyListViewAdapter;
import qianfeng.qqmusicbymyself.showmusic.view.view.MyListView;
import qianfeng.qqmusicbymyself.util.LoginUtil;

/**
 * Created by Administrator on 2016/10/26 0026.
 */
public class MineFragment extends BaseFragement implements IMineFgView {
    private View view;
    private RelativeLayout re_layout;
    private TextView play_list_size_tv;
    private MyListView myListView;
    private MineFgPresenter mineFgPresenter = new MineFgPresenter(this);
    private LinearLayout ll_localMusic;
    private LinearLayout ll_before;
    private RelativeLayout loginLayout;
    private TextView username_Tv;
    private CustomShapeImageView userface_Iv;

    private UserBean loadedUserBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.mine_fg_layout, container, false);
        }

        initView(view);

        mineFgPresenter.start();// 初始化MyListView

        // 会调用initLoginlayout()方法
//        mineFgPresenter.loadUserInfoFromSP(getActivity()); // 我这种逻辑，是可以主动从sp中加载数据，因为用户名都是请登录

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 从其他界面回来，会调用这个方法, 在这里重新更新UI就可以了
        updateUserInfo(this.loadedUserBean);
    }

    private void initView(View view) {
        re_layout = ((RelativeLayout) view.findViewById(R.id.re_layout));

        play_list_size_tv = ((TextView) view.findViewById(R.id.play_list_size_tv));
        myListView = ((MyListView) view.findViewById(R.id.play_list_lv));
        ll_localMusic = ((LinearLayout) view.findViewById(R.id.ll_localMusic));
        ll_before = (LinearLayout) view.findViewById(R.id.ll_before);

        loginLayout = ((RelativeLayout) view.findViewById(R.id.loginLayout));
        username_Tv = ((TextView) view.findViewById(R.id.username));
        userface_Iv = ((CustomShapeImageView) view.findViewById(R.id.userface));

        re_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ll, new SearchFragment()).commit();
                MainActivity.ACTIVITY_STATE = 1;
            }
        });

        ll_localMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ll, new LocalMusicFragment()).commit();
                MainActivity.ACTIVITY_STATE = 1;
            }
        });

        ll_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ll, new BeforeMusicFragment()).commit();
                MainActivity.ACTIVITY_STATE = 1;
            }
        });

        loginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username_Tv.getText().toString().equals("请登录")) {
                    // 跳转到登录界面
                    startActivity(new Intent(getActivity(), LoginActivity.class));

                } else {
                    // 如果不是登录界面的话，是不是弹出对话框？
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            QQ qq = new QQ(getActivity());

                            qq.removeAccount();// 清除授权信息

                            // 清空sp中的信息
                            LoginUtil.savePlatformDb(null,getActivity());

                            // 什么时候会调用这个方法？肯定不是在第一次加载的时候，而是在用户已经登录了，跳转过了到第二个界面的时候，就是调用这个方法的时候，已经从本界面跳到别的界面过了，所以。。肯定会调用onResume()方法
                            // 还要记得更新UI
                            mineFgPresenter.loadUserInfoFromSP(getActivity());
                            // 怪不得要用一个本地变量来接收this.bean，因为只有Data中有数据，
                            // 而Presenter的所有方法都不能有返回值，所以这个值只能在一开始的时候，
                            // 用Presenter中的那个和Data交互时，拿到的那个UserBean，传过来，一直操作它。

                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                            .setTitle("提示")
                            .setMessage("退出登录？")
                            .create().show();
                }
            }
        });


    }

    @Override
    public void initMyListView(final List<StringLoveBean> list) {
        MyListViewAdapter adapter = new MyListViewAdapter(list, getActivity());
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), list.get(position).getSongname(), Toast.LENGTH_SHORT).show();
            }
        });

        play_list_size_tv.setText(list.size() + "");

    }

    @Override
    public void initLoginlayout(UserBean userBean) {
        this.loadedUserBean = userBean;
        if(username_Tv != null) // 如果第一次加载的时候，sp已经有数据了，但
        {
            updateUserInfo(userBean);
        }
    }

    public void updateUserInfo(UserBean userBean)
    {
        if (userBean != null) {
            username_Tv.setText(userBean.getUsername());
            if(userBean.getUserface()!= null && !"".equals(userBean.getUserface()))
            {
                Picasso.with(getActivity()).load(userBean.getUserface()).into(userface_Iv);
            }else
            {
                userface_Iv.setImageResource(R.drawable.default1);
            }
        }
    }


}
