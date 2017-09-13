package com.tt.rds.app.service;

public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}
