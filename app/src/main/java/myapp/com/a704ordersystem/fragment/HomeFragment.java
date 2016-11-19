package myapp.com.a704ordersystem.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import myapp.com.a704ordersystem.R;
import myapp.com.a704ordersystem.adapter.ItemTableAdapter;
import myapp.com.a704ordersystem.application.MyApplication;
import myapp.com.a704ordersystem.bean.SeatBean;
import myapp.com.a704ordersystem.config.WebConfig;
import myapp.com.a704ordersystem.utils.HttpPost;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {
    private GridView gridView;
    private ItemTableAdapter itemTableAdapter;
    public static List<SeatBean> seatBeanList;

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        gridView = findViewById(R.id.grid_view);
    }

    @Override
    public void initData() {
        super.initData();
        seatBeanList = new ArrayList<>();
        itemTableAdapter = new ItemTableAdapter(getActivity(),seatBeanList);
        getSeats();
    }

    public void getSeats(){
        HttpPost httpPost = HttpPost.getInstance();
        httpPost.setOnResult(new HttpPost.OnResult() {
            @Override
            public RequestParams onParams() {
                RequestParams params = new RequestParams(WebConfig.GET_SEATS);
                params.addBodyParameter("token", MyApplication.token);
                return params;
            }

            @Override
            public void onUsage(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("code")==200){

                        SeatBean seatBean = new SeatBean();
                        Gson gson = new Gson();

                        JSONArray array = object.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject item = array.getJSONObject(i);
                            seatBean = gson.fromJson(item.toString(),SeatBean.class);
                            seatBeanList.add(seatBean);

                        }
                        gridView.setAdapter(itemTableAdapter);
                        itemTableAdapter.notifyDataSetChanged();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        httpPost.send();
    }

}
