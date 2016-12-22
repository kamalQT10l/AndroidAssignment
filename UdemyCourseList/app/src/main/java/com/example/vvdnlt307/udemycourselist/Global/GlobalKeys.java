package com.example.vvdnlt307.udemycourselist.Global;

/**
 * Created by kamal on 20/12/16.
 */

public interface GlobalKeys
{

     String GET_COURSE_API="https://www.udemy.com/api-2.0/course-categories/?fields%5Bcourse_category%5D=@all&locale=en_GB";

    String CONTENT_TYPE = "Content-Type";
    String APP_JSON = "application/json";

    /* response keys */
    String RESPONSE_RESULT = "results";

    int ONE_SECOND = 1000, API_REQUEST_TIME = 20;


}
