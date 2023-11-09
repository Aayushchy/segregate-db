package com.esewa.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseStatusCode {
    public static final int BAD_REQUEST = 400;
    public static final int SUCCESS = 200;
    public static final int INTERNAL_SERVER_ERROR = 500;

}
