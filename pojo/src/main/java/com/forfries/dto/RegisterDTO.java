package com.forfries.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
@Data
public class RegisterDTO {
    String username;
    String password;
}
