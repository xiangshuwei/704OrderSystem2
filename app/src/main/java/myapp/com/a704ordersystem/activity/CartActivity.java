package myapp.com.a704ordersystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import myapp.com.a704ordersystem.R;
import myapp.com.a704ordersystem.adapter.ItemCartAdapter;
import myapp.com.a704ordersystem.application.MyApplication;
import myapp.com.a704ordersystem.bean.CartBean;
import myapp.com.a704ordersystem.bean.SeatBean;
import myapp.com.a704ordersystem.bean.SendOrderMenu;
import myapp.com.a704ordersystem.config.WebConfig;
import myapp.com.a704ordersystem.fragment.HomeFragment;
import myapp.com.a704ordersystem.fragment.MenuFragment;
import myapp.com.a704ordersystem.utils.HttpPost;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    private ItemCartAdapter cartAdapter;
    private TextView tvclear;
    private RelativeLayout rlclear;
    private FloatingActionButton fabshoppingcar;
    private TextView tvshoppingmenunumber;
    private ListView lvcart;
    private TextView tvtotalmoney;
    private Spinner spinner;
    private TextView tvConfirmOrder;


    private String total_money;
    /**
     * 可用的餐桌位置
     */
    private List<String> usableSeatBeanList = new ArrayList<>();
    /**
     * 可用餐桌的table_id列表
     */
    private List<String> tableIdList = new ArrayList<>();

    /**
     * 选中的餐桌的table_id
     */
    private String table_id;
    /**
     * 下单传参的menu对象
     */
    private List<SendOrderMenu> sendOrderMenus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initData();
        findViews();
        initViews();
        initListener();


    }

    /**
     * 初始化点击事件
     */
    private void initListener() {
        fabshoppingcar.setOnClickListener(this);
        tvConfirmOrder.setOnClickListener(this);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        float money = 0;
        //初始化购物车的Adapter
        cartAdapter = new ItemCartAdapter(this, MenuFragment.cartBeanList);
        for (CartBean cartBean : MenuFragment.cartBeanList) {
            SendOrderMenu sendOrderMenu = new SendOrderMenu();
            sendOrderMenu.setId(cartBean.getMenuBean().getId());
            sendOrderMenu.setName(cartBean.getMenuBean().getName());
            sendOrderMenu.setCount(cartBean.getCount() + "");
            sendOrderMenu.setMark(cartBean.getMenuBean().getMark());
            sendOrderMenus.add(sendOrderMenu);
            money += cartBean.getCount() * Float.valueOf(cartBean.getMenuBean().getPrice());

        }

        total_money = "￥" + money;
    }

    /**
     * 找到布局文件
     */
    private void findViews() {
        this.tvtotalmoney = (TextView) findViewById(R.id.tv_total_money);
        this.lvcart = (ListView) findViewById(R.id.lv_cart);
        this.tvshoppingmenunumber = (TextView) findViewById(R.id.tv_shopping_menu_number);
        this.fabshoppingcar = (FloatingActionButton) findViewById(R.id.fab_shopping_car);
        this.spinner = (Spinner) findViewById(R.id.sp_table_number);
        this.tvConfirmOrder = (TextView) findViewById(R.id.tv_confirm_order);
    }

    /**
     * 初始化Views
     */
    private void initViews() {
        tvtotalmoney.setText(total_money);
        lvcart.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        // 建立数据源
        for (SeatBean seatBean : HomeFragment.seatBeanList) {
            if (seatBean.getStatus().equals("1")) {
                usableSeatBeanList.add(seatBean.getTable_id() + "号桌" + " (" + seatBean.getMark() + ")");
                tableIdList.add(seatBean.getTable_id());

            }
        }
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, usableSeatBeanList);
        //绑定 Adapter到控件
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //的到选中的餐桌的table_id
                table_id = tableIdList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_shopping_car:
                finish();
                break;
            case R.id.tv_confirm_order:
                sendOrder();
                break;
        }
    }

    private void sendOrder() {

        final Gson gson = new Gson();
        System.out.println("=======================" + gson.toJson(sendOrderMenus));

        HttpPost httpPost = HttpPost.getInstance();
        httpPost.setOnResult(new HttpPost.OnResult() {
            @Override
            public RequestParams onParams() {

                RequestParams params = new RequestParams(WebConfig.SEND_ORDER);
                params.addBodyParameter("token", MyApplication.token);
                params.addBodyParameter("table_id", table_id);
                params.addBodyParameter("menus", gson.toJson(sendOrderMenus));
                return params;
            }

            @Override
            public void onUsage(String result) {
//                System.out.println(result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("code") == 200) {
                        Toast.makeText(CartActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CartActivity.this,MainActivity.class));

                    } else {
                        Toast.makeText(CartActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        httpPost.send();
    }


}
