package com.example.vvdnlt307.udemycourselist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vvdnlt307.udemycourselist.Adapter.CourseAdapter;
import com.example.vvdnlt307.udemycourselist.Global.GlobalKeys;
import com.example.vvdnlt307.udemycourselist.iHelper.WebAPIResponseListener;
import com.example.vvdnlt307.udemycourselist.webservice.GetcoursedetailsAPIHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity
{
    private Activity mActivity;

    public static ProgressDialog nDialog;

    private String TAG = MainActivity.class.getSimpleName();

    private CourseAdapter mCourseAdapter;

    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        new NetCheck().execute();
    }

    /**
     * Function for Initializing the Views
     */
    private void init()
    {
        mActivity = MainActivity.this;
        mListView=(ListView)findViewById(R.id.list_item);
        mCourseAdapter=new CourseAdapter(mActivity);
    }

    private WebAPIResponseListener GetCourseAPIResponseListener()
    {

        WebAPIResponseListener webAPIResponseListener =new WebAPIResponseListener() {
            @Override
            public void onSuccessResponse(Object... arguments) {
                try {
                        JSONObject mSuccessResponse = (JSONObject) arguments[0];
                    JSONArray mSuccessArray = mSuccessResponse.getJSONArray(GlobalKeys.RESPONSE_RESULT);
                    mCourseAdapter.addUpdateDataIntoList(mSuccessArray);
                    mListView.setAdapter(mCourseAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailResponse(Object... arguments) {

            }

            @Override
            public void onOfflineResponse(Object... arguments) {

            }
        };

        return webAPIResponseListener;

    }


    /**
     * Function for Checking the Internet connection is there or not
     */
    private class NetCheck extends AsyncTask<String,Integer,Boolean>
    {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(mActivity);
            nDialog.setMessage("Loading...");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }
        protected Boolean doInBackground(String... args)
        {
/**
 * Gets current device state and checks for working internet connection by trying Google.
 **/
            System.out.println("Executed");
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;
        }
        protected void onPostExecute(Boolean th){
            if(th){
                new GetcoursedetailsAPIHandler(mActivity,GetCourseAPIResponseListener());
            }
            else{
                nDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
