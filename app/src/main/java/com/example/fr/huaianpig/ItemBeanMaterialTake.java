package com.example.fr.huaianpig;

/**
 * Created by FR on 2017/7/13.
 */
public class ItemBeanMaterialTake {
    public String name;
    public String count;
    public String time;

    public ItemBeanMaterialTake(String name, String count, String time) {
        this.name = name;
        this.count = count;
        this.time = time;
    }

    public String getName() {return name;}

    public String getCount() {
        return count;
    }

    public String getTime() {return time;}


    public void setName() {
        this.name = name;
    }

    public void setCount() {
        this.count = count;
    }

    public void setTime() {
        this.time = time;
    }

}