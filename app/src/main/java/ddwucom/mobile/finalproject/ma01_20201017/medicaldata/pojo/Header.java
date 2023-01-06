package ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo;

import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "header")
public class Header {
    @PropertyElement(name = "resultCode")
    private String resultCode;
    @PropertyElement(name = "resultMsg")
    private String resultMsg;
    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    public String getResultMsg() {
        return resultMsg;
    }
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}