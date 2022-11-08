package com.leorizick.recipeapp.services.domain.service.mapping.Comment;

import com.leorizick.recipeapp.dto.recipe.CommentCreationRequest;
import com.leorizick.recipeapp.entities.recipe.Comment;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.domain.service.recipe.CommentCrud;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class CommentMapper {
    private final ModelMapper modelMapper;
    private final CommentCrud commentCrud;
    private final AuthenticationContext authenticationContext;

    @PostConstruct
    public void configure() {
        createFromCommentCreationRequest();
    }

    private void createFromCommentCreationRequest() {
        modelMapper.createTypeMap(CommentCreationRequest.class, Comment.class)
                .setConverter(mappingContext -> {
                    var src = mappingContext.getSource();
                    var account = authenticationContext.getAccount();
                    var comment = mappingContext.getDestination();

                    if(comment == null){
                        comment = new Comment();
                        comment.setAuthor(account);
                    }

                    comment.setBody(src.getBody());
                    comment.setEnabled(true);

                    return comment;
                });
    }

}
