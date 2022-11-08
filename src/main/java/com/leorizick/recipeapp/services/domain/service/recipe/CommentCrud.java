package com.leorizick.recipeapp.services.domain.service.recipe;

import com.leorizick.recipeapp.entities.recipe.Comment;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.repositories.CommentRepository;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.exceptions.AccountTypeNotAllowed;
import com.leorizick.recipeapp.services.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentCrud {

    private final CommentRepository commentRepository;
    private final AuthenticationContext authenticationContext;

    public Comment save(Comment comment){
        verifyAuthor(comment);
        return commentRepository.save(comment);
    }

    public Comment findById(Long id){
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found " + id ));
    }

    public void delete(Long id){
        Comment comment = findById(id);
        verifyAuthor(comment);
        comment.setEnabled(false);
        commentRepository.save(comment);
    }

    public Page<Comment> findAllPerRecipe(Pageable pageable, Long id){
        return commentRepository.findAllByRecipe(id, pageable);
    }

    private void verifyAuthor(Comment comment){
        if(!comment.getAuthor().getId().equals(authenticationContext.getAccountId())){
            throw new AccountTypeNotAllowed("author");
        }
    }

}
