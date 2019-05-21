package cn.net.easyinfo.common.msg.auth;


import cn.net.easyinfo.common.constant.RestCodeConstants;
import cn.net.easyinfo.common.msg.BaseResponse;

public class TokenForbiddenResponse  extends BaseResponse {
    public TokenForbiddenResponse(String message) {
        super(RestCodeConstants.TOKEN_FORBIDDEN_CODE, message);
    }
}
