package com.leorizick.recipeapp.services.api.service.recipe;

import com.leorizick.recipeapp.dto.recipe.CommentCreationRequest;
import com.leorizick.recipeapp.dto.recipe.CommentCrudResponse;
import com.leorizick.recipeapp.entities.recipe.Comment;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.services.domain.service.recipe.CommentCrud;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeCrud;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentApiService {

    private final CommentCrud commentCrud;
    private final RecipeCrud recipeCrud;
    private final ModelMapper modelMapper;

    public CommentCrudResponse findById(Long id) {
        Comment comment = commentCrud.findById(id);
        return modelMapper.map(comment, CommentCrudResponse.class);
    }

    public Page<CommentCrudResponse> findAllPerRecipe(Pageable pageable, Long id) {
        Page<Comment> commentPage = commentCrud.findAllPerRecipe(pageable, id);
        return commentPage.map(comment -> modelMapper.map(comment, CommentCrudResponse.class));
    }

    @Transactional
    public CommentCrudResponse create(CommentCreationRequest commentCreationRequest, Long id) {
        Recipe recipe = recipeCrud.findById(id);
        Comment comment = modelMapper.map(commentCreationRequest, Comment.class);
        comment.setRecipe(recipe);
        comment = commentCrud.save(comment);
        return modelMapper.map(comment, CommentCrudResponse.class);
    }

    @Transactional
    public CommentCrudResponse update(CommentCreationRequest commentCreationRequest, Long id) {
        Comment comment = commentCrud.findById(id);
        modelMapper.map(commentCreationRequest, comment);
        comment = commentCrud.save(comment);
        return modelMapper.map(comment, CommentCrudResponse.class);
    }

    @Transactional
    public void delete(Long id) {
        commentCrud.delete(id);
    }

}
