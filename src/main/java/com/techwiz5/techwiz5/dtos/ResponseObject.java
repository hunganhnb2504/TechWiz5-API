    package com.techwiz5.techwiz5.dtos;

    import com.fasterxml.jackson.annotation.JsonInclude;
    import lombok.*;
    import lombok.experimental.FieldDefaults;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class ResponseObject {
        private boolean status;
        private int statusCode;
        private String message;
        private Object data;
    }
