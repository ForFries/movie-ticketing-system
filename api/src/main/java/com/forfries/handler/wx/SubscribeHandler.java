package com.forfries.handler.wx;

//import com.github.binarywang.demo.wx.mp.builder.TextBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.forfries.constant.MessageConstant;
import com.forfries.exception.SystemException;
import com.forfries.service.common.UserService;
import com.forfries.service.webscket.UserWebSocketService;
import com.forfries.utils.TextBuilder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.Constants;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Component
@Slf4j
public class SubscribeHandler extends AbstractHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private UserWebSocketService userWebSocketService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        log.info("新关注用户 OPENID: {}" , wxMpXmlMessage.getFromUser());

        log.info("EventKey:{}", wxMpXmlMessage.getEventKey());

        //获得场景id
        String prefix = "qrscene_";
        String sceneId = wxMpXmlMessage.getEventKey().substring(prefix.length());

        if(sceneId.isEmpty() || sceneId.equals("null")){
            return new TextBuilder().build("感谢关注！", wxMpXmlMessage, wxMpService);
        }

        String token = userService.createTokenWithRegister(wxMpXmlMessage.getFromUser());

        try {
            userWebSocketService.sendJwtToUser(sceneId,token);
        } catch (Exception e) {
            throw new SystemException(MessageConstant.SYSTEM_ERROR);
        }

        return new TextBuilder().build("感谢关注，成功登录！", wxMpXmlMessage, wxMpService);

//        // 获取微信用户基本信息
//        try {
//            WxMpUser userWxInfo = weixinService.getUserService()
//                .userInfo(wxMessage.getFromUser(), null);
//            if (userWxInfo != null) {
//                // TODO 可以添加关注用户到本地数据库
//            }
//        } catch (WxErrorException e) {
//            if (e.getError().getErrorCode() == 48001) {
//                this.logger.info("该公众号没有获取用户信息权限！");
//            }
//        }


//        WxMpXmlOutMessage responseResult = null;
//        try {
//            responseResult = this.handleSpecial(wxMessage);
//        } catch (Exception e) {
//            this.logger.error(e.getMessage(), e);
//        }
//
//        if (responseResult != null) {
//            return responseResult;
//        }
//
//        try {
//            return new TextBuilder().build("感谢关注", wxMessage, weixinService);
//        } catch (Exception e) {
//            this.logger.error(e.getMessage(), e);
//        }
//
//        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
        throws Exception {
        //TODO
        return null;
    }

}
