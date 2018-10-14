 //控制层 
app.controller('goodsController' ,function($scope,$controller,goodsService){	
	
	$controller('baseController',{$scope:$scope});//继承

    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        )
    };
    //    新增数据
    $scope.save=function () {
        goodsService.save($scope.entity).success(
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
        goodsService.findOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        )
    }


    //删除
    $scope.dele=function(){
        if(confirm('确定要删除吗？')){
            goodsService.dele($scope.selectList).success(
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
        goodsService.findPage(page,size,$scope.searchEntity).success(
            function (response) {
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.page;
            }
        )
    }
    
});	
