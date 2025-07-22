package wotc.models;

import java.util.List;

public class ScryfallBulkResponse {
    private String object;
    private boolean has_more;
    private List<ScryfallBulkData> data;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public List<ScryfallBulkData> getData() {
        return data;
    }

    public void setData(List<ScryfallBulkData> data) {
        this.data = data;
    }
}
