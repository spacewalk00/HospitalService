package ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "response")
public class HospRoot {
    @Element
    private Header header;
    @Element
    private Body body;
    public Header getHeader() {
        return header;
    }
    public void setHeader(Header header) {
        this.header = header;
    }
    public Body getBody() {
        return body;
    }
    public void setBody(Body body) {
        this.body = body;
    }
}
