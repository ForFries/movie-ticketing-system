package com.forfries.service.common;

import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Service;

@Service
public interface WechatService {
    String getQrcodeUrl(String sceneId) throws WxErrorException;

    String getSceneId();
}
