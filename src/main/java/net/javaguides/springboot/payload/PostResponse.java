package net.javaguides.springboot.payload;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PostResponse {
    private List<PostDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
