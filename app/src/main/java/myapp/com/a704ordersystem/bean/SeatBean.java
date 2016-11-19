package myapp.com.a704ordersystem.bean;

/**
 * 时 间: 2016/11/18 0018
 */

public class SeatBean {

    /**
     * id : 3
     * table_id : 2
     * mark : 6人座
     * status : 1
     */
    private String id;
    private String table_id;
    private String mark;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
