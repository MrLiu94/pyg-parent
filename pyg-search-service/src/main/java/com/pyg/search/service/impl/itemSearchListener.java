package com.pyg.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.pyg.pojo.TbItem;
import com.pyg.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;
import java.util.Map;

@Component
public class itemSearchListener implements MessageListener {
    @Autowired
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(Message message) {
        System.out.println("监听到消息:");
        TextMessage textMessage= (TextMessage) message;
        try {
            String text = textMessage.getText();
            List<TbItem> tbItems = JSON.parseArray(text, TbItem.class);
            for (TbItem item : tbItems) {
                System.out.println(item.getId()+" "+item.getTitle());
                Map specMap = JSON.parseObject(item.getSpec());//将spec中的字段转换成map
                item.setSpecMap(specMap);//给带注解的字段赋值
            }
            itemSearchService.importList(tbItems);
            System.out.println("导入solr成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
