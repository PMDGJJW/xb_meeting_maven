package com.constants;

/**
 * @auth jian j w
 * @date 2020/3/17 18:04
 * @Description
 */
public class SysConstant {
    //验证码文本
    public static final String SESSION_PIC_CODE="sessionPicCode";

    //登录用户
    public static final String SESSION_LOGIN_CHECK="session_login_check";

    //逻辑删除
    public static final String USER_DEL_CODE="0";

    //上传图片默认路径
    public static final String SYS_USER_IMGBASE="G:\\img\\head\\";

    //免密登录
    public static final String COOKIE_LOGIN_USER="cookie_login_user";

    //用户管理表头
    public static final String [] headers = new String[]{"用户名", "真实姓名", "性别", "年龄", "创建时间", "创建人"};

    //邮箱验证码
    public static  final String SEND_EMAIL_TOKEN="send_email_token";

    //会议集合
    public static final String MEETLISTALL="meetlistall";
}
