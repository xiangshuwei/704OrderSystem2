package myapp.com.a704ordersystem.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import myapp.com.a704ordersystem.R;
import myapp.com.a704ordersystem.adapter.ItemOrderAdapter;
import myapp.com.a704ordersystem.application.MyApplication;
import myapp.com.a704ordersystem.bean.OrderBean;
import myapp.com.a704ordersystem.config.WebConfig;
import myapp.com.a704ordersystem.utils.HttpPost;

/**
 * 首页
 */
public class OrderFragment extends BaseFragment {

    private static List<OrderBean> orderBeanList;
    private static ListView lvOrder;
    private static ItemOrderAdapter itemOrderAdapter;

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        lvOrder = findViewById(R.id.lv_order);
    }

    @Override
    public void initData() {
        super.initData();
        orderBeanList = new ArrayList<>();
        itemOrderAdapter = new ItemOrderAdapter(getActivity(),orderBeanList);
        myOrder();

    }

    /**
     * 获得订单信息
     */
    public static void myOrder() {
        HttpPost httpPost = HttpPost.getInstance();
        httpPost.setOnResult(new HttpPost.OnResult() {
            @Override
            public RequestParams onParams() {
                RequestParams params = new RequestParams(WebConfig.MY_ORDER);
                params.addBodyParameter("token", MyApplication.token);
                return params;
            }

            @Override
            public void onUsage(String result) {
                orderBeanList.clear();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("code")==200){
                        OrderBean orderBean = new OrderBean();
                        Gson gson = new Gson();

                        JSONObject data = object.getJSONObject("data");
                        JSONArray array = data.getJSONArray("order_list");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject item = array.getJSONObject(i);
                            orderBean = gson.fromJson(item.toString(),OrderBean.class);
                            orderBeanList.add(orderBean);
                        }

                        lvOrder.setAdapter(itemOrderAdapter);
                        itemOrderAdapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        httpPost.send();
    }
}
