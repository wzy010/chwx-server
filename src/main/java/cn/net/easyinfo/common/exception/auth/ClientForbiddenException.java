package cn.net.easyinfo.common.exception.auth;


import cn.net.easyinfo.common.constant.CommonConstants;
import cn.net.easyinfo.common.exception.BaseException;

public class ClientForbiddenException extends BaseException {
    public ClientForbiddenException(String message) {
        super(message, CommonConstants.EX_CLIENT_FORBIDDEN_CODE);
    }

}
