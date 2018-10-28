package com.pyg.search.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pyg.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import java.util.Arrays;

@Component
public class itemDeleteListener implements MessageListener {
    @Autowired
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage =(ObjectMessage)message;
           Long[] goodIds = (Long[]) objectMessage.getObject();
            System.out.println("监听到消息: "+goodIds);
            itemSearchService.deleList(Arrays.asList(goodIds));
            System.out.println("删除索引库中数据");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
