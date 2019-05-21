package cn.net.easyinfo.common.msg.auth;


import cn.net.easyinfo.common.constant.RestCodeConstants;
import cn.net.easyinfo.common.msg.BaseResponse;


public class TokenErrorResponse extends BaseResponse {
    public TokenErrorResponse(String message) {
        super(RestCodeConstants.TOKEN_ERROR_CODE, message);
    }
}
