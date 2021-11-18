package com.ds.concurrent.fetch.dto.common;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CommonResponse {
    private String message;

    public CommonResponse(String message) {
        this.message = message;
    }
}
