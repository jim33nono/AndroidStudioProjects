package com.example.yesee.jsontogsonbyurl.vo;

import java.util.List;

/**
 * Created by yesee on 2016/12/13.
 */

public class Result {
    private int limit;
    private int total;
    private List<Field> fields;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> listField) {
        this.fields = listField;
    }
}
