package com.strong.PostService.Utils;

public class BlogException extends Exception {

    public BlogException() {
        super();
    }

    public BlogException(String Message) {
        super(Message);
    }

    public BlogException(String Message, Throwable throwable) {
        super(Message, throwable);
    }

    public BlogException(Throwable throwable) {
        super(throwable);
    }

}
