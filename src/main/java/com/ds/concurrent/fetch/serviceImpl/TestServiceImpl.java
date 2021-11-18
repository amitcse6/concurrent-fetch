package com.ds.concurrent.fetch.serviceImpl;

import com.ds.concurrent.fetch.dto.common.CommonResponse;
import com.ds.concurrent.fetch.service.TestService;
import com.ds.concurrent.fetch.task.FetchTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final RestTemplate restTemplate;

    @Override
    public CommonResponse fetch() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Object>> todo = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FetchTask fetchTask = new FetchTask(i, restTemplate);
            todo.add(Executors.callable(fetchTask));
        }
        List<Future<Object>> answers = executorService.invokeAll(todo);
        executorService.shutdown();
        todo.clear();
        answers.clear();
        return new CommonResponse("Fetch Start Successfully!");
    }
}
