package com.forfries.constant;

/**
 * 状态常量
 */
public class StatusConstant {
    //禁用
    public static final String DISABLED = "0";

    //正常
    public static final String NORMAL = "1";

    //电影相关状态
    //未上映
    public static final String NOT_RELEASE = "2";

    //已下架
    public static final String END_RELEASE = "3";

    //影院影厅状态
    //装修中
    public static final String DURING_RENOVATION = "4";
    //座位未创建
    public static final String SEAT_NOT_CREATED = "11";

    //座位状态
    //维修中
    public static final String IN_MAINTENANCE = "5";
    public static final String OCCUPIED= "15";

    //票状态（座位场次状态）
    public static final String AVAILABLE = "13";

    public static final String NOT_AVAILABLE = "14";


    //订单状态
    //未支付
    public static final String UNPAID = "6";

    //已支付
    public static final String PAID = "7";

    //已出票
    public static final String TICKETS_HAVE_BEEN_ISSUED = "8";

    //订单完成
    public static final String COMPLETE = "9";

    //已退款
    public static final String REFUNDED = "10";

    //已取消
    public static final String CANCELED = "12";

    //评论状态
    //待审核
    public static final String PENDING_REVIEW = "17";

    //审核通过
    public static final String PASSED = "16";
//    public static final String
//    public static final String
//    public static final String
//    public static final String

}
