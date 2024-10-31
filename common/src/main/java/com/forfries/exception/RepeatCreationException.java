package com.forfries.exception;

import com.forfries.context.BaseContext;

public class RepeatCreationException extends BaseException {
    public RepeatCreationException(String message) {
        super(message);
    }
}
