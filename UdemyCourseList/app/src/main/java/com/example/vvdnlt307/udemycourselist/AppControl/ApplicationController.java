package com.example.vvdnlt307.udemycourselist.AppControl;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/*******************************************************************************
 * MyApplication holds the access of whole application (application context to
 * be used while showing alrets and animations)
 *
 * @author Kamal
 ******************************************************************************/
public class ApplicationController extends Application {

    public static final String TAG = ApplicationController.class
            .getSimpleName();
    public static Context mApplicationContext = null;
    private static ApplicationController mInstance;
    final int MAX_CACHE_SIZE = 2 * 1024 * 1024; // 2 MB
    int MAX_SERIAL_THREAD_POOL_SIZE = 1;
    private RequestQueue mRequestQueue, serialRequestQueue;
    private ImageLoader mImageLoader;
    private AbstractHttpClient mHttpClient;




    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


    public static synchronized ApplicationController getInstance()
    {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = this.getApplicationContext();

        /* initializes the parse constants */
        mInstance = this;
        // we hold a reference to the HttpClient in order to be able to get/set
        // cookies
        mHttpClient = new DefaultHttpClient();


    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext(),
                    new HttpClientStack(mHttpClient));
        } else {
        }
        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getSerialRequestQueue().add(req);
    }


    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**
     * Use to fetch the serial request queue
     */
    public RequestQueue getSerialRequestQueue() {
//        if (getStoredRequestQueue() != null) {
//            serialRequestQueue = getStoredRequestQueue();
//        }
        if (serialRequestQueue == null) {
            serialRequestQueue = prepareSerialRequestQueue();
            serialRequestQueue.start();
//            storeRequestQueue();
        }
        return serialRequestQueue;
    }

    private RequestQueue prepareSerialRequestQueue() {
        Cache cache = new DiskBasedCache(mApplicationContext.getCacheDir(),
                MAX_CACHE_SIZE);
        Network network = getNetwork();
        return new RequestQueue(cache, network, MAX_SERIAL_THREAD_POOL_SIZE);
    }

    private Network getNetwork() {
        HttpStack stack;
        // String userAgent = "volley/0";
        // if (Build.VERSION.SDK_INT >= 9) {
        // stack = new HurlStack();
        // } else {
        stack = new HttpClientStack(mHttpClient);
        // }

        return new BasicNetwork(stack);
    }

    public <T> void addToImageRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getSerialRequestQueue().add(req);
    }


}
