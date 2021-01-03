package ies.project.toSeeOrNot.controller;

import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.common.enums.HttpStatusCode;
import ies.project.toSeeOrNot.entity.Comment;
import ies.project.toSeeOrNot.service.CommentService;
import ies.project.toSeeOrNot.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author Wei
 * @date 2020/12/30 22:40
 */
@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/user/comment/create")
    public Result createComment(@RequestParam(value = "msg", defaultValue = "") String msg,
                                @RequestParam(value = "parent", defaultValue = "0") Integer parentId,
                                @RequestParam(value = "cinema", defaultValue = "0") Integer cinemaId,
                                @RequestParam(value = "film", defaultValue = "0") String filmId,
                                @RequestParam(value = "premier", defaultValue = "0") Integer premierId,
                                @RequestParam(value = "reply", defaultValue = "0") Integer replyTo,
                                HttpServletRequest request){

        String token = request.getHeader(JWTUtils.getHeader());
        int id = JWTUtils.getUserId(token);
        Comment comment = new Comment();
        comment.setAuthor(id);
        comment.setCinema(cinemaId);
        comment.setContent(msg);
        comment.setFilm(filmId);
        comment.setParentId(parentId);
        comment.setPremier(premierId);
        comment.setReplyto(replyTo);
        comment.setCreated(new Date());
        comment.setLikes(0);
        Comment saved = commentService.createComment(comment);

        return saved == null ?
                Result.failure(HttpStatusCode.BAD_REQUEST,"Can not created a comment")
                :
                Result.sucess("Comment created!") ;
    }

    @PutMapping("/user/comment/like")
    public Result like(@RequestParam("commentId") int commentId, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        int currentUser = JWTUtils.getUserId(token);
        commentService.like(currentUser, commentId);
        return Result.sucess("");
    }

    @GetMapping("/common/film/{filmId}/commentPage{page}")
    public Result getCommentsByFilm(@PathVariable("filmId") String filmId, @RequestParam(value = "page", defaultValue = "1") int page){
        return Result.sucess(commentService.getCommentsByFilm(filmId, page - 1));
    }

    @DeleteMapping("/user/remove/comment/{commentId}")
    public Result removeComment(@RequestParam("commentId") int commentId, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        int currentUser = JWTUtils.getUserId(token);
        commentService.removeComment(currentUser, commentId);
        return Result.sucess("Comment removed!");
    }
}
