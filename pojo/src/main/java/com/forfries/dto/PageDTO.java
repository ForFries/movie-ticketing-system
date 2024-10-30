package com.forfries.dto;



import lombok.Data;

import java.io.Serializable;

@Data
public class PageDTO implements Serializable {
    private String page;
    private String pageSize;
    private String status;
}
