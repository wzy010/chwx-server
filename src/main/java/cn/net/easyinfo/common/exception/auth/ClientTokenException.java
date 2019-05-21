package cn.net.easyinfo.common.exception.auth;


import cn.net.easyinfo.common.constant.CommonConstants;
import cn.net.easyinfo.common.exception.BaseException;

public class ClientTokenException extends BaseException {
    public ClientTokenException(String message) {
        super(message, CommonConstants.EX_CLIENT_INVALID_CODE);
    }
}
