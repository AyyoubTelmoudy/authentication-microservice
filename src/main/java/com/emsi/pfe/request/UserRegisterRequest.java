package com.emsi.pfe.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserRegisterRequest {
    @NotBlank(message ="L'email ne doit pas être vide")
    @NotNull(message ="Ce champ est obligatoire")
    @Size(min = 2)
    private String email;
    @NotBlank(message ="Le mot de passe ne doit pas être vide")
    @NotNull(message ="Ce champ est obligatoire")
    @Size(min = 2)
    private String password;
}
