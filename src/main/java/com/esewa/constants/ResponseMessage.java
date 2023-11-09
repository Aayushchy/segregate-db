package com.esewa.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseMessage {

    public static final String FILE_NOT_FOUND = "File not found";
    public static final String REQUEST_FAILED = "Request Failed!";
    public static final String ERROR_READ_FILE = "Error in reading file";

}
