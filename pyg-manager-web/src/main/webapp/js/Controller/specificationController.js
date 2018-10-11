 //控制层 
app.controller('specificationController' ,function($scope,$controller   ,specificationService){	
	
	$controller('baseController',{$scope:$scope});//继承

    $scope.findAll = function () {
        specificationService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        )
    };
    //    新增数据
    $scope.save=function () {
        specificationService.save($scope.entity).success(
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
        specificationService.findOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        )
    }

    //删除
    $scope.dele=function(){
        if(confirm('确定要删除吗？')){
            specificationService.dele($scope.selectList).success(
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
        specificationService.findPage(page,size,$scope.searchEntity).success(
            function (response) {
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.page;
            }
        )
    }
    
});	
