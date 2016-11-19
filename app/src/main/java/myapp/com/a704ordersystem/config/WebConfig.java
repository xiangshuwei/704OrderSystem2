package myapp.com.a704ordersystem.config;

/**
 * 时 间: 2016/11/18 0018
 */

public class WebConfig {
    public static  final String http = "http://10.1.56.117/shop704/api.php";
    public static  final String API_HOST = "http://10.1.56.117/shop704/";

    /**
     * 1.登陆获取Token
     *   请求方式：POST
     *   参数1: username[String] 服务员账号
     *   参数2: password[String] 服务员密码
     */
    public static final String GET_TOKEN = http+"/protal/getToken";
    /**
     * 3.获取座位号
     *   请求方式：POST
     *   参数1: status [String] 筛选座位状态(可选)
     *   参数2: token  [String] 授权token
     */
    public static final String GET_SEATS = http+"/seat/getSeats";


    /**
     * 3.获取菜单
     *   请求方式：POST
     *   参数1: token  [String] 授权token
     */
    public static final String GET_MNEUS = http+"/Foods/getMenus";

    /**
     * 4.统一下单
     *  请求方式：POST
     *  参数1: token  [String] 授权token
     *  参数2: table_id  [String] 座位号
     *  参数3: menus  [String] 菜单JSON字符串
     */
    public static final String SEND_ORDER = http+"/Order/sendOrder";

    /**
     * 4..我的订单列表
     *  请求方式：POST
     *  参数1: token  [String] 授权token
     */
    public static final String MY_ORDER = http+"/Order/myOrder";

    /**
     * 5.统一确认收款
     * 请求方式：POST
     *  参数1: token  [String] 授权token
     *  参数2: order_no  订单号
     */
    public static final String END_ORDER = http+"/Order/endOrder";




}
