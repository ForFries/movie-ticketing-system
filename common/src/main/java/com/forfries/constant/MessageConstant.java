package com.forfries.constant;

/**
 * 信息提示常量类
 */
public class MessageConstant {

    public static final String SYSTEM_ERROR = "系统错误，请联系管理员";

    public static final String PASSWORD_ERROR = "密码错误";
    public static final String ACCOUNT_NOT_FOUND = "账号不存在";
    public static final String COULD_NOT_FIND = "无对应";
    public static final String INCONSISTENT_ID = "ID不一致，请检查Path 参数和Body id参数是否一致";
    public static final String INCONSISTENT_CINEMA_ID = "cinemaId不一致，请检查Query 参数 cinemaId和Body 参数cinemaId是否一致";

    public static final String PERMISSION_ERROR = "权限错误，请检查token是否具有该权限！";
    public static final String PERMISSION_ERROR_CINEMA = "权限错误，请检查请求的id是否属于该Cinema";

    public static final String JWT_ERROR = "JWT令牌解析错误，请检查token";
    public static final String SYSTEM_ERROR_ILLEGAL_ARGUMENT = "方法中没有含有id的参数";
    public static final String SYSTEM_ERROR_NO_MAPPER = "无法反射找到Mapper";


}
