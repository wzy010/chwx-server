package cn.net.easyinfo.common.exception.auth;


import cn.net.easyinfo.common.constant.CommonConstants;
import cn.net.easyinfo.common.exception.BaseException;

public class UserInvalidException extends BaseException {
    public UserInvalidException(String message) {
        super(message, CommonConstants.EX_USER_PASS_INVALID_CODE);
    }
}
