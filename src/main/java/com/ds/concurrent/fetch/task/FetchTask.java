package com.ds.concurrent.fetch.task;

import com.ds.concurrent.fetch.callback.PostResCallback;
import com.ds.concurrent.fetch.dto.post.PostResponse;
import com.ds.concurrent.fetch.staticVariable.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
public class FetchTask implements Runnable {

    private int index;
    private RestTemplate restTemplate;
    private PostResCallback postResCallback;

    public FetchTask(int index, RestTemplate restTemplate, PostResCallback postResCallback) {
        this.index = index;
        this.restTemplate = restTemplate;
        this.postResCallback = postResCallback;
    }

    @Override
    public void run() {
        ResponseEntity<PostResponse> postResponseResponseEntity = restTemplate.getForEntity(Api.POST_GET + (index + 1), PostResponse.class);
        synchronized (this) {
            if (Objects.nonNull(postResponseResponseEntity)) {
                PostResponse postResponse = postResponseResponseEntity.getBody();
                if (Objects.nonNull(postResponse)) {
                    postResCallback.response(index, postResponse);
                }
            }
        }
    }
}
