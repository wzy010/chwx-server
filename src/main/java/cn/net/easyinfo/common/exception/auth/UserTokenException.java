package cn.net.easyinfo.common.exception.auth;


import cn.net.easyinfo.common.constant.CommonConstants;
import cn.net.easyinfo.common.exception.BaseException;

public class UserTokenException extends BaseException {
    public UserTokenException(String message) {
        super(message, CommonConstants.EX_USER_INVALID_CODE);
    }
}
