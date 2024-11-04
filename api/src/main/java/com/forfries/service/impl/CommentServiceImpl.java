package com.forfries.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forfries.common.Impl.PageableServiceImpl;
import com.forfries.constant.MessageConstant;
import com.forfries.context.BaseContext;
import com.forfries.entity.Comment;
import com.forfries.dto.CommentPageDTO;
import com.forfries.exception.PasswordErrorException;
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
        if (commentPageDTO.getUserId() != null) {
            queryWrapper.eq("user_id", commentPageDTO.getMovieId());
        }
        // 可以添加更多的查询条件
        queryWrapper.orderByDesc("updated_at");
    }

    @Override
    public Comment getByIdWithCheck(Long id) {
        this.check(id);
        return this.getById(id);
    }

    @Override
    public boolean deleteByIdWithCheck(Long id) {
        this.check(id);
        return this.removeById(id);
    }

    @Override
    public boolean updateByIdWithCheck(Long id, Comment com) {

        this.check(id);
        return this.updateById(com);
    }

    public boolean check(Long id){
        Comment comment = this.getById(id);
        if (comment.getUserId() != Long.parseLong(BaseContext.getCurrentPayload().get("userId"))) {
            throw new PasswordErrorException(MessageConstant.PERMISSION_ERROR_USER_ID);
        }
        return true;
    }

}




