package com.forfries.mapper;

import com.forfries.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Nolan
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2024-11-04 10:17:38
* @Entity com.forfries.entity.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




