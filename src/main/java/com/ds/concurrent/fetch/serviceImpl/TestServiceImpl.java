package com.ds.concurrent.fetch.serviceImpl;

import com.ds.concurrent.fetch.callback.PostResCallback;
import com.ds.concurrent.fetch.dto.common.CommonResponse;
import com.ds.concurrent.fetch.service.TestService;
import com.ds.concurrent.fetch.staticVariable.AppMessage;
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
        int totalProcess = 3;
        int totalStep = 100;
        for (int p = 0; p < totalProcess; p++) {
            ExecutorService executorService = Executors.newFixedThreadPool(totalStep);
            List<Callable<Object>> todo = new ArrayList<>();
            AtomicInteger totalReq = new AtomicInteger(0);
            AtomicInteger totalRes = new AtomicInteger(0);
            PostResCallback postResCallback = (index, postResponse) -> {
                totalRes.getAndIncrement();
                if (totalRes.get() == totalStep) {
                    log.info("Total Step Complete: {}", totalRes.get());
                }
            };
            for (int i = 0; i < totalStep; i++) {
                FetchTask fetchTask = new FetchTask(i, restTemplate, postResCallback);
                todo.add(Executors.callable(fetchTask));
                totalReq.incrementAndGet();
            }
            List<Future<Object>> answers = executorService.invokeAll(todo);
            executorService.shutdown();
            todo.clear();
            answers.clear();
            log.info("{}/{} Complete.", (p + 1), totalProcess);
        }
        return new CommonResponse(AppMessage.FETCH_START);
    }
}
