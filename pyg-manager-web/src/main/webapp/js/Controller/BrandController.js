//前端控制器
app.controller('brandController', function ($scope,brandService) {
    $scope.findAll = function () {
        brandService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        )
    };
    //分页控件配置currentPage:当前页   totalItems :总记录数  itemsPerPage:每页记录数
    // perPageOptions :分页选项  onChange:当页码变更后自动触发的方法
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 8,
        perPageOptions: [8,16,24,32,40],
        onChange: function () {
            $scope.reloadList();
        }
    };

    //刷新列表
    $scope.reloadList = function () {
        $scope.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
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
    //选中集合
    $scope.selectList=[];
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
    $scope.searchEntity={};
    $scope.findPage=function (page,size) {
        brandService.findPage(page,size,$scope.searchEntity).success(
            function (response) {
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.page;
            }
        )
    }
});