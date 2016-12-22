package com.example.vvdnlt307.udemycourselist.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vvdnlt307.udemycourselist.MainActivity;
import com.example.vvdnlt307.udemycourselist.R;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by kamal on 20/12/16.
 */

public class CourseAdapter extends BaseAdapter
{

    private Activity mActivity;


    private LayoutInflater mLayoutInflater;

    private JSONArray mSuccessArray;

    public CourseAdapter()
    {}

    public CourseAdapter(Activity mActivity)
    {
       this.mActivity=mActivity;
        try {
            mLayoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUpdateDataIntoList(JSONArray mSuccessArray) {
        this.mSuccessArray = mSuccessArray;
    }

    @Override
    public int getCount()
    {
        return mSuccessArray.length();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;

        if (convertView == null)
        {
            mViewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.course_list_row, null);
            mViewHolder.mcourseid = (TextView) convertView.findViewById(R.id.course_id);
            mViewHolder.mcourse_title = (TextView) convertView.findViewById(R.id.title);
            mViewHolder.class_txt=(TextView)convertView.findViewById(R.id.class_s);
            mViewHolder.channel_id=(TextView)convertView.findViewById(R.id.channel_id);
            mViewHolder.parent_layout=(RelativeLayout)convertView.findViewById(R.id.parent_layout);
            convertView.setTag(mViewHolder);
        }
        else
        {
            mViewHolder = (ViewHolder) convertView.getTag();
        }


        try
        {
            Log.e("---------------","-----------Inside Adapter classs-----------");



            for(int i=0;i<mSuccessArray.length();i++)
            {

                if(position==i)
                {
                    JSONObject item = mSuccessArray.getJSONObject(i);
                    Log.e("------------","----------arry" + "------"+item.getString("id"));
                    Log.e("------------","----------title" + "------"+item.getString("title"));
                    mViewHolder.mcourseid.setText(item.getString("id"));
                    mViewHolder.mcourse_title.setText(item.getString("title"));
                    mViewHolder.channel_id.setText(item.getString("channel_id"));
                    MainActivity.nDialog.cancel();
                }

            }
        }
        catch (Exception e)
        {

        }



        return convertView;
    }


    public class ViewHolder {
        TextView mcourseid, mcourse_title,class_txt,channel_id;

        RelativeLayout parent_layout;

    }
}
