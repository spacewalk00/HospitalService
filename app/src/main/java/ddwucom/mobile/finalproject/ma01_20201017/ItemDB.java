package ddwucom.mobile.finalproject.ma01_20201017;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo.Item;

@Database(entities = {Item.class}, version = 6)
public abstract class ItemDB extends RoomDatabase {
    public abstract ItemDao itemDao();

    private static volatile ItemDB INSTANCE;

    static ItemDB getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (ItemDB.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ItemDB.class, "item_table.db").build();
                }
            }
        }
        return INSTANCE;
    }

}
