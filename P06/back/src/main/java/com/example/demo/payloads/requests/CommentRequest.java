package com.example.demo.payloads.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CommentRequest {

    @NotBlank
    @Size(min = 3, max = 50)
    private String content;

    private Integer postId;

}
