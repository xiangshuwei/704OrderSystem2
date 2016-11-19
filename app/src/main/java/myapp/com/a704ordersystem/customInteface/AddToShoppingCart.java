package myapp.com.a704ordersystem.customInteface;

import myapp.com.a704ordersystem.bean.MenuBean;

/**
 * 时 间: 2016/11/17 0017
 * 购物车接口
 */

public interface AddToShoppingCart {
    /**
     * 添加菜单
     * @param menuBean
     * @param position
     */
    void addMenu(MenuBean menuBean,int position);

    /**
     * 移除菜单
     * @param position
     */
    void removeMenu(MenuBean menuBean,int position);
}
