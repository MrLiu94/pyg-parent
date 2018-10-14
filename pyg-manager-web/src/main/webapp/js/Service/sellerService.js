//服务层
app.service('sellerService', function ($http) {

    //读取列表数据绑定到表单中
    this.findAll = function () {
        return $http.get('../seller/findAll');
    }
    //存储&修改
    this.save = function (entity) {
        return $http.post('../seller/save', entity);
    }

    //查询实体
    this.findOne = function (id) {
        return $http.get('../seller/findOne?id=' + id);
    }

    //删除
    this.dele = function (ids) {
        return $http.get('../seller/delete?ids=' + ids);
    }


    //分页 查询
    this.findPage = function (page, size, searchEntity) {
        return $http.post('../seller/search?page=' + page + '&size=' + size, searchEntity);
    }

//    更新状态
    this.updateStatus = function (sellerId, status) {
        return $http.get('../seller/updateStatus?sellerId='+sellerId+'&status='+status);
    }
});
