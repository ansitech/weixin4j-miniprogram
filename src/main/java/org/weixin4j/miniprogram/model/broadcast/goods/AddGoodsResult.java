package org.weixin4j.miniprogram.model.broadcast.goods;

/**
 * @author yangqisheng
 * @since 1.0.3
 */
public class AddGoodsResult {

    /**
     * 商品ID
     */
    private long goodsId;
    /**
     * 审核单ID
     */
    private long auditId;

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public long getAuditId() {
        return auditId;
    }

    public void setAuditId(long auditId) {
        this.auditId = auditId;
    }
}
