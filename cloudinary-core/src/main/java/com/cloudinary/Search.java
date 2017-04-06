package com.cloudinary;

import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Search {

    private ArrayList<HashMap<String, Object>> sortByParam;
    private ArrayList<String> aggregateParam;
    private ArrayList<String> includesParam;
    private HashMap<String, Object> params;

    Search() {
        this.params = new HashMap<String, Object>();
        this.sortByParam = new ArrayList<HashMap<String, Object>>();
        this.aggregateParam = new ArrayList<String>();
        this.includesParam = new ArrayList<String>();
    }

    public Search expression(String value, Object... formats) {
        this.params.put("expression", String.format(value, formats));
        return this;
    }

    public Search maxResults(Integer value) {
        this.params.put("max_results", value);
        return this;
    }

    public Search nextCursor(String value) {
        this.params.put("next_cursor", value);
        return this;
    }

    public Search aggregate(String field) {
        aggregateParam.add(field);
        return this;
    }

    public Search include(String field) {
        includesParam.add(field);
        return this;
    }

    public Search sortBy(String field, String dir) {
        HashMap<String, Object> sortBucket = new HashMap<String, Object>();
        sortBucket.put(field, dir);
        sortByParam.add(sortBucket);
        return this;
    }

    public HashMap<String, Object> toQuery() {
        HashMap<String, Object> queryParams = new HashMap<String, Object>(this.params);
        queryParams.put("include", includesParam);
        queryParams.put("sort_by", sortByParam);
        queryParams.put("aggregate", aggregateParam);
        return queryParams;
    }

    public ApiResponse execute() throws Exception {
        Cloudinary cloudinary = new Cloudinary();
        Map<String, String> options = ObjectUtils.asMap("content_type", "json");
        return cloudinary.api().callApi(Api.HttpMethod.POST, Arrays.asList("resources", "search"), this.toQuery(), options);
    }
}