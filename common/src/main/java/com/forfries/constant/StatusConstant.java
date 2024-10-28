package com.forfries.constant;

/**
 * 状态常量
 */
public class StatusConstant {
    //禁用
    public static final Integer DISABLED = 0;

    //正常
    public static final Integer NORMAL = 1;

    //电影相关状态
    //未上映
    public static final Integer NOT_RELEASE = 2;

    //已下架
    public static final Integer END_RELEASE = 3;

    //影院影厅状态
    //装修中
    public static final Integer DURING_RENOVATION = 4;

    //座位状态
    //维修中
    public static final Integer IN_MAINTENANCE = 5;

    //订单状态
    //未支付
    public static final Integer UNPAID = 6;

    //已支付
    public static final Integer PAID = 7;

    //已出票
    public static final Integer TICKETS_HAVE_BEEN_ISSUED = 8;

    //订单完成
    public static final Integer COMPLETE = 9;

    //已退款
    public static final Integer REFUNDED = 10;

}
