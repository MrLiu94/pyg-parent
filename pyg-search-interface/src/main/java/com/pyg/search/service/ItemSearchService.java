package com.pyg.search.service;

import java.util.List;
import java.util.Map;

public interface ItemSearchService {
    public Map<String,Object> search(Map searchMap);

    //d导入数据
    void importList(List list);
    //同步删除数据
    void deleList(List list);
}
