package com.eric.rxjava;

import android.util.Log;

abstract class Observable<T> {

    private static final String TAG = "Observable";

    //1. 创建被观察者
    public static Observable create(Observable observable) {
        return observable;
    }

    //2. 订阅方法
    public abstract void subscribe(Observer<T> observer);

    // 自定义flatmap操作符
    public Observable<T> flatMap() {
        return new Observable<T>() {
            @Override
            public void subscribe(Observer<T> observerC) {
                Observer<T> observerB = new Observer<T>() {

                    @Override
                    public void onNext(T t) {
                        Log.v(CustomRxjava.FLATMAP_TAG, "observerB 的onNext");
                        observerC.onNext(t);
                    }

                    @Override
                    public void onComplete(T t) {
                        Log.v(CustomRxjava.FLATMAP_TAG, "observerB的 onComplete");
                        observerC.onComplete(t);
                    }

                    @Override
                    public void onError(T t) {
                        Log.v(CustomRxjava.FLATMAP_TAG, "observerB 的onError");
                        observerC.onError(t);
                    }
                };
                Log.v(CustomRxjava.FLATMAP_TAG, "this:" + this);
                Log.v(CustomRxjava.FLATMAP_TAG, "Observable this:" + Observable.this);
                Observable.this.subscribe(observerB);
            }
        };
    }

}
