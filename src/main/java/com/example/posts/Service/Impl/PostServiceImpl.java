package com.example.posts.Service.Impl;

import com.example.posts.DTO.MinAboutPost;
import com.example.posts.DTO.PostDto;
import com.example.posts.DTO.PredicateDto;
import com.example.posts.Entity.Post;
import com.example.posts.Entity.Post_;
import com.example.posts.Mappers.PostMapper;
import com.example.posts.Repositories.PostRepositories;
import com.example.posts.Service.PostService;
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
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepositories postRepositories;

    private final PostMapper postMapper;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public PostServiceImpl(PostRepositories postRepositories, PostMapper postMapper) {
        this.postRepositories = postRepositories;
        this.postMapper = postMapper;
    }

    @Override
    public PostDto getPostById(Long id) {
        log.info("Get post with id {}", id);
        return postMapper.postToPostDto(postRepositories.findPostById(id).orElseThrow(() -> new RuntimeException("Post don't found")));
    }

    @Override
    public String savePost(PostDto postDto) {
        log.info("Save post {}", postDto);
        postRepositories.save(postMapper.postDtoToPost(postDto));
        return "Suggest";
    }

    @Override
    public PostDto updatePost(PostDto postDto) {
        log.info("Update post {}", postDto);
        getPostById(postDto.getId());
        return postMapper.postToPostDto(postRepositories.save(postMapper.postDtoToPost(postDto)));
    }

    @Override
    public PostDto patchPost(PostDto postDto) {
        log.info("Update post {}", postDto);
        PostDto findPostDto = getPostById(postDto.getId());

        if(postDto.getDislikes() != null){
            findPostDto.setDislikes(postDto.getDislikes());
        }
        if(postDto.getLikes() != null){
            findPostDto.setLikes(postDto.getLikes());
        }
        if(postDto.getName() != null){
            findPostDto.setName(postDto.getName());
        }
        if(postDto.getText() != null){
            findPostDto.setText(postDto.getText());
        }
        return updatePost(findPostDto);
    }

    @Override
    public void deletePost(Long id) {
        log.info("Delete post with id {}", id);
        postRepositories.deleteById(id);
    }

    @Override
    public List<MinAboutPost> getAllPost() {
        log.info("Get all post");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MinAboutPost> cq = cb.createQuery(MinAboutPost.class);

        Root<Post> root = cq.from(Post.class);

        cq.multiselect(
                root.get(Post_.ID),
                root.get(Post_.NAME),
                root.get(Post_.USER_ID),
                root.get(Post_.LIKES),
                root.get(Post_.DISLIKES)
        );
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<MinAboutPost> getPostByPredicate(List<PredicateDto> predicateDtos) {
        log.info("Get post by predicates {}", predicateDtos);
        List<Order> orders = new ArrayList<>();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MinAboutPost> cq = cb.createQuery(MinAboutPost.class);

        Root<Post> root = cq.from(Post.class);

        predicateDtos.forEach(n -> orders.add(n.getValue() ? cb.asc(root.get(n.getKey())) : cb.desc(root.get(n.getKey()))));
        cq.orderBy(orders);
        cq.multiselect(
                root.get(Post_.ID),
                root.get(Post_.NAME),
                root.get(Post_.USER_ID),
                root.get(Post_.LIKES),
                root.get(Post_.DISLIKES)
        );
        return entityManager.createQuery(cq).getResultList();
    }
}
