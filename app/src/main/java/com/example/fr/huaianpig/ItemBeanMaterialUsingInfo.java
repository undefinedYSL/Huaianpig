package com.example.fr.huaianpig;

/**
 * Created by FR on 2017/7/13.
 */
public class ItemBeanMaterialUsingInfo {
    public String name;
    public String price;
    public String count;
    public String time;

    public ItemBeanMaterialUsingInfo(String name, String price, String count, String time) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.time = time;
    }

    public String getName() {return name;}

    public String getPrice() {
        return price;
    }

    public String getTime() {return time;}

    public String getCount() {return count;}

    public void setName() {
        this.name = name;
    }

    public void setPrice() {
        this.price = price;
    }

    public void setTime() {
        this.time = time;
    }

    public void setCount() {
        this.count = count;
    }
}
