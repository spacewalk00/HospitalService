package ddwucom.mobile.finalproject.ma01_20201017;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo.Item;
@Dao
public interface ItemDao {
    @Insert
    long insertItem(Item item);

    @Delete
    void deleteItem(Item item);

    @Query("SELECT * FROM item_table")
    List<Item> getAllItem();

    @Query("SELECT * FROM item_table WHERE telno = :telno")
    Item getItem(String telno);

    @Query("UPDATE item_table SET memo = :newMemo WHERE telno = :telno")
    int updateItem(String newMemo, String telno);
}
