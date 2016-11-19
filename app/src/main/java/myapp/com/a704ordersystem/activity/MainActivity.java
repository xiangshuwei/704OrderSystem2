package myapp.com.a704ordersystem.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import myapp.com.a704ordersystem.R;
import myapp.com.a704ordersystem.fragment.BaseFragment;
import myapp.com.a704ordersystem.fragment.HomeFragment;
import myapp.com.a704ordersystem.fragment.MenuFragment;
import myapp.com.a704ordersystem.fragment.MyFragment;
import myapp.com.a704ordersystem.fragment.OrderFragment;

public class MainActivity extends AppCompatActivity {

    private FrameLayout flcontent;
    private RadioGroup rgnavigation;

    private List<BaseFragment> mBaseFragment;
    private FragmentTransaction fragmentTransaction;


    /**
     * 选中Fragment对应的位置
     */
//    private ;

    /**
     * 切换之前的Fragment
     */
    private BaseFragment mConten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
        setListener();

    }

    /**
     * 初始化控件
     */
    private void initView() {
        this.rgnavigation = (RadioGroup) findViewById(R.id.rg_navigation);
        this.flcontent = (FrameLayout) findViewById(R.id.fl_content);
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new HomeFragment());
        mBaseFragment.add(new MenuFragment());
        mBaseFragment.add(new OrderFragment());
        mBaseFragment.add(new MyFragment());

        /*1.根据对应的位置得到相应的Fragment*/
        BaseFragment toFragment = getFragment(0);
        //2.替换界面
        replaceFragment(mConten, toFragment);

    }


    private void setListener() {
        rgnavigation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            int position = 0;
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_menu:
                        position = 1;
                        break;
                    case R.id.rb_order:
                        position = 2;
                        break;
                    case R.id.rb_my:
                        position = 3;
                        break;
                    default:
                        position = 0;
                        break;
                }

                /*1.根据对应的位置得到相应的Fragment*/
                BaseFragment toFragment = getFragment(position);
                //2.替换界面
                replaceFragment(mConten, toFragment);

            }
        });
    }


    public void switchInterface(int position){
        /*1.根据对应的位置得到相应的Fragment*/
        BaseFragment toFragment = getFragment(position);
        //2.替换界面
        replaceFragment(mConten, toFragment);
    }

    /**
     * 用hide和show的方式替换界面显示Fragment,防止Fragment重复创建
     * @param from 刚才显示的Fragment
     * @param to   需要切换到的Fragment
     */
    private void replaceFragment(BaseFragment from, BaseFragment to) {
        //FragmentTransaction
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_content, to).commit();
        /*if (from != to) {
            mConten = to;

            //判断toFragment是否被添加
            if (!to.isAdded()) {
                //没有添加
                //隐藏from
                if (from != null) {
                    fragmentTransaction.hide(from);
                }
                fragmentTransaction.add(R.id.fl_content, to);
                fragmentTransaction.show(to).commit();
                //添加to
            } else {
                //已经添加
                //隐藏from
                if (from != null) {
                    fragmentTransaction.hide(from);
                }
                //添加to
                fragmentTransaction.show(to).commit();
            }
        }*/
    }

    /**
     * 根据对应的位置得到相应的Fragment
     *
     * @return
     */
    public BaseFragment getFragment(int position) {
        return mBaseFragment.get(position);
    }

}
