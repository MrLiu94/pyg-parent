//前端控制器
app.controller('brandController', function ($scope,brandService,$controller) {
    //继承BaseController
    $controller('baseController',{$scope:$scope});

    $scope.findAll = function () {
        brandService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        )
    };
    //    新增数据
    $scope.save=function () {
        brandService.save($scope.entity).success(
            function (response) {
                if (response.success){
                    $scope.reloadList();//刷新
                }else {
                    alert(response.message);
                }
            }
        )
    };
    $scope.findOne=function (id) {
        brandService.findOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        )
    }

    //    选中id添加到集合方法
    $scope.selectCheck=function ($event,id) {
        if ($event.target.checked){
            $scope.selectList.push(id);//push向集合添加元素
        }else {
            var index= $scope.selectList.indexOf(id);//查找值的位置
            $scope.selectList.splice(index,1);//参数1：移除的位置 参数2：移除的个数
        }
    }
    //删除
    $scope.dele=function(){
        if(confirm('确定要删除吗？')){
            brandService.dele($scope.selectList).success(
                function(response){
                    if(response.success){
                        $scope.reloadList();//刷新
                    }else{
                        alert(response.message);
                    }
                }
            );
        }

    }
    //查询&搜索
    $scope.findPage=function (page,size) {
        brandService.findPage(page,size,$scope.searchEntity).success(
            function (response) {
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.page;
            }
        )
    }
});