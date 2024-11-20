package com.forfries.service.common.impl;

import com.forfries.service.common.WechatService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    private WxMpService mpService;

    @Override
    public String getQrcodeUrl(String sceneId) throws WxErrorException {
        WxMpQrCodeTicket wxMpQrCodeTicket = this.mpService.getQrcodeService().qrCodeCreateTmpTicket(sceneId, 10 * 60);
        return  this.mpService.getQrcodeService().qrCodePictureUrl(wxMpQrCodeTicket.getTicket());
    }

    @Override
    public String getSceneId() {
        return UUID.randomUUID().toString();
    }
}
