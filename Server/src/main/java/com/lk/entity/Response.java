package com.lk.entity;

import java.util.List;
import java.util.Map;

public class Response {

    private boolean isSuccess;
    private String message;
    private Exception exception;
    private List<Object> List;
    private Map<String, List<Object>> Map;
    private Object object;

    public Response(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Response(boolean isSuccess, Object object) {
        this.isSuccess = isSuccess;
        this.object = object;
    }

    public Response(boolean isSuccess, List<Object> list) {
        this.isSuccess = isSuccess;
        this.List = list;
    }

    public Response(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public Response(boolean isSuccess, String message, Exception exception, Object object) {
        this(isSuccess, message, object);
        this.exception = exception;
    }

    public Response(boolean isSuccess, String message, Exception exception) {
        this(isSuccess, message);
        this.exception = exception;
    }

    public Response(boolean isSuccess, String message, Object object) {
        this(isSuccess, message);
        this.object = object;
    }

    public Response(boolean isSuccess, String message, Exception exception, List<Object> list) {
        this(isSuccess, message, exception);
        this.List = list;
    }

    public Response(boolean isSuccess, Map<String, List<Object>> map) {
        this.isSuccess = isSuccess;
        this.Map = map;
    }

    public Response(boolean isSuccess, String message, List<Object> list) {
        this(isSuccess, message);
        this.List = list;
    }

    public Response(boolean isSuccess, String message, Map<String, List<Object>> map) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.Map = map;
    }

    public Response(boolean isSuccess, String message, Object object, Map<String, List<Object>> map) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.object = object;
        this.Map = map;
    }

    public boolean getIsSuccess() {
        return this.isSuccess;
    }

    private void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return this.message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public Exception getException() {
        return this.exception;
    }

    private void setException(Exception exception) {
        this.exception = exception;
    }

    public Map<String, List<Object>> getMap() {
        return this.Map;
    }

    private void setMap(Map<String, List<Object>> map) {
        this.Map = map;
    }

    public List<Object> getList() {
        return this.List;
    }

    private void setList(List<Object> list) {
        this.List = list;
    }

    public Object getObject() {
        return this.object;
    }

    private void setObject(Object object) {
        this.object = object;
    }
}
