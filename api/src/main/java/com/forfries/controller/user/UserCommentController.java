package com.forfries.controller.user;



import com.forfries.constant.MessageConstant;
import com.forfries.constant.StatusConstant;
import com.forfries.context.BaseContext;
import com.forfries.dto.CommentPageDTO;
import com.forfries.entity.Comment;
import com.forfries.exception.InconsistentIDException;
import com.forfries.result.PageResult;
import com.forfries.result.Result;
import com.forfries.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user/comments")
@Slf4j
public class UserCommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public Result<PageResult> pageComments(CommentPageDTO commentPageDTO) {

        PageResult pageResult = commentService.page(commentPageDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<Comment> getCommentById(@PathVariable long id) {
        return Result.success(commentService.getByIdWithCheck(id));
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteCommentById(@PathVariable long id) {
        commentService.deleteByIdWithCheck(id);
        return Result.success();
    }

    @PostMapping
    public Result<?> addComment(@RequestBody Comment comment) {
        //TODO 这里添加检测
        comment.setStatus(StatusConstant.NORMAL);
        comment.setUserId(Long.parseLong(BaseContext.getCurrentPayload().get("userId")));
        commentService.save(comment);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<?> updateComment(@PathVariable Long id,
                                   @RequestBody Comment comment) {
        if(!comment.getId().equals(id))
            throw new InconsistentIDException(MessageConstant.INCONSISTENT_ID);

        commentService.updateByIdWithCheck(id,comment);
        return Result.success();
    }

}
