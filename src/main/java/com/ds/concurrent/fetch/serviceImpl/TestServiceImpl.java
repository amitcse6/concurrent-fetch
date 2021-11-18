package com.ds.concurrent.fetch.serviceImpl;

import com.ds.concurrent.fetch.callback.PostResCallback;
import com.ds.concurrent.fetch.dto.common.CommonResponse;
import com.ds.concurrent.fetch.dto.post.PostResponse;
import com.ds.concurrent.fetch.dto.sc.ResponseResult;
import com.ds.concurrent.fetch.service.TestService;
import com.ds.concurrent.fetch.staticVariable.AppMessage;
import com.ds.concurrent.fetch.task.FetchTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final RestTemplate restTemplate;
    private final Environment environment;

    @Value("${concurrent.totalProcess}")
    private int totalProcess;
    @Value("${concurrent.totalStep}")
    private int totalStep;

    @Override
    public CommonResponse fetch() throws InterruptedException {
        for (int p = 0; p < totalProcess; p++) {
            ExecutorService executorService = Executors.newFixedThreadPool(totalStep);
            List<Callable<Object>> todo = new ArrayList<>();
            AtomicInteger totalReq = new AtomicInteger(0);
            AtomicInteger totalRes = new AtomicInteger(0);
            PostResCallback postResCallback = new PostResCallback() {
                @Override
                public void response(int index, PostResponse postResponse) {
                    totalRes.getAndIncrement();
                    if (totalRes.get() == totalStep) {
                        log.info("Total Step Complete: {}", totalRes.get());
                    }
                }

                @Override
                public void responseResult(int index, ResponseResult responseResult) {
                    totalRes.getAndIncrement();
                    if (totalRes.get() == totalStep) {
                        log.info("Total Step Complete: {}", totalRes.get());
                    }
                }
            };
            for (int i = 0; i < totalStep; i++) {
                FetchTask fetchTask = new FetchTask(i, restTemplate, environment, requestEntity(), postResCallback);
                todo.add(Executors.callable(fetchTask));
                totalReq.incrementAndGet();
            }
            List<Future<Object>> answers = executorService.invokeAll(todo);
            executorService.shutdown();
            todo.clear();
            answers.clear();
            log.info("{}/{} Complete.", (p + 1), totalProcess);
        }
        log.info(AppMessage.FETCH_COMPLETE);
        return new CommonResponse(AppMessage.FETCH_START);
    }

    private HttpEntity<String> requestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        Map<String, Object> bodyParamMap = addToMapPeriodicScreening();
        String reqBodyData = null;
        try {
            reqBodyData = new ObjectMapper().writeValueAsString(bodyParamMap);
        } catch (JsonProcessingException e) {
            log.info("error: {}", e.getLocalizedMessage());
        }
        HttpEntity<String> requestEntity = new HttpEntity<>(reqBodyData, headers);
        return requestEntity;
    }

    private Map<String, Object> addToMapPeriodicScreening() {
        Map<String, Object> bodyParamMap = new HashMap<>();
        bodyParamMap.put("Name", "MD FARUK ISLAM");
        bodyParamMap.put("Msisdn", "1918938042");
        bodyParamMap.put("DateOfBirth", "1976-09-09");
        bodyParamMap.put("IdNumber", "2692619463734");
        bodyParamMap.put("Country", "BANGLADESH");
        bodyParamMap.put("Gender", "Male");
        bodyParamMap.put("MothersName", "HALIMA BEGUM");
        bodyParamMap.put("FathersName", "ABDUR RAHMAN");
        bodyParamMap.put("Threshold", 0.11);
        return bodyParamMap;
    }
}
