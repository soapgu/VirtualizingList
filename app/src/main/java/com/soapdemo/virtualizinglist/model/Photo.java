package com.soapdemo.virtualizinglist.model;

import com.google.gson.annotations.SerializedName;

public class Photo {
    public String id;
    public int width;
    public int height;

    public String alt_description;

    public transient int position;
}
