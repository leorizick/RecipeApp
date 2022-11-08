package com.leorizick.recipeapp.services.domain.service.mapping.Comment;

import com.leorizick.recipeapp.dto.account.AccountSummaryResponse;
import com.leorizick.recipeapp.dto.recipe.CommentSummaryResponse;
import com.leorizick.recipeapp.entities.recipe.Comment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class CommentSummaryResponseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    private void configure() {
        createCommentSummaryResponse();
    }

    private void createCommentSummaryResponse() {
        modelMapper.createTypeMap(Comment.class, CommentSummaryResponse.class).setConverter(mappingContext -> {
            var src = mappingContext.getSource();
            var author = modelMapper.map(src.getAuthor(), AccountSummaryResponse.class);

            return CommentSummaryResponse.builder()
                    .id(src.getId())
                    .author(author)
                    .body(src.getBody())
                    .build();
        });
    }
}
