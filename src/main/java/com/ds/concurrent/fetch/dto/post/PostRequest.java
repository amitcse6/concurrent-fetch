package com.ds.concurrent.fetch.dto.post;

import lombok.Data;

@Data
public class PostRequest {
    private String userId;
    private String id;
    private String title;
    private String body;
}
