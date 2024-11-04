package com.forfries.service;

import com.forfries.common.PageableService;
import com.forfries.dto.CommentPageDTO;
import com.forfries.entity.Comment;
import com.forfries.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Nolan
* @description 针对表【comment】的数据库操作Service
* @createDate 2024-11-04 10:17:38
*/
public interface CommentService extends PageableService<Comment, CommentPageDTO> {

}