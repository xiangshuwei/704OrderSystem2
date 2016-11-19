package myapp.com.a704ordersystem.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import myapp.com.a704ordersystem.R;
import myapp.com.a704ordersystem.application.MyApplication;
import myapp.com.a704ordersystem.config.WebConfig;
import myapp.com.a704ordersystem.utils.HttpPost;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etaccount;
    private EditText etpassword;
    private Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListener();

    }

    private void initListener() {
        btnlogin.setOnClickListener(this);
    }

    private void initViews() {
        this.btnlogin = (Button) findViewById(R.id.btn_login);
        this.etpassword = (EditText) findViewById(R.id.et_password);
        this.etaccount = (EditText) findViewById(R.id.et_account);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
        }
    }

    public void login() {
        HttpPost httpPost = HttpPost.getInstance();
        httpPost.setOnResult(new HttpPost.OnResult() {
            @Override
            public RequestParams onParams() {
                RequestParams params = new RequestParams(WebConfig.GET_TOKEN);
                params.addBodyParameter("username", etaccount.getText().toString().trim());
                params.addBodyParameter("password", etpassword.getText().toString().trim());
                return params;
            }

            @Override
            public void onUsage(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("code")==200){
                        JSONObject data = object.getJSONObject("data");
                        String token = data.getString("token");
                        MyApplication.token = token;
                        saveToken(token);

                    }else {
                        Toast.makeText(LoginActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        httpPost.send();
    }

    /**
     * 保存Token
     *
     * @param token
     */
    public void saveToken(String token) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);

        if (editor.commit()) {
            System.out.println("保存成功...");
            startActivity(new Intent(this,MainActivity.class));
        } else {
            System.out.println("保存失败...");
        }

    }



}

