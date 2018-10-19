//服务层
app.service('goodsService', function ($http) {

    //读取列表数据绑定到表单中
    this.findAll = function () {
        return $http.get('../goods/findAll');
    }
    //存储&修改
    this.save = function (entity) {
        return $http.post('../goods/save', entity);
    }

    //查询实体
    this.findOne = function (id) {
        return $http.get('../goods/findOne?id=' + id);
    }

    //删除
    this.dele = function (ids) {
        return $http.get('../goods/delete?ids=' + ids);
    }


    //分页 查询
    this.findPage = function (page, size, searchEntity) {
        return $http.post('../goods/search?page=' + page + '&size=' + size, searchEntity);
    }

    //更新状态
    this.updateStatus=function (ids,status) {
        return $http.get('../goods/updateStatus?ids='+ids+"&status="+status);

    }
});

