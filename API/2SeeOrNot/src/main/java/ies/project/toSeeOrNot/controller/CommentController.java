package ies.project.toSeeOrNot.controller;

import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.common.enums.HttpStatusCode;
import ies.project.toSeeOrNot.dto.CommentDTO;
import ies.project.toSeeOrNot.entity.Comment;
import ies.project.toSeeOrNot.service.CommentService;
import ies.project.toSeeOrNot.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
                                @RequestParam(value = "parent", defaultValue = "0") int parentId,
                                @RequestParam(value = "cinema", defaultValue = "0") int cinemaId,
                                @RequestParam(value = "film", defaultValue = "0") String filmId,
                                @RequestParam(value = "premier", defaultValue = "0") int premierId,
                                @RequestParam(value = "reply", defaultValue = "0") int replyTo,
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

    @PutMapping("/user/comment/{commentId}/like")
    public Result like(@PathVariable("commentId") int commentId, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        int currentUser = JWTUtils.getUserId(token);
        commentService.like(currentUser, commentId);
        return Result.sucess("");
    }

    @GetMapping("/common/comment/{parentId}/second/level")
    public Result getSecondLevelComments(@PathVariable("parentId") int parentId, @RequestParam(value = "page", defaultValue = "1") int page){
        return Result.sucess(commentService.getCommentsByParentId(parentId, page - 1));
    }

    @GetMapping("/common/film/{filmId}/commentPage{page}")
    public Result getCommentsByFilm(@PathVariable("filmId") String filmId, @PathVariable(value = "page") int page){
        return Result.sucess(commentService.getCommentsByFilm(filmId, page - 1));
    }

    @GetMapping("/common/cinema/{cinemaId}/commentPage{page}")
    public Result getCommentsByCinema(@PathVariable("cinemaId") int cinemaId, @PathVariable(value = "page") int page){
        return Result.sucess(commentService.getCommentsByCinema(cinemaId, page - 1));
    }

    @GetMapping("/common/premier/{premierId}/commentPage{page}")
    public Result getCommentsByPremier(@PathVariable("premier") int premier, @PathVariable(value = "page") int page){
        return Result.sucess(commentService.getCommentsByPremier(premier, page - 1));
    }

    @DeleteMapping("/user/comment/{commentId}/remove/")
    public Result removeComment(@RequestParam("commentId") int commentId, HttpServletRequest request){
        String token = request.getHeader(JWTUtils.getHeader());
        int currentUser = JWTUtils.getUserId(token);
        commentService.removeComment(currentUser, commentId);
        return Result.sucess("Comment removed!");
    }
}
