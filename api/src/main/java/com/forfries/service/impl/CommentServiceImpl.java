package com.forfries.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forfries.common.Impl.PageableServiceImpl;
import com.forfries.entity.Comment;
import com.forfries.dto.CommentPageDTO;
import com.forfries.mapper.CommentMapper;
import com.forfries.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Nolan
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2024-11-04 10:17:38
*/
@Service
public class CommentServiceImpl extends PageableServiceImpl<CommentMapper, Comment, CommentPageDTO>
        implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    protected void buildQueryWrapper(QueryWrapper<Comment> queryWrapper, CommentPageDTO commentPageDTO) {
        if (commentPageDTO.getMovieId() != null) {
            queryWrapper.eq("movie_id", commentPageDTO.getMovieId());
        }
        // 可以添加更多的查询条件
        queryWrapper.orderByDesc("updated_at");
    }
}




