package org.weixin4j.miniprogram.model.broadcast.goods;

import java.util.List;

/**
 * @author yangqisheng
 * @since 1.0.3
 */
public class GoodsWareHouse {
    /**
     * 商品个数
     */
    private int total;
    /**
     * 商品列表
     */
    private List<GoodsStat> goodsList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<GoodsStat> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsStat> goodsList) {
        this.goodsList = goodsList;
    }
}
