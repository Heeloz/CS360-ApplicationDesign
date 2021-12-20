package com.example.invfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class InvDB extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "inventory.db";
    private static InvDB mInvDB;
    private Context context;

    public static InvDB getInstance(Context context) {
        if(mInvDB == null) {
            mInvDB = new InvDB(context);
        }
        return mInvDB;
    }

    InvDB(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    private static final class inventoryInfoTable {
        private static final String TABLE = "inventoryInfo";
        private static final String COL_ID = "_id";
        private static final String COL_ITEM_NAME = "ITEM_NAME";
        private static  final String COL_ITEM_QTY = "ITEM_QTY";
        private static final String COL_ITEM_NUMBER = "ITEM_NUMBER";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ InvDB.inventoryInfoTable.TABLE + " ("
                + InvDB.inventoryInfoTable.COL_ID + " integer primary key autoincrement, " +
                InvDB.inventoryInfoTable.COL_ITEM_NAME + " text, " +
                inventoryInfoTable.COL_ITEM_NUMBER + " integer, " +
                InvDB.inventoryInfoTable.COL_ITEM_QTY + " integer);" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + InvDB.inventoryInfoTable.TABLE);
        onCreate(db);
    }


    // create item
    public Boolean createItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(inventoryInfoTable.COL_ITEM_NAME, item.getmItem_name());
        values.put(inventoryInfoTable.COL_ITEM_NUMBER, item.getmItem_number());
        values.put(inventoryInfoTable.COL_ITEM_QTY, item.getmItem_qty());
        long id = db.insert(inventoryInfoTable.TABLE, null, values);
        item.setmItemId(id);
        return id != -1;
    }

    public Item getItem(String itemId) {
        Item item = null;
        Log.i("itemid", String.valueOf(itemId));
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + inventoryInfoTable.TABLE +
                " where " + inventoryInfoTable.COL_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{ itemId });

        if (cursor.moveToFirst()) {
            Log.i("cursor good", String.valueOf(item));
            item = new Item();
            item.setmItemId(cursor.getInt(0));
            item.setmItem_name(cursor.getString(1));
            item.setmItem_number(cursor.getString(2));
            item.setmItem_qty(cursor.getString(3));
        }
        return item;
    }

    // update item
    public void updateItem(String item_name,String item_number,String item_qty,String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(inventoryInfoTable.COL_ITEM_NAME, item_name);
        cv.put(inventoryInfoTable.COL_ITEM_NUMBER, item_number);
        cv.put(inventoryInfoTable.COL_ITEM_QTY,item_qty);

        long result = db.update(inventoryInfoTable.TABLE, cv, "_id=?", new String[]{row_id});
        if(result == 0 )
            Toast.makeText(context,"zero",Toast.LENGTH_LONG).show();

        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(inventoryInfoTable.TABLE, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    // show all items
    Cursor readAllData() {
        String query = "select * from " + inventoryInfoTable.TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
           cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}