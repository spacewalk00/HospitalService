package ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.List;

@Xml(name = "body")
public class Body {
    @PropertyElement(name="pageNo")
    private Integer pageNo;
    @PropertyElement(name="totalCount")
    private Integer totalCount;
    @Element(name="items")
    private Items items;
    @PropertyElement(name="numOfRows")
    private Integer numOfRows;
    public Integer getPageNo() {
        return pageNo;
    }
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
    public Integer getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
    public Items getItems() {
        return items;
    }
    public void setItems(Items items) {
        this.items = items;
    }
    public Integer getNumOfRows() {
        return numOfRows;
    }
    public void setNumOfRows(Integer numOfRows) {
        this.numOfRows = numOfRows;
    }
}