package com.nhnacademy.webserver.enums;

public enum API {
    ACCOUNT("http://localhost:8090/account/api"), TASK("http://localhost:8090/task/api");
    String domain;

    API(String domain){
        this.domain = domain;
    }

    public String toString(){
        return this.domain;
    }
}
