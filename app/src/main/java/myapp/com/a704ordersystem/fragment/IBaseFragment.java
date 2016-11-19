package myapp.com.a704ordersystem.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * 基础的Fragment接口
 * 时 间: 2016/11/16 0016
 */

public interface IBaseFragment {
    /**
     * 此方法用于返回Fragment设置ContentView的布局文件资源ID
     *
     * @return 布局文件资源ID
     */
    @LayoutRes
    int getCreateViewLayoutId();

    /**
     * 此方法用于初始化成员变量及获取Intent传递过来的数据
     * 注意：这个方法中不能调用所有的View，因为View还没有被初始化，要使用View在initView方法中调用
     */
    void initData();

    /**
     * 此方法用于初始化布局中所有的View，如果使用了View注入框架则不需要调用
     */
    void findView(View inflateView, Bundle savedInstanceState);

    /**
     * 此方法用于设置View显示数据
     */
    void initView(View inflateView, Bundle savedInstanceState);

    /**
     * 此方法用于设置监听器
     */
    void initListener();

    /**
     * 此方法用于初始化对话框
     */
    void initDialog();
}
