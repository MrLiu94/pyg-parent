package PageBean;

import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable {
    private Long page;
    private List rows;

    public PageResult(Long page, List rows) {
        this.page = page;
        this.rows = rows;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
