package com.ds.concurrent.fetch.callback;

import com.ds.concurrent.fetch.dto.post.PostResponse;
import com.ds.concurrent.fetch.dto.sc.ResponseResult;

public interface PostResCallback {
    void response(int index, PostResponse postResponse);

    void responseResult(int index, ResponseResult responseResult);
}
