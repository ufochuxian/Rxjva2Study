package com.eric.rxjava;

import android.util.Log;

/**
 * @Author: chen
 * @datetime: 2021/6/24
 * @desc:
 */
class Observable<T> {

    private static final String TAG = "Observable";

    //1. 创建被观察者
    public static Observable create(Observable observable) {
        return observable;
    }

    //2. 订阅方法
    public void subscribe(Observer<T> observer) {
        Log.i(TAG, "[subscribe]");

    }

}
