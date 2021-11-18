package com.ds.concurrent.fetch.task;

import com.ds.concurrent.fetch.callback.PostResCallback;
import com.ds.concurrent.fetch.dto.post.PostResponse;
import com.ds.concurrent.fetch.dto.sc.ResponseResult;
import com.ds.concurrent.fetch.staticVariable.Api;
import com.ds.concurrent.fetch.staticVariable.Env;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class FetchTask implements Runnable {

    private int index;
    private RestTemplate restTemplate;
    private Environment environment;
    private HttpEntity<String> requestEntity;
    private PostResCallback postResCallback;

    public FetchTask(int index, RestTemplate restTemplate, Environment environment, HttpEntity<String> requestEntity, PostResCallback postResCallback) {
        this.index = index;
        this.restTemplate = restTemplate;
        this.environment = environment;
        this.requestEntity = requestEntity;
        this.postResCallback = postResCallback;
    }

    @Override
    public void run() {
        if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> (env.equalsIgnoreCase(Env.PROD) || env.equalsIgnoreCase(Env.UAT)))) {

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(Api.SC, requestEntity, String.class);
            synchronized (this) {
                if (Objects.nonNull(responseEntity)) {
                    String postResponse = responseEntity.getBody();
                    if (Objects.nonNull(postResponse)) {
                        ResponseResult responseResult = null;
                        try {
                            responseResult = new ObjectMapper().readValue(Objects.requireNonNull(responseEntity.getBody()), ResponseResult.class);
                            postResCallback.responseResult(index, responseResult);
                        } catch (JsonProcessingException e) {
                            log.info("error: {}", e.getLocalizedMessage());
                        }
                    }
                }
            }
        } else {
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
}
