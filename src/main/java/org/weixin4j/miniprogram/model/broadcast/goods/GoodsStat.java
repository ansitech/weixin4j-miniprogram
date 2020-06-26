package org.weixin4j.miniprogram.model.broadcast.goods;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author yangqisheng
 * @since 1.0.3
 */
public class GoodsStat {

    /**
     * 商品ID
     */
    @JSONField(name = "goods_id")
    private long goodsId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 1:一口价，此时读price字段;
     *
     * 2:价格区间，此时price字段为左边界，price2字段为右边界;
     *
     * 3:折扣价，此时price字段为原价，price2字段为现价；
     */
    @JSONField(name = "price_type")
    private int priceType;
    /**
     * 价格1
     */
    private double price;
    /**
     * 价格2
     */
    private double price2;
    /**
     * 0：未审核，1：审核中，2:审核通过，3审核失败
     */
    @JSONField(name = "audit_status")
    private int auditStatus;
    /**
     * 2：表示是为api添加商品，否则不是api添加商品
     */
    @JSONField(name = "third_party_tag")
    private int thirdPartyTag;

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice2() {
        return price2;
    }

    public void setPrice2(double price2) {
        this.price2 = price2;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public int getThirdPartyTag() {
        return thirdPartyTag;
    }

    public void setThirdPartyTag(int thirdPartyTag) {
        this.thirdPartyTag = thirdPartyTag;
    }
}
