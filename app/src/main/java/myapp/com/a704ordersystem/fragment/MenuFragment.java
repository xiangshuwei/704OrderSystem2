package myapp.com.a704ordersystem.fragment;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myapp.com.a704ordersystem.R;
import myapp.com.a704ordersystem.activity.CartActivity;
import myapp.com.a704ordersystem.adapter.ItemMenuAdapter;
import myapp.com.a704ordersystem.application.MyApplication;
import myapp.com.a704ordersystem.bean.CartBean;
import myapp.com.a704ordersystem.bean.MenuBean;
import myapp.com.a704ordersystem.config.WebConfig;
import myapp.com.a704ordersystem.customInteface.AddToShoppingCart;
import myapp.com.a704ordersystem.utils.HttpPost;

/**
 * 首页
 */
public class MenuFragment extends BaseFragment {
    private ListView listView;

    private ItemMenuAdapter itemMenuAdapter;
    /**
     * 所有菜单
     */
    List<MenuBean> menuBeanList = new ArrayList<>();
    public static List<CartBean> cartBeanList = new ArrayList<>();

    /**
     * 购物车中的菜单
     */
    private Map<String, CartBean> cartBeanMap;
    /**
     * 购物车图标
     */
    private FloatingActionButton fabShoppingCar;

/*=====================================================================================*/
    /**
     * 购物车根布局
     */
    private RelativeLayout dialogLayout;


    @Override
    public int getCreateViewLayoutId() {
        return R.layout.fragment_menu;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        listView = findViewById(R.id.lv_menu);
        fabShoppingCar = findViewById(R.id.fab_shopping_car);
    }


    @Override
    public void initData() {
        super.initData();
        //初始化购物车菜单
        cartBeanMap = new HashMap<>();
        //获取所有菜单
        getMenus();

        //初始化所有菜单的Adapter
        itemMenuAdapter = new ItemMenuAdapter(getContext(), menuBeanList);


        itemMenuAdapter.setAddToShoppingCart(new AddToShoppingCart() {

            /**
             * 添加菜单
             * @param menuBean
             * @param position
             */
            @Override
            public void addMenu(MenuBean menuBean, int position) {
                CartBean cartBean = cartBeanMap.get(menuBean.getId());

                if (cartBean == null) {
                    cartBean = new CartBean();
                    cartBean.setMenuBean(menuBean);
                    cartBean.setCount(1);
                    cartBeanMap.put(menuBean.getId(), cartBean);
                } else {
                    cartBean.setCount(cartBean.getCount() + 1);
                }
                fabShoppingCar.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary)));
            }

            /**
             * 移除菜单
             * @param menuBean
             * @param position
             */
            @Override
            public void removeMenu(MenuBean menuBean, int position) {
                CartBean cartBean = cartBeanMap.get(menuBean.getId());

                if (cartBean == null) {
                    return;
                } else {
                    if (cartBean.getCount() <= 1) {
                        System.out.println("数量:"+cartBean.getCount());
                        cartBeanMap.remove(menuBean.getId());
                    }else {
                        cartBean.setCount(cartBean.getCount() - 1);
                    }
                }

                if (cartBeanMap.size() ==0){
                    fabShoppingCar.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.secondary_text)));
                }

            }
        });
    }

    /**
     * 获取餐厅菜单
     */
    private void getMenus() {
        HttpPost httpPost = HttpPost.getInstance();
        httpPost.setOnResult(new HttpPost.OnResult() {
            @Override
            public RequestParams onParams() {
                RequestParams params = new RequestParams(WebConfig.GET_MNEUS);
                params.addBodyParameter("token", MyApplication.token);
                return params;
            }

            @Override
            public void onUsage(String result) {
                menuBeanList.clear();
                System.out.println("返回值:" + result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("code") == 200) {
                        MenuBean menuBean = new MenuBean();
                        Gson gson = new Gson();
                        JSONObject data = object.getJSONObject("data");
                        JSONArray array = data.getJSONArray("foods");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject item = array.getJSONObject(i);
                            menuBean = gson.fromJson(item.toString(), MenuBean.class);
                            menuBeanList.add(menuBean);

                        }

                        listView.setAdapter(itemMenuAdapter);
                        itemMenuAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        httpPost.send();
    }

    @Override
    public void initListener() {
        super.initListener();
        fabShoppingCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("购物车的大小:" + cartBeanMap.size());
                //从Map中取得菜单集合
                cartBeanList.clear();
                for (Map.Entry<String, CartBean> entry : cartBeanMap.entrySet()) {
                    cartBeanList.add(entry.getValue());
                }
                if (cartBeanMap.size()>0){
                    Intent intent = new Intent(getActivity(), CartActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getContext(), "请先点菜!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
