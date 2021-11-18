package com.ds.concurrent.fetch.serviceImpl;

import com.ds.concurrent.fetch.callback.PostResCallback;
import com.ds.concurrent.fetch.dto.common.CommonResponse;
import com.ds.concurrent.fetch.service.TestService;
import com.ds.concurrent.fetch.task.FetchTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final RestTemplate restTemplate;

    @Override
    public CommonResponse fetch() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Object>> todo = new ArrayList<>();
        AtomicInteger totalReq = new AtomicInteger(0);
        AtomicInteger totalRes = new AtomicInteger(0);
        PostResCallback postResCallback = (index, postResponse) -> {
            totalRes.getAndIncrement();
            if (totalReq.get() == totalRes.get()) {
                log.info("Total Complete: {}", totalRes.get());
            }
        };
        for (int i = 0; i < 10; i++) {
            FetchTask fetchTask = new FetchTask(i, restTemplate, postResCallback);
            todo.add(Executors.callable(fetchTask));
            totalReq.incrementAndGet();
        }
        List<Future<Object>> answers = executorService.invokeAll(todo);
        executorService.shutdown();
        todo.clear();
        answers.clear();
        return new CommonResponse("Fetch Start Successfully!");
    }
}
