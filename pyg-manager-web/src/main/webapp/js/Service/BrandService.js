//前端服务层
app.service('brandService', function ($http) {
    this.findAll = function () {
        return $http.get('../brand/findAll');
    }
    this.save = function (entity) {
        return $http.post('../brand/save', entity);
    }
    this.findOne = function (id) {
        return $http.get('../brand/findOne?id=' + id);
    }
    this.dele = function (ids) {
        return $http.get('../brand/delete?ids=' + ids);
    }
    this.findPage = function (page, size, searchEntity) {
        return $http.post('../brand/search?page=' + page + '&size=' + size, searchEntity);
    }
    //品牌数据查询
    this.selectOptionList=function () {
        return $http.get('../brand/selectOption');
    }
});