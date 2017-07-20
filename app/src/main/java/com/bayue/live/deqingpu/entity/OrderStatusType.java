package com.bayue.live.deqingpu.entity;

/**
 * Created by BaYue on 2017/7/4 0004.
 * email : 2651742485@qq.com
 */

public enum OrderStatusType implements ICnEnum{


    CANCEL("cancel", "取消订单", 1),
    COMMENT("comment", "立即评价", 2),
    DELETE("delete", "删除订单", 3),
    LOGISTICS("logistics", "查看物流", 4),
    PAY("pay", "立即付款", 5),
    RECEIPT("receipt", "确认收货", 6),
    REFUND("refund", "申请退款", 7);

    // 成员变量
    String name;
    String cnName;
    int value;

    // 构造方法
    OrderStatusType(String name, String cnName, int value) {
        this.name = name;
        this.cnName = cnName;
        this.value = value;
    }

    //覆盖方法
    @Override
    public String toString() {
        return "[" + this.value + ":" + this.name + "][" + this.cnName + "]";
    }

    @Override
    public String toCnName() {
        return this.cnName;
    }

    @Override
    public String toName() {
        return this.name;
    }

    @Override
    public int toValue() {
        return this.value;
    }

    @Override
    public CnEnum toItem() {
        return new CnEnum(this);
    }
}
