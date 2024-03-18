package com.example.demo.payloads.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PostRequest {
    @NotBlank
    @Size(min = 50, max = 5000)
    @Email
    private String text;

    @NotBlank
    @Size(min = 3, max = 20)
    private String title;

    @NotBlank
    @Size(min = 3, max = 20)
    private ArrayList<Long> topic;
}
