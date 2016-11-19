package myapp.com.a704ordersystem.adapter;

/**
 * 时 间: 2016/11/18 0018
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import myapp.com.a704ordersystem.R;
import myapp.com.a704ordersystem.bean.CartBean;

public class ItemCartAdapter extends BaseAdapter {

    private List<CartBean> cartBeanList = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemCartAdapter(Context context,List<CartBean> cartBeanList) {
        this.context = context;
        this.cartBeanList = cartBeanList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cartBeanList.size();
    }

    @Override
    public CartBean getItem(int position) {
        return cartBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_cart, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((CartBean)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(final CartBean cartBean, ViewHolder holder) {

        holder.tvName.setText(cartBean.getMenuBean().getName());
        holder.tvMoney.setText("单价:￥"+cartBean.getMenuBean().getPrice());
        holder.tvNumber.setText("× "+cartBean.getCount());

    }

    private class ViewHolder {
        private TextView tvName;
        private RelativeLayout rlSubtract;
        private TextView tvNumber;
        private TextView tvMoney;


        ViewHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.tv_name);
            rlSubtract = (RelativeLayout) view.findViewById(R.id.rl_subtract);
            tvNumber = (TextView) view.findViewById(R.id.tv_number);
            tvMoney = (TextView) view.findViewById(R.id.tv_money);
        }
    }
}

