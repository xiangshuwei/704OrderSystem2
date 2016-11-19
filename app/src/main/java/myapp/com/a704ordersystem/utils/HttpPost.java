package myapp.com.a704ordersystem.utils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Http请求工具
 */

public class HttpPost {

    private static HttpPost httpPost;
    private HttpPost() {
    }

    public static HttpPost getInstance(){
        if (httpPost==null){
            httpPost = new HttpPost();
        }
        return httpPost;
    }

    public void send() {
        x.http().post(onResult.onParams(), new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                onResult.onUsage(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("请求出错...");
                System.out.println(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public interface OnResult {
        /**
         *
         * @return 自定义的RequestParame请求参数
         */
        RequestParams onParams();

        /**
         * 请求成功后的操作
         * @param result
         */
        void onUsage(String result);
    }

    private OnResult onResult;

    public void setOnResult(OnResult onResult) {
        this.onResult = onResult;
    }
}
