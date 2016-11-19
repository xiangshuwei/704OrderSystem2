package myapp.com.a704ordersystem.adapter;

/**
 * 时 间: 2016/11/16 0016
 * 菜单列表适配器
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import myapp.com.a704ordersystem.R;
import myapp.com.a704ordersystem.bean.MenuBean;
import myapp.com.a704ordersystem.config.WebConfig;
import myapp.com.a704ordersystem.customInteface.AddToShoppingCart;

public class ItemMenuAdapter extends BaseAdapter{

    public static int[] number;

    private List<MenuBean> menuBeanList = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    private AddToShoppingCart addToShoppingCart;

    public void setAddToShoppingCart(AddToShoppingCart addToShoppingCart) {
        this.addToShoppingCart = addToShoppingCart;
    }

    public ItemMenuAdapter(Context context, List<MenuBean> menuBeanList) {
        this.context = context;
        this.menuBeanList = menuBeanList;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return menuBeanList.size();
    }

    @Override
    public MenuBean getItem(int position) {
        return menuBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_menu, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((MenuBean)getItem(position), (ViewHolder) convertView.getTag(),position);
        return convertView;
    }

    private void initializeViews(final MenuBean menuBean, final ViewHolder holder, final int position) {
        number = new int[menuBeanList.size()];

        holder.tvName.setText(menuBean.getName());
        holder.tvDescription.setText(menuBean.getMark());
        holder.tvMoney.setText("￥"+menuBean.getPrice());
        System.out.println(WebConfig.API_HOST+menuBean.getImg_path());
        //加载图片
        Glide.with(context)
                .load(WebConfig.API_HOST+menuBean.getImg_path())
                .crossFade()//淡出效果
                .override(320,320)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//开启缓存
                .thumbnail(0.3f)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.ivImag);

        //添加菜单事件
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number[position]++;
                holder.rlSubtract.setVisibility(View.VISIBLE);
                holder.tvNumber.setText(number[position]+"");
                //加入购物车
                addToShoppingCart.addMenu(menuBean,position);
            }
        });
        //删除菜单事件
        holder.ivSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number[position]--;
                if (number[position]==0){
                    holder.rlSubtract.setVisibility(View.GONE);
                }else {
                    holder.tvNumber.setText(number[position]+"");
                }
                addToShoppingCart.removeMenu(menuBean,position);
            }
        });



    }


    private class ViewHolder {
        private ImageView ivImag;
        private TextView tvName;
        private TextView tvDescription;
        private TextView tvMoney;
        private ImageView ivAdd;
        private TextView tvNumber;
        private ImageView ivSubtract;
        private RelativeLayout rlSubtract;

        ViewHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvDescription = (TextView) view.findViewById(R.id.tv_description);
            tvMoney = (TextView) view.findViewById(R.id.tv_money);
            ivAdd = (ImageView) view.findViewById(R.id.iv_add);
            tvNumber = (TextView) view.findViewById(R.id.tv_number);
            ivSubtract = (ImageView) view.findViewById(R.id.iv_subtract);
            rlSubtract = (RelativeLayout) view.findViewById(R.id.rl_subtract);
            ivImag = (ImageView) view.findViewById(R.id.iv_img);
        }
    }
}

