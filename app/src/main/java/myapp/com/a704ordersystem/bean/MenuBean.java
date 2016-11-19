package myapp.com.a704ordersystem.bean;

/**
 * 时 间: 2016/11/16 0016
 * 菜单数据对象
 */

public class MenuBean {

    /**
     * id : 6
     * name : 黄猛击
     * price : 100.00
     * mark : 1:中辣,2特辣
     * sort : null
     * status : 0
     * img_path : ./Public/uploads/2016-11-18/582e68c7b5a27.jpg
     */

    private String id;
    private String name;
    private String price;
    private String mark;
    private Object sort;
    private String status;
    private String img_path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }
}
