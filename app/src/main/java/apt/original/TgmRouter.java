package apt.original;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chen
 * @datetime: 2021/6/29
 * @desc:
 */
public class TgmRouter {

    private static final String TAG = "TgmRouter";

    private Context mCtx;

    public void init(Context context) {
        this.mCtx = context;
    }

    //path 集合
    public Map<String, Class> routes = new HashMap<>();

    private static TgmRouter mInstance = null;

    private TgmRouter() {

    }

    public synchronized static TgmRouter getInstance() {
        if (mInstance == null) {
            synchronized (TgmRouter.class) {
                if (mInstance == null) {
                    mInstance = new TgmRouter();
                }
            }
        }
        return mInstance;
    }

    public void register(String path, Class clazz) {
        routes.put(path, clazz);
    }


    public void startActivity(String path) {
        Class aClass = routes.get(path);

        if (aClass == null) {
            Log.e(TAG, "路由跳转的地址不存在");
            return;
        }

        Intent intent = new Intent(mCtx, aClass);
        this.mCtx.startActivity(intent);
    }

}
