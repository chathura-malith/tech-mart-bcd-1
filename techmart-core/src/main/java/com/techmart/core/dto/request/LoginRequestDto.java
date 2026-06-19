package com.techmart.core.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Email is required to login")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required to login")
    private String password;
}