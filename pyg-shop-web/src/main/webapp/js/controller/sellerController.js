 //控制层 
app.controller('sellerController' ,function($scope,$controller ,sellerService){
	
	$controller('baseController',{$scope:$scope});//继承

	$scope.findAll = function () {
        sellerService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        )
    };
    //    新增数据
    $scope.save=function () {
        sellerService.save($scope.entity).success(
            function (response) {
                if (response.success){
                    location.href="shoplogin.html";
                }else {
                    alert(response.message);
                }
            }
        )
    };
    $scope.findOne=function (id) {
        sellerService.findOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        )
    }

    //删除
    $scope.dele=function(){
        if(confirm('确定要删除吗？')){
            sellerService.dele($scope.selectList).success(
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
        sellerService.findPage(page,size,$scope.searchEntity).success(
            function (response) {
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.page;
            }
        )
    }
    
});	
