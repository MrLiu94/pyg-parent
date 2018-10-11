//服务层
app.service('specificationService',function($http){

    //读取列表数据绑定到表单中
    this.findAll = function () {
        return $http.get('../specification/findAll');
    }
    //存储&修改
    this.save = function (entity) {
        return $http.post('../specification/save', entity);
    }

    //查询实体
    this.findOne = function (id) {
        return $http.get('../specification/findOne?id=' + id);
    }

    //删除
    this.dele = function (ids) {
        return $http.get('../specification/delete?ids=' + ids);
    }


    //分页 查询
    this.findPage = function (page, size, searchEntity) {
        return $http.post('../specification/search?page=' + page + '&size=' + size, searchEntity);
    }  	
});
