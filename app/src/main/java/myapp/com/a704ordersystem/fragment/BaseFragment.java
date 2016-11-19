package myapp.com.a704ordersystem.fragment;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * 基础的Fragment，该类简化了原始类的生命周期，并增加了一些常用方法，强烈建议Fragment继承该类
 * 时 间: 2016/11/16 0016
 */

public abstract class BaseFragment extends Fragment implements IBaseFragment {
    /**
     * Fragment Content view
     */
    private View inflateView;
    /**
     * 所属Activity
     */
    private Activity activity;
    /**
     * 记录是否已经创建了,防止重复创建
     */
    private boolean viewCreated;
    private static final String TAG = "BaseFragment";

    /**
     * 显示Toast消息
     *
     * @param msg 消息文本
     */
    public final void showToast(@NonNull String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();;
    }

    /**
     * 通过ID查找控件
     *
     * @param viewId 控件资源ID
     * @param <VIEW> 泛型参数，查找得到的View
     * @return 找到了返回控件对象，否则返回null
     */
    final public <VIEW extends View> VIEW findViewById(@IdRes int viewId) {
        return (VIEW) inflateView.findViewById(viewId);
    }


    @CallSuper
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @CallSuper
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach... activity = " + activity.toString());
        this.activity = activity;
    }

    @Override
    final public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        // 防止重复调用onCreate方法，造成在initData方法中adapter重复初始化问题
        if (!viewCreated) {
            viewCreated = true;
            initData();
        }
    }

    @Override
    final public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        if (null == inflateView) {
            // 强制竖屏显示
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            int layoutResId = getCreateViewLayoutId();
            if (layoutResId > 0)
                inflateView = inflater.inflate(getCreateViewLayoutId(), container, false);

            // 解决点击穿透问题
            inflateView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
        return inflateView;
    }

    @Override
    final public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        if (viewCreated) {
            findView(view, savedInstanceState);
            initView(view, savedInstanceState);
            initDialog();
            initListener();
            viewCreated = false;
        }
    }

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
        // 解决ViewPager中的问题
        if (null != inflateView) {
            ((ViewGroup) inflateView.getParent()).removeView(inflateView);
        }
    }

    @CallSuper
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @CallSuper
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @CallSuper
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @CallSuper
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory: ");

    }

    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @CallSuper
    @Override
    public void initData() {
        Log.d(TAG, "initData: ");
    }

    @CallSuper
    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        Log.d(TAG, "findView: ");
    }

    @CallSuper
    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        Log.d(TAG, "initView: ");
    }

    @CallSuper
    @Override
    public void initListener() {
        Log.d(TAG, "initListener: ");
    }

    @CallSuper
    @Override
    public void initDialog() {
        Log.d(TAG, "initDialog: ");
    }
}
