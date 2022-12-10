package com.example.posts.Service.Impl;

import com.example.posts.DTO.CommentDto;
import com.example.posts.DTO.GetCommentDto;
import com.example.posts.DTO.PredicateDto;
import com.example.posts.Entity.Comment;
import com.example.posts.Entity.Comment_;
import com.example.posts.Mappers.CommentMappers;
import com.example.posts.Repositories.CommentRepositories;
import com.example.posts.Service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepositories commentRepositories;

    private final CommentMappers commentMappers;
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public CommentServiceImpl(CommentRepositories commentRepositories, CommentMappers commentMappers) {
        this.commentRepositories = commentRepositories;
        this.commentMappers = commentMappers;
    }

    @Override
    public List<CommentDto> getComment(GetCommentDto getCommentDto) {
        log.info("Get comment with {}", getCommentDto);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CommentDto> cq = cb.createQuery(CommentDto.class);

        Root<Comment> root = cq.from(Comment.class);

        cq.where(cb.equal(root.get(Comment_.POST_ID), getCommentDto.getPostId()));

        cq.multiselect(
                root.get(Comment_.ID),
                root.get(Comment_.POST_ID),
                root.get(Comment_.USER_ID),
                root.get(Comment_.TEXT),
                root.get(Comment_.LIKES),
                root.get(Comment_.DISLIKES)
        );
        return entityManager.createQuery(cq).setFirstResult(getCommentDto.getFrom().intValue()).setMaxResults(getCommentDto.getTo().intValue()).getResultList();
    }

    @Override
    public CommentDto patchComment(CommentDto commentDto) {
        log.info("Update comment {}", commentDto);
        CommentDto findCommentDto = commentMappers.commentToCommentDto(commentRepositories.findCommentById(commentDto.getId()).orElseThrow(() -> new RuntimeException("Comment not found")));

        if(commentDto.getDislikes() != null){
            findCommentDto.setDislikes(commentDto.getDislikes());
        }
        if(commentDto.getLikes() != null){
            findCommentDto.setLikes(commentDto.getLikes());
        }
        if(commentDto.getText() != null){
            findCommentDto.setText(commentDto.getText());
        }
        return updateComment(findCommentDto);
    }

    @Override
    public List<CommentDto> getCommentByPredicate(Long postId,List<PredicateDto> map) {
        log.info("Get comment by predicate with {}", map);
        List<Order> orders = new ArrayList<>();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CommentDto> cq = cb.createQuery(CommentDto.class);

        Root<Comment> root = cq.from(Comment.class);

        cq.where(cb.and(cb.equal(root.get(Comment_.POST_ID), postId)));
        map.forEach(n -> orders.add(n.getValue() ? cb.asc(root.get(n.getKey())) : cb.desc(root.get(n.getKey()))));
        cq.orderBy(orders);
        cq.multiselect(
                root.get(Comment_.ID),
                root.get(Comment_.POST_ID),
                root.get(Comment_.USER_ID),
                root.get(Comment_.TEXT),
                root.get(Comment_.LIKES),
                root.get(Comment_.DISLIKES)
        );
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public String saveComment(CommentDto commentDto) {
        log.info("Save comment {}", commentDto);
        commentRepositories.save(commentMappers.commentDtoTpComment(commentDto));
        return "Suggest";
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto) {
        log.info("Update comment {}", commentDto);
        return commentMappers.commentToCommentDto(commentRepositories.save(commentMappers.commentDtoTpComment(commentDto)));
    }

    @Override
    public void deleteComment(Long id) {
        log.info("Delete comment with id {}", id);
        commentRepositories.deleteById(id);
    }
}
