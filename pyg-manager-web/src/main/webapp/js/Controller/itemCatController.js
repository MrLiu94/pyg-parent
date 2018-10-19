//控制层
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService){

    $controller('baseController',{$scope:$scope});//继承

    $scope.findAll = function () {
        itemCatService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        )
    };
    //    新增数据
    $scope.save=function () {
        itemCatService.save($scope.entity).success(
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
        itemCatService.findOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        )
    }

    //删除
    $scope.dele=function(){
        if(confirm('确定要删除吗？')){
            itemCatService.dele($scope.selectList).success(
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
        itemCatService.findPage(page,size,$scope.searchEntity).success(
            function (response) {
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.page;
            }
        )
    }
    $scope.findByParentId=function (parentId) {
        itemCatService.findParentId(parentId).success(
            function (response) {
                $scope.list=response;
            }
        )


    }
    //设置默认等级为1
    $scope.grade=1;

    //设置级别
    $scope.setGrade=function (value) {

        $scope.grade=value;
    }
    //    读取列表
    $scope.selectList=function (p_entity) {
        if($scope.grade==1){
            $scope.entity_1=null;
            $scope.entity_2=null;
        }
        if($scope.grade==2){
            $scope.entity_1=p_entity;
            $scope.entity_2=null;
        }
        if ($scope.grade==3){
            $scope.entity_2=p_entity;
        }
        $scope.findByParentId(p_entity.id);//查询此级下级列表
    }
    

});	
