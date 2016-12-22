package com.example.vvdnlt307.udemycourselist.webservice;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vvdnlt307.udemycourselist.AppControl.ApplicationController;
import com.example.vvdnlt307.udemycourselist.Global.GlobalKeys;
import com.example.vvdnlt307.udemycourselist.iHelper.WebAPIResponseListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kamal on 20/12/16.
 */

public class GetcoursedetailsAPIHandler {

    private Activity mActivity;
    /**
     * Debug TAG
     */
    private String TAG = GetcoursedetailsAPIHandler.class.getSimpleName();
    /**
     * API Response Listener
     */
    private WebAPIResponseListener mResponseListener;


    /**
     * @param mActivity
     * @param webAPIResponseListener
     */

    public GetcoursedetailsAPIHandler(Activity mActivity,
                                     WebAPIResponseListener webAPIResponseListener) {

        this.mActivity = mActivity;
        this.mResponseListener = webAPIResponseListener;

        Log.e("********Inside *********","************GetcoursedetailsAPIHandler*********");

        postAPICall();
    }

    /**
     * Making json object request
     */
    public void postAPICall() {
        /**
         * JSON Request
         */
        Log.e("********Inside *********","************POSTAPI CALL*********"+GlobalKeys.GET_COURSE_API.trim());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                GlobalKeys.GET_COURSE_API.trim(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        parseAPIResponse(response);
                        Log.e(TAG,"***********************JSON SuccessResponse***************************"+response);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (mResponseListener != null)
                    mResponseListener.onOfflineResponse();


            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(GlobalKeys.CONTENT_TYPE, GlobalKeys.APP_JSON);
                return params;
            }
        };

        // Adding request to request queue
        ApplicationController.getInstance().addToRequestQueue(jsonObjReq,
                GetcoursedetailsAPIHandler.class.getSimpleName());
        // set request time-out
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                GlobalKeys.ONE_SECOND * GlobalKeys.API_REQUEST_TIME,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }



    /*Parse API Response
      * @param response*/
    protected void parseAPIResponse(JSONObject response) {

        Log.e(TAG,"***********************Inside parseAPIResponse***************************"+response);

        if (response != null)
        {mResponseListener.onSuccessResponse(response);}
        else
        {
            mResponseListener.onFailResponse(response);
        }
    }
}
