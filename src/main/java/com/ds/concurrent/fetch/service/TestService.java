package com.ds.concurrent.fetch.service;

import com.ds.concurrent.fetch.dto.common.CommonResponse;

public interface TestService {
    CommonResponse fetch() throws InterruptedException;
}
