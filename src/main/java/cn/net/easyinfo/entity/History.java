package cn.net.easyinfo.entity;

import lombok.Data;

@Data
public class History {
    private static String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    private int index;
    private int id;
    private int userId;
    private String name;
    private String operType;
    private String operTime;
    private int result;
    private String ip;
    private String city;
    private String resultStr;
    public String getOperTime() {
        if (operTime != null && !operTime.equals("") && operTime.length() > DATEFORMAT.length()) {
            operTime = operTime.substring(0, DATEFORMAT.length());
        }
        return operTime;
    }
}
