package com.example.yesee.myfirstapp.vo;

/**
 * Created by yesee on 2016/12/13.
 */

public class DeviceInfo {
    private int dev_listid;
    private String dev_id;
    private String dev_name;
    private String dev_category;
    private String dev_ext_type;
    private String manu_code;
    private String enable_status;

    public int getDev_listid() {
        return dev_listid;
    }

    public void setDev_listid(int dev_listid) {
        this.dev_listid = dev_listid;
    }

    public String getDev_id() {
        return dev_id;
    }

    public void setDev_id(String dev_id) {
        this.dev_id = dev_id;
    }

    public String getDev_name() {
        return dev_name;
    }

    public void setDev_name(String dev_name) {
        this.dev_name = dev_name;
    }

    public String getDev_category() {
        return dev_category;
    }

    public void setDev_category(String dev_category) {
        this.dev_category = dev_category;
    }

    public String getDev_ext_type() {
        return dev_ext_type;
    }

    public void setDev_ext_type(String dev_ext_type) {
        this.dev_ext_type = dev_ext_type;
    }

    public String getManu_code() {
        return manu_code;
    }

    public void setManu_code(String manu_code) {
        this.manu_code = manu_code;
    }

    public String getEnable_status() {
        return enable_status;
    }

    public void setEnable_status(String enable_status) {
        this.enable_status = enable_status;
    }
}
