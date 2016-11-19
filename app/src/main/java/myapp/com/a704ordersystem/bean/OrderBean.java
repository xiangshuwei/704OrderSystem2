package myapp.com.a704ordersystem.bean;

/**
 * 时 间: 2016/11/18 0018
 * 订单数据对象
 */

public class OrderBean {

    /**
     * id : 3
     * order_no : G201611181111B183973
     * user_name : null
     * waiter_id : 8
     * table_id : 2
     * menus :
     * money : 100.00
     * create_time : 0
     * end_time : 1479447205
     * status : 1
     */

    private String id;
    private String order_no;
    private Object user_name;
    private String waiter_id;
    private String table_id;
    private String menus;
    private String money;
    private String create_time;
    private String end_time;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public Object getUser_name() {
        return user_name;
    }

    public void setUser_name(Object user_name) {
        this.user_name = user_name;
    }

    public String getWaiter_id() {
        return waiter_id;
    }

    public void setWaiter_id(String waiter_id) {
        this.waiter_id = waiter_id;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
