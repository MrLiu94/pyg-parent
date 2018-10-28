package com.pyg.manager.controller;

import PageBean.PageResult;
import ReturnResult.Results;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pyg.pojo.TbGoods;
import com.pyg.pojo.TbItem;
import com.pyg.pojogroup.Goods;
import com.pyg.sellergoods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;

/**
 * controller
 *
 * @author Administrator
 */

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;

    @Autowired
    private Destination queueSolrDestination;//用于发送 solr 导入的消息

    @Autowired
    private Destination queueSolrDeleteDestination;//用于删除solr索引

    @Autowired
    private Destination topicPageDestination;//生成静态页面

    @Autowired
    private Destination topicPageDeleteDestination;//删除静态页面

    @Autowired
    private JmsTemplate jmsTemplate;


//    @Reference
//    private ItemSearchService itemSearchService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbGoods> findAll() {
        return goodsService.findAll();
    }


    /**
     * 增加
     *
     * @param goods
     * @return
     */
    //    数据的更新和增加
    @RequestMapping("/save")
    public Results add(@RequestBody Goods goods) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        goods.getGoods().setSellerId(name);
        try {
            goodsService.add(goods);
            return new Results(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Results(false, "增加失败");
        }
    }

    @RequestMapping("/update")
    public Results update(@RequestBody Goods goods) {
        Goods one = goodsService.findOne(goods.getGoods().getId());
        //获取当前登录的商家ID
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!one.getGoods().getSellerId().equals(sellerId) || !goods.getGoods().getSellerId().equals(sellerId)) {
            return new Results(false, "非法操作");
        }
        try {
            goodsService.update(goods);
            return new Results(true, "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Results(false, "更新失败");
        }


    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public Goods findOne(Long id) {
        return goodsService.findOne(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Results delete(final Long[] ids) {
        try {
            goodsService.delete(ids);
//            itemSearchService.deleList(Arrays.asList(ids));
            jmsTemplate.send(queueSolrDeleteDestination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createObjectMessage(ids);
                }
            });
            //删除页面
            jmsTemplate.send(topicPageDeleteDestination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createObjectMessage(ids);
                }
            });
            return new Results(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Results(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param goods
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbGoods goods, int page, int size) {
        return goodsService.search(goods, page, size);
    }

    @RequestMapping("/updateStatus")
    public Results updateStatus(Long ids[], String status) {
        try {
            goodsService.updateStatus(ids, status);
            if (status.equals("1")) {
                List<TbItem> itemList = goodsService.findItemListByGoodsIdandStatus(ids, status);
                //调用搜索接口实现数据批量导入
                if (itemList.size() > 0) {
//                    itemSearchService.importList(itemList);
                    final String jsonString = JSON.toJSONString(itemList);
                    jmsTemplate.send(queueSolrDestination, new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            return session.createTextMessage(jsonString);
                        }
                    });
                } else {
                    System.out.println("没有明细数据");
                }
                //静态页生成
                for (final Long goodsId : ids) {
//                    itemPageService.genItemHtml(goodsId);
                    jmsTemplate.send(topicPageDestination, new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            return session.createTextMessage(goodsId+"");
                        }
                    });
                }

            }
            return new Results(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Results(false, "操作失败");
        }
    }

//    @Reference(timeout = 40000)
//    private ItemPageService itemPageService;

    /**
     * 生成静态页（测试）
     *
     * @param goodsId
     */
    @RequestMapping("/genHtml")
    public void genHtml(Long goodsId) {
//        itemPageService.genItemHtml(goodsId);
    }

}
