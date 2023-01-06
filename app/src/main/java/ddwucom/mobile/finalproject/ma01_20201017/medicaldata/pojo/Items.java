package ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.ArrayList;
import java.util.List;

@Xml(name = "items")
public class Items {
    @Element(name="item")
    List<Item> item = new ArrayList<>();
    public List<Item> getItem() {
        return item;
    }
    public void setItem(List<Item> item) {
        this.item = item;
    }
}