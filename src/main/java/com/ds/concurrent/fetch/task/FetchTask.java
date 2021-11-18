package com.ds.concurrent.fetch.task;

import com.ds.concurrent.fetch.dto.post.PostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
public class FetchTask implements Runnable {

    private int index;
    private RestTemplate restTemplate;

    public FetchTask(int index, RestTemplate restTemplate) {
        this.index = index;
        this.restTemplate = restTemplate;
    }

    @Override
    public void run() {
        ResponseEntity<PostResponse> postResponseResponseEntity = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts/" + index, PostResponse.class);
        if (Objects.nonNull(postResponseResponseEntity)) {
            PostResponse postResponse = postResponseResponseEntity.getBody();
            if (Objects.nonNull(postResponse)) {
                log.info("ID: {}", postResponse.getId());
            }
        }
    }
}
