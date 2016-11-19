package myapp.com.a704ordersystem.adapter;

/**
 * 时 间: 2016/11/16 0016
 * 作 者: 郑亮
 * Q  Q : 1023007219
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import myapp.com.a704ordersystem.R;
import myapp.com.a704ordersystem.bean.SeatBean;

public class ItemTableAdapter extends BaseAdapter {

    private List<SeatBean> seatBeanList = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public ItemTableAdapter(Context context, List<SeatBean> seatBeanList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.seatBeanList = seatBeanList;
    }

    @Override
    public int getCount() {
        return seatBeanList.size();
    }

    @Override
    public SeatBean getItem(int position) {
        return seatBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_table, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((SeatBean) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(SeatBean seatBean, ViewHolder holder, int position) {
        holder.tvNumber.setText(position + 1 + "");
        if (seatBean.getStatus().equals("0")){
            //如果座位状态为0,就是不可用状态
            holder.ivTable.setImageResource(R.mipmap.table_checked);
        }
    }

    private class ViewHolder {
        private ImageView ivTable;
        private TextView tvNumber;
        ViewHolder(View view) {
            ivTable = (ImageView) view.findViewById(R.id.iv_table);
            tvNumber = (TextView) view.findViewById(R.id.tv_number);
        }
    }
}

