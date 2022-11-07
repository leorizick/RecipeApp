package com.leorizick.recipeapp.web.recipe;

import com.leorizick.recipeapp.dto.recipe.CommentCreationRequest;
import com.leorizick.recipeapp.dto.recipe.CommentCrudResponse;
import com.leorizick.recipeapp.dto.recipe.RecipeCrudResponse;
import com.leorizick.recipeapp.entities.recipe.Comment;
import com.leorizick.recipeapp.services.api.service.recipe.CommentApiService;
import com.leorizick.recipeapp.services.domain.service.recipe.CommentCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentApiService commentApiService;

    @GetMapping(value = "/api/recipe/comment/{id}")
    public ResponseEntity<CommentCrudResponse> findById(@PathVariable Long id){
        CommentCrudResponse commentCrudResponse = commentApiService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentCrudResponse);
    }

    @GetMapping(value = "/api/recipe/comment")
    public ResponseEntity<Page<CommentCrudResponse>> findAll(Pageable pageable) {
        Page<CommentCrudResponse> commentPages = commentApiService.findAll(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentPages);
    }

    @GetMapping(value = "/api/recipe/{id}/comment")
    public ResponseEntity<Page<CommentCrudResponse>> findAll(Pageable pageable, @PathVariable Long id) {
        Page<CommentCrudResponse> commentPages = commentApiService.findAllPerRecipe(pageable, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentPages);
    }

    @PostMapping(value = "/api/recipe/{id}/comment")
    public ResponseEntity<CommentCrudResponse> create(@RequestBody CommentCreationRequest commentCreationRequest, @PathVariable Long id){
        CommentCrudResponse commentCrudResponse = commentApiService.create(commentCreationRequest, id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentCrudResponse);
    }

    @PutMapping(value = "/api/recipe/comment/{id}")
    public ResponseEntity<CommentCrudResponse> update(@RequestBody CommentCreationRequest commentCreationRequest, @PathVariable Long id){
        CommentCrudResponse commentCrudResponse = commentApiService.update(commentCreationRequest, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping(value = "/api/recipe/comment/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        commentApiService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
