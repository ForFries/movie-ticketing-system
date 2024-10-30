package com.forfries.mapper;

import com.forfries.entity.Movie;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Nolan
* @description 针对表【movie】的数据库操作Mapper
* @createDate 2024-10-30 08:02:52
* @Entity com.forfries.entity.Movie
*/
@Mapper
public interface MovieMapper extends BaseMapper<Movie> {

}




