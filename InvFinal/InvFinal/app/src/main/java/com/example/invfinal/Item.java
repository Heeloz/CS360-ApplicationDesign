package com.example.invfinal;

public class Item {
    // Create item class for transport
    private long mItemId;
    private String mItem_name;
    private String mItem_number;
    private String mItem_qty;
    private long mUpdateTime;

    public Item() {}

    public Item(String item_name, String item_number, String item_qty) {
        mItem_name = item_name;
        mItem_number = item_number;
        mItem_qty = item_qty;
        mUpdateTime = System.currentTimeMillis();
    }
    public Item(String item_name, String item_number, String item_qty, long id) {
        mItem_name = item_name;
        mItem_number = item_number;
        mItem_qty = item_qty;
        mItemId = id;
        mUpdateTime = System.currentTimeMillis();
    }

    public long getmItemId() {return mItemId;}

    public void setmItemId(long itemId) {mItemId = itemId;}

    public String getmItem_name() {
        return mItem_name;
    }


    public void setmItem_name(String text) {
        mItem_name = text;
    }

    public String getmItem_number() {
        return mItem_number;
    }

    public void setmItem_number(String num) {
        mItem_number = num;
    }

    public String getmItem_qty() {
        return mItem_qty;
    }

    public void setmItem_qty(String num) {
        mItem_qty = num;
    }

    public long getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(long updateTime) {
        mUpdateTime = updateTime;
    }

}
