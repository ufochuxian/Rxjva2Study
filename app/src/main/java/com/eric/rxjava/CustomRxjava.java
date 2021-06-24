package com.eric.rxjava;

import android.util.Log;

/**
 * @Author: chen
 * @datetime: 2021/6/24
 * @desc:
 */
class CustomRxjava {

    //执行结果
//            2021-06-24 21:28:37.854 31276-31276/com.eric.rxjava V/CustomRxjava: [subscribe]
//            2021-06-24 21:28:37.854 31276-31276/com.eric.rxjava V/CustomRxjava: [onNext],hello :0
//            2021-06-24 21:28:37.854 31276-31276/com.eric.rxjava V/CustomRxjava: [onNext],hello :1
//            2021-06-24 21:28:37.854 31276-31276/com.eric.rxjava V/CustomRxjava: [onNext],hello :2
//            2021-06-24 21:28:37.854 31276-31276/com.eric.rxjava V/CustomRxjava: [onNext],hello :3
//            2021-06-24 21:28:37.854 31276-31276/com.eric.rxjava V/CustomRxjava: [onNext],hello :4
//            2021-06-24 21:28:37.854 31276-31276/com.eric.rxjava V/CustomRxjava: [onNext],hello :5
//            2021-06-24 21:28:37.854 31276-31276/com.eric.rxjava V/CustomRxjava: [onNext],hello :6
//            2021-06-24 21:28:37.854 31276-31276/com.eric.rxjava V/CustomRxjava: [onNext],hello :7
//            2021-06-24 21:28:37.854 31276-31276/com.eric.rxjava V/CustomRxjava: [onNext],hello :8
//            2021-06-24 21:28:37.854 31276-31276/com.eric.rxjava V/CustomRxjava: [onNext],hello :9
//            2021-06-24 21:28:37.854 31276-31276/com.eric.rxjava V/CustomRxjava: [onComplete]


    private static final String TAG = "CustomRxjava";

    public void testRxjava() {
        Observable.create(new Observable() {
            @Override
            public void subscribe(Observer observer) {
                Log.v(TAG, "[subscribe]");

                for (int i = 0; i < 10; i++) {
                    observer.onNext("hello :" + i);
                }
                observer.onComplete("观察者执行结束啦");
            }
        }).subscribe(new Observer() {
            @Override
            public void onNext(Object o) {
                Log.v(TAG, "[onNext]," + o.toString());


            }

            @Override
            public void onComplete(Object o) {
                Log.v(TAG, "[onComplete]");

            }

            @Override
            public void onError(Object o) {
                Log.v(TAG, "[onError]," + o.toString());

            }
        });
    }
}
