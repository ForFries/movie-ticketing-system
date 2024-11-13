package com.forfries.config;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CustomIdGenerator implements IdentifierGenerator {
    private static final Random random = new Random();

    @Override
    public Long nextId(Object entity) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        // 生成一个4位随机数
        int randomNum = 1000 + random.nextInt(9000);
        String last10Chars = timestamp.substring(timestamp.length() - 10);
        return Long.parseLong(last10Chars + randomNum);
    }
}