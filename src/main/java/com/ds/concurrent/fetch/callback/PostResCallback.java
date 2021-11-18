package com.ds.concurrent.fetch.callback;

import com.ds.concurrent.fetch.dto.post.PostResponse;

public interface PostResCallback {
    void response(int index, PostResponse postResponse);
}
