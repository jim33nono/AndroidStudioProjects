package com.example.yesee.jsontogsonbyurl.vo;

/**
 * Created by yesee on 2016/12/13.
 */

public class Field {
    private String type;
    private String id;
    private boolean open;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
