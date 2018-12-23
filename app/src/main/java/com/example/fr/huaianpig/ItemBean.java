package com.example.fr.huaianpig;

/**
 * Created by Administrator on 2017/3/30.
 */

public class ItemBean {
    public String id;
    public String money;
    public String time;
    public String state;

    public ItemBean(String id, String money, String time, String state) {
        this.id = id;
        this.money = money;
        this.time = time;
        this.state = state;
    }

    public String getId() {return id;}

    public String getMoney() {
        return money;
    }

    public String getTime() {return time;}

    public String getState() {return state;}

    public void setId() {
        this.id = id;
    }

    public void setMoney() {
        this.money = money;
    }

    public void setTime() {
        this.time = time;
    }

    public void setState() {
        this.money = state;
    }
}
