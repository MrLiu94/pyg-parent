 //控制层 
app.controller('typeTemplateController' ,function($scope,$controller   ,typeTemplateService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        typeTemplateService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        )
    };
    //    新增数据
    $scope.save=function () {
        typeTemplateService.save($scope.entity).success(
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
        typeTemplateService.findOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        )
    }

    //删除
    $scope.dele=function(){
        if(confirm('确定要删除吗？')){
            typeTemplateService.dele($scope.selectList).success(
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
        typeTemplateService.findPage(page,size,$scope.searchEntity).success(
            function (response) {
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.page;
            }
        )
    }

});	
