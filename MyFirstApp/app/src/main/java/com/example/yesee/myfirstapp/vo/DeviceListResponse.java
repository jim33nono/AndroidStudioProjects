package com.example.yesee.myfirstapp.vo;

import java.util.List;

/**
 * Created by yesee on 2016/12/13.
 */

public class DeviceListResponse {
    private Err err;
    private String status;
    private List<DeviceInfo> device_info_list;

    public Err getErr() {
        return err;
    }

    public void setErr(Err err) {
        this.err = err;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DeviceInfo> getDevice_info_list() {
        return device_info_list;
    }

    public void setDevice_info_list(List<DeviceInfo> device_info_list) {
        this.device_info_list = device_info_list;
    }
}
