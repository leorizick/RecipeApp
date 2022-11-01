package com.leorizick.recipeapp.dto.account;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCreationResponse {

    private Long id;
    private String name;
    private String username;
    private LocalDate birth;
    private String email;
    private Set<String> profiles = new HashSet<>();
    private boolean enabled;

}
