package com.example.fr.huaianpig;

/**
 * Created by FR on 2017/7/13.
 */
public class ItemBeansick {
    public String id;
    public String data;
    public String location;
    public String weight;

    public ItemBeansick(String id, String data, String location, String weight) {
        this.id = id;
        this.data = data;
        this.location = location;
        this.weight = weight;
    }

    public String getId() {return id;}

    public String getData() {
        return data;
    }

    public String getLocation() {return location;}

    public String getWeight() {return weight;}

    public void setId() {
        this.id = id;
    }

    public void setData() {
        this.data = data;
    }

    public void setLocation() {
        this.location = location;
    }

    public void setWeight() {this.weight = weight;}
}

