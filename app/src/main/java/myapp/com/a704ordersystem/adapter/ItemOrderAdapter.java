package myapp.com.a704ordersystem.adapter;

/**
 * 时 间: 2016/11/18 0018
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import myapp.com.a704ordersystem.R;
import myapp.com.a704ordersystem.application.MyApplication;
import myapp.com.a704ordersystem.bean.OrderBean;
import myapp.com.a704ordersystem.config.WebConfig;
import myapp.com.a704ordersystem.fragment.OrderFragment;
import myapp.com.a704ordersystem.utils.HttpPost;
import myapp.com.a704ordersystem.utils.TimeUtils;


public class ItemOrderAdapter extends BaseAdapter {

    private List<OrderBean> orderBeanList = new ArrayList<OrderBean>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemOrderAdapter(Context context, List<OrderBean> orderBeanList) {
        this.context = context;
        this.orderBeanList = orderBeanList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orderBeanList.size();
    }

    @Override
    public OrderBean getItem(int position) {
        return orderBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_order, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((OrderBean) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initializeViews(final OrderBean orderBean, ViewHolder holder) {
        holder.tvOrderNumber.setText(orderBean.getOrder_no());
        holder.tvTiem.setText(TimeUtils.formatData(orderBean.getCreate_time()));
        holder.tvMoney.setText(orderBean.getMoney());
        holder.btnSeatNumber.setText(orderBean.getTable_id() + "号桌");

        if (orderBean.getStatus().equals("1")){
            holder.btnPay.setText("已支付");
            holder.btnPay.setBackground(context.getDrawable(R.drawable.btn_pay_bg2));
        }else {
            holder.btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context)
                            .setTitle("确认订单")
                            .setMessage("请核对顾客是否付款!")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    pay(orderBean.getOrder_no());
                                }
                            })
                            .setNegativeButton("否", null)
                            .show();
                }
            });
        }

    }

    /**
     * 支付
     */
    private void pay(final String order_no) {
        HttpPost httpPost = HttpPost.getInstance();
        httpPost.setOnResult(new HttpPost.OnResult() {
            @Override
            public RequestParams onParams() {
                RequestParams params = new RequestParams(WebConfig.END_ORDER);
                params.addBodyParameter("order_no", order_no);
                params.addBodyParameter("token", MyApplication.token);
                return params;
            }

            @Override
            public void onUsage(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getInt("code") == 200) {
                        Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
                        OrderFragment.myOrder();
                    } else {
                        Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        httpPost.send();
    }

    private class ViewHolder {
        private TextView tvOrderNumber;
        private TextView tvTiem;
        private TextView tvMoney;
        private Button btnSeatNumber;
        private Button btnPay;

        ViewHolder(View view) {
            tvOrderNumber = (TextView) view.findViewById(R.id.tv_order_number);
            tvTiem = (TextView) view.findViewById(R.id.tv_tiem);
            tvMoney = (TextView) view.findViewById(R.id.tv_money);
            btnSeatNumber = (Button) view.findViewById(R.id.btn_seat_number);
            btnPay = (Button) view.findViewById(R.id.btn_pay);
        }
    }
}
