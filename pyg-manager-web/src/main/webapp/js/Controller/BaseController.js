app.controller('baseController', function ($scope) {
    //分页控件配置currentPage:当前页   totalItems :总记录数  itemsPerPage:每页记录数
    // perPageOptions :分页选项  onChange:当页码变更后自动触发的方法
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 8,
        perPageOptions: [8, 16, 24, 32, 40],
        onChange: function () {
            $scope.reloadList();
        }
    };


    //选中集合
    $scope.selectIds = [];
    //    选中id添加到集合方法
    $scope.selectCheck=function ($event,id) {
        if ($event.target.checked){
            $scope.selectIds.push(id);//push向集合添加元素
        }else {
            var index= $scope.selectIds.indexOf(id);//查找值的位置
            $scope.selectIds.splice(index,1);//参数1：移除的位置 参数2：移除的个数
        }
    }

    //刷新列表
    $scope.reloadList = function () {
        $scope.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };


//  json转换成string 友好显示
    $scope.jsonToString=function (jsonString,key) {
        var json=JSON.parse(jsonString);//将json字符串转换成json对象
        var value="";
        for (i=0;i<json.length;i++){
            if (i>0){
                value+=","
            }
            value+=json[i][key];
        }
        return value;
    }
    //    提交的查询元素
    $scope.searchEntity = {};
    //在list中根据某key查询对象
    $scope.searchObjectByKey= function (list,key,keyValue) {
        for (var i=0;i<list.length;i++){
            if ( list[i][key]==keyValue){
                return list[i];
            }
        }
        return null;


    }
});