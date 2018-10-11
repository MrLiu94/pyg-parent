//服务层
app.service('specificationOptionService',function($http){

    //读取列表数据绑定到表单中
    this.findAll = function () {
        return $http.get('../specificationOption/findAll');
    }
    //存储&修改
    this.save = function (entity) {
        return $http.post('../specificationOption/save', entity);
    }

    //查询实体
    this.findOne = function (id) {
        return $http.get('../specificationOption/findOne?id=' + id);
    }

    //删除
    this.dele = function (ids) {
        return $http.get('../specificationOption/delete?ids=' + ids);
    }


    //分页 查询
    this.findPage = function (page, size, searchEntity) {
        return $http.post('../specificationOption/search?page=' + page + '&size=' + size, searchEntity);
    }  	
});
