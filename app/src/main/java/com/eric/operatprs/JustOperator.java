package com.eric.operatprs;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * @Author: chen
 * @datetime: 2021/10/18
 * @desc:
 */
public class JustOperator {

    private static final String TAG = "JustOperator";

    public void flowable() {
        Flowable.just("Hello world", "nice to meet you").subscribe(
                s -> {
                    Log.v(TAG, s);
                }
        );
    }

    public void just() {
        Observable.just(1, 2, 3).subscribe(
                x -> {
                    Log.v(TAG, "just: " + x);
                }

        );
    }

    public void zipWith() {
        Disposable d = Observable.range(0, 30)
                .doOnNext(integer -> {
                    Log.i(TAG, "zipWidth" + integer);
                })
                .interval(100, TimeUnit.MILLISECONDS)
                .zipWith(Observable.range(10, 30), (i1, i2) -> i1 + "-" + i2)
                .take(30)
                .subscribe();
    }

    public void interval() {
        Disposable disposable = null;
        disposable = Observable.range(0, 30)
                //定时器
                .interval(100, TimeUnit.MILLISECONDS)
                .take(10)//限制次数
                .doOnComplete(() -> {
                    Log.i(TAG, "interval onComplete");
                })
                .doOnLifecycle(disposable1 -> {
                    Log.i(TAG,"interval onSubscribe");
                }, () -> {
                    Log.i(TAG,"interval onDisposable");

                })
                .subscribe(i -> {
                            Log.i(TAG, "interval: " + i);
                        }

                );
        //解除订阅关系
//        disposable.dispose();
    }
}