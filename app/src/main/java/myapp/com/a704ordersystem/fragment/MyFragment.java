package myapp.com.a704ordersystem.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import myapp.com.a704ordersystem.R;
import myapp.com.a704ordersystem.activity.LoginActivity;
import myapp.com.a704ordersystem.application.MyApplication;

import static android.content.Context.MODE_PRIVATE;

/**
 * 首页
 */
public class MyFragment extends BaseFragment {

    private Button button;

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        button = findViewById(R.id.btn_login);

    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);

        if (MyApplication.token.equals("")) {
            button.setText("登录");
        } else {
            button.setText("切换账号");
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().toString().equals("登录")) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    clearToken();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 退出登录
     */
    public void clearToken() {
        // 获取SharedPreference
        SharedPreferences preference = getActivity().getSharedPreferences("login_info", MODE_PRIVATE);
        // 获取editor
        SharedPreferences.Editor editor = preference.edit();
        // 存入数据
        editor.clear();
        // 提交存入文件中
        if (editor.commit()) {
            System.out.println("退出登录成功...");
        } else {
            System.out.println("退出登录失败...");
        }
    }
}
