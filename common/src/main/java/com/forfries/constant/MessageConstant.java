package com.forfries.constant;

/**
 * 信息提示常量类
 */
public class MessageConstant {
//  public static final String
    public static final String SYSTEM_ERROR = "系统错误，请联系管理员";

    public static final String PASSWORD_ERROR = "密码错误";
    public static final String ACCOUNT_NOT_FOUND = "账号不存在";
    public static final String COULD_NOT_FIND = "无对应";
    public static final String INCONSISTENT_ID = "ID不一致，请检查Path 参数和Body id参数是否一致";
    public static final String INCONSISTENT_CINEMA_ID = "cinemaId不一致，请检查Query 参数 cinemaId和Body 参数cinemaId是否一致";
    public static final String INCONSISTENT_SEAT_ID = "seatId不一致，请检查Schedule的screeningHallId与seatId的screeningHallId是否一致";


    public static final String PERMISSION_ERROR = "权限错误，请检查token是否具有该权限！";
    public static final String PERMISSION_ERROR_NULL = "权限错误，请检查Query 参数是否含有cinemaId";
    public static final String PERMISSION_ERROR_ID = "权限错误，请检查token中cinemaId与Query 参数cinemaId是否一致";
    public static final String PERMISSION_ERROR_USER = "权限错误，目前不支持访问User界面";
    public static final String PERMISSION_ERROR_CINEMA = "权限错误，请检查请求的id是否属于该Cinema";
    public static final String PERMISSION_ERROR_USER_ID = "权限错误，请检查请求的id是否属于该User";

    public static final String JWT_ERROR = "JWT令牌解析错误，请检查token";
    public static final String SYSTEM_ERROR_ILLEGAL_ARGUMENT = "方法中没有含有id的参数";
    public static final String SYSTEM_ERROR_NO_MAPPER = "无法反射找到Mapper";

    public static final String TIME_CONFLICT = "排片时间冲突！请检查！";
    public static final String TIME_CONFLICT_TIME = "开始时间必须早于结束时间！";

    public static final String REPEAT_CREATION = "座位不能重复创建！";

    public static final String STANDARDIZATION_ERROR = "请核查你的数据与API文档是否一致！";

    public static final String SEAT_OCCUPIED = "座位已经被占领啦！请重新选座";
    public static final String SEAT_IN_MAINTENANCE = "座位正在维修！请重新选座";


//  public static final String
//  public static final String
//  public static final String
//  public static final String
//  public static final String
//  public static final String
//  public static final String

}
