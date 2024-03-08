package com.leorizick.recipeapp.services.domain.service.mapping.Comment;

import com.leorizick.recipeapp.dto.account.AccountSummaryResponse;
import com.leorizick.recipeapp.dto.recipe.CommentCrudResponse;
import com.leorizick.recipeapp.entities.recipe.Comment;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.domain.service.rating.RatingManagement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class CommentCrudResponseMapper {

    private final ModelMapper modelMapper;
    private final AuthenticationContext authenticationContext;
    private final RatingManagement ratingManagement;

    @PostConstruct
    public void configure() {
        createFromComment();
    }

    private void createFromComment() {
        modelMapper.createTypeMap(Comment.class, CommentCrudResponse.class)
                .setConverter(mappingContext -> {
                    var src = mappingContext.getSource();
                    var author = modelMapper.map(src.getAuthor(), AccountSummaryResponse.class);
                    var rating = ratingManagement.getCommentAuthorRating(src.getRecipe().getId(), src.getAuthor().getId());

                    return CommentCrudResponse.builder()
                            .id(src.getId())
                            .date(src.getCreatedAt())
                            .author(author)
                            .body(src.getBody())
                            .recipe(src.getRecipe().getId())
                            .authorRating(rating)
                            .enabled(src.isEnabled())
                            .build();
                });
    }

}
