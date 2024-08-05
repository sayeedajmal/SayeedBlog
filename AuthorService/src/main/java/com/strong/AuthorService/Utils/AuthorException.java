package com.strong.AuthorService.Utils;

public class AuthorException extends Exception {

    public AuthorException() {
        super();
    }

    public AuthorException(String Message) {
        super(Message);
    }

    public AuthorException(String Message, Throwable throwable) {
        super(Message, throwable);
    }

    public AuthorException(Throwable throwable) {
        super(throwable);
    }

}
