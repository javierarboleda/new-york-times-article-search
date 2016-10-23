package com.javierarboleda.newyorktimesarticlesearch.utils;

/**
 * Created on 10/18/16.
 */
public class NytApiUtil {

    // URLs
    public final static String NYT_API_BASE_URL = "https://api.nytimes.com/svc/search/v2/";
    public final static String NYT_BASE_URL = "http://www.nytimes.com/";

    // Params
    public final static String API_KEY_PARAM = "api-key";
    public final static String QUERY_PARAM = "q";
    public final static String BEGIN_DATE_PARAM = "begin_date";
    public final static String END_DATE = "end_date";
    public final static String SORT_PARAM = "sort";
    public final static String PAGE_PARAM = "page";
    public final static String FILTER_QUERY_PARAM = "fq";

    // Values
    public final static String SORT_NEWEST = "newest";
    public final static String SORT_OLDEST = "oldest";

}
