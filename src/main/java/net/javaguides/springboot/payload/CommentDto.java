package net.javaguides.springboot.payload;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private long id;
    private String email;
    private String name;
    private String body;
}
