package com.techwiz5.techwiz5.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    //feedback

    UNAUTHORIZED (400,"You are not authorized to delete this wishlist.",HttpStatus.BAD_REQUEST) ,
    USER_NOT_FOUND (404, "User Not Found", HttpStatus.NOT_FOUND),
    INVALID_TOKEN(400, "Invalid Token", HttpStatus.BAD_REQUEST) ,
    PREFERRED_CURRENCY_NOTFOUND(404, "Preferred Currency Not Found", HttpStatus.NOT_FOUND) ,
    INVALID_DATE(404 , "Invalid Date", HttpStatus.NOT_FOUND) ,
    TRIP_NOTFOUND(404, "Trip Not Found", HttpStatus.NOT_FOUND) ,
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    EXPIRES(400, "Expires", HttpStatus.BAD_REQUEST),
    //storage
    INITIALIZE(400, "Cannot initialize storage", HttpStatus.BAD_REQUEST),
    CART_EMPTY(400, "Cart is empty", HttpStatus.BAD_REQUEST),
    PHOTO_NOTFOUND(400, "Photo Not Found", HttpStatus.NOT_FOUND),
    // Category
    CATEGORY_NOTFOUND(404, "Category Not Found", HttpStatus.NOT_FOUND),
    CATEGORY_EXISTED(400, "Category name existed", HttpStatus.BAD_REQUEST),

    NOTFOUND(404, "Not Found", HttpStatus.NOT_FOUND),
    // Student
    STUDENT_NOTFOUND(404, "Student Not Found", HttpStatus.NOT_FOUND),
    // User
    USER_NOTFOUND(404, "User Not Found", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    INVALIDEMAILORPASSWORD(400, "Invalid email or password", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(401, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(403, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_RESETTOKEN(400, "Invalid or expired reset token", HttpStatus.NOT_FOUND),
    FILE_UPLOAD_FAILED (400, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR),


    ;
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}