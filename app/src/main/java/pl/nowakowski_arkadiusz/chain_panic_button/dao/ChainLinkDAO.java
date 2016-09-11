package pl.nowakowski_arkadiusz.chain_panic_button.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import pl.nowakowski_arkadiusz.chain_panic_button.models.ChainLink;
import pl.nowakowski_arkadiusz.chain_panic_button.models.ChainLinkType;

public class ChainLinkDAO extends SQLiteOpenHelper {

    public ChainLinkDAO(Context context) {
        super(context, "chain_panic_button", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createChainLinkTable = "CREATE TABLE chain_link (" +
            "id INT PRIMARY_KEY," +
            "type INT NOT NULL," +
            "name TEXT NOT NULL," +
            "message TEXT NULL," +
            "addLocation INT NULL," +
            "addPhoto INT NULL," +
            "phone TEXT NULL," +
            "email TEXT NULL," +
            "subject TEXT NULL" +
            ")";
        db.execSQL(createChainLinkTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert(ChainLink chainLink) {
        SQLiteDatabase database = getWritableDatabase();
        database.insert("chain_link", null, chainLink.toContentValues());
    }

    public List<ChainLink> findAll() {
        List<ChainLink> chainLinks = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM chain_link ORDER BY `type` ASC", null);
        while (cursor.moveToNext()) {
            ChainLink chainLink = new ChainLink(
                cursor.getInt(cursor.getColumnIndex("id")),
                ChainLinkType.getByType(cursor.getInt(cursor.getColumnIndex("type"))),
                cursor.getString(cursor.getColumnIndex("name")),
                cursor.getString(cursor.getColumnIndex("message")),
                cursor.getInt(cursor.getColumnIndex("addLocation")) == 1,
                cursor.getInt(cursor.getColumnIndex("addPhoto")) == 1,
                cursor.getString(cursor.getColumnIndex("phone")),
                cursor.getString(cursor.getColumnIndex("email")),
                cursor.getString(cursor.getColumnIndex("subject"))
            );
            chainLinks.add(chainLink);
        }
        cursor.close();
        return chainLinks;
    }

    public boolean hasCallChainLink() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM chain_link WHERE type = " +
                ChainLinkType.CALL.getValue() + " LIMIT 1", null);
        System.out.println(cursor.getCount());
        boolean existing = cursor.getCount() > 0;
        cursor.close();
        return existing;
    }
}
