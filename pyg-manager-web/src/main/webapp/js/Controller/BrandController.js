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
        brandService.findPage(page,size).success(
            function (response) {
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.page;
            }
        )
    }
    //查询&搜索
    $scope.search=function (page,size) {
        brandService.searchPage(page,size,$scope.searchEntity).success(
            function (response) {
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.page;
            }
        )
    }
});