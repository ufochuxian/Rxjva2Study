package com.eric.operatprs;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
                    Log.i(TAG, "interval onSubscribe");
                }, () -> {
                    Log.i(TAG, "interval onDisposable");

                })
                .subscribe(i -> {
                            Log.i(TAG, "interval: " + i);
                        }

                );
        //解除订阅关系
//        disposable.dispose();
    }

    // 带迭代器，针对list等实现了迭代器接口的集合类使用 还有fromArray操作符
    public void fromIterable() {
        List list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");

        Observable.fromIterable(list)
                .map(o -> {
                    Log.i(TAG, "map thread: " + Thread.currentThread().getName());
                    return o + " join map";
                })
                //被观察者 在io线程中进行操作
                .subscribeOn(Schedulers.io())
                //观察者 在主线程中进行操作
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "onNext thread:" + Thread.currentThread().getName() + ", s: " + s);
                });
    }


    /**
     * 变化操作符
     * <p>
     * map
     * <p>
     * flatMap
     * <p>
     * conactMap
     */

    public static final String json = "{\"uid\":\"00001\",\"userName\":\"Freeman\",\"telNumber\":\"13000000000\"}";

    // map操作符的作用，作用是将一个对象 转换为 另一个对象
    //适用场景： 比如从一个接口请求回来json数据，需要经过fastjson处理，转换为javabean，等等类似的变换操作
    public void testMap() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext(json);
            }
        }).map((Function<String, Person>) s -> {
            //将json字符串，通过gson转换工具，进行转换javabean对象
            Gson gson = new Gson();
            Person person = gson.fromJson(s, Person.class);
            return person;
        }).subscribe(new Observer<Person>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Person person) {
                Log.i(TAG, "经过map转换后的person firstname: " + person);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }


}