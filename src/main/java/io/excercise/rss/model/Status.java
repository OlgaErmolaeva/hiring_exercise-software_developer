package io.excercise.rss.model;

public enum  Status {

    RUNNING("RUNNING"),
    READY("READY"),
    ERROR("ERROR");

    private String status;

    Status(String status) {
        this.status = status;
    }
}
