package com.forfries.handler.wx;

import com.forfries.constant.MessageConstant;
import com.forfries.exception.SystemException;
import com.forfries.service.common.UserService;
import com.forfries.service.webscket.UserWebSocketService;
import com.forfries.utils.TextBuilder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Component
@Slf4j
public class ScanHandler extends AbstractHandler {
    @Autowired
    private UserService userService;

    @Autowired
    private UserWebSocketService userWebSocketService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        // 扫码事件处理
        log.info("收到扫码，OPENID:{}", wxMpXmlMessage.getFromUser());

        String sceneId = wxMpXmlMessage.getEventKey();

        String token = userService.createTokenWithRegister(wxMpXmlMessage.getFromUser());

        try {
            userWebSocketService.sendJwtToUser(sceneId,token);
        } catch (Exception e) {
            throw new SystemException(MessageConstant.SYSTEM_ERROR);
        }

        return new TextBuilder().build("成功登录！", wxMpXmlMessage, wxMpService);
    }
}
