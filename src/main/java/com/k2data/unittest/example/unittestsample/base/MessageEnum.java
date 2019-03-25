package com.k2data.unittest.example.unittestsample.base;

/**
 * Created by cx on 2018-10-09.
 */
public enum MessageEnum {
    OK(0, "成功"),
    PARAM_ERROR(1, "缺少参数"),
    FILE_ERROR(10, "必须选择一个非空文件"),
    FILE_SIZE_ERROR(11, "文件大小不能超过1M"),
    FILE_TYPE_ERROR(12, "文件类型错误"),
    DATASOURCE_ERROR(21, "数据库操作不成功"),
    NO_DATA_ERROR(22, "无数据"),
    EXTERNAL_API_ERROR(31, "外部接口调用错误"),
    INVALID_BIRTHDAY_ERROR(41, "生日在当前日期之后"),
    UNKOWN_ERROR(-1, "未知错误");

    private Integer code;
    private String message;

    MessageEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
