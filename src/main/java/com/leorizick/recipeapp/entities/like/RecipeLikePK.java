package com.leorizick.recipeapp.entities.like;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RecipeLikePK  implements Serializable {

    private Long recipeId;
    private Long accountId;

}
