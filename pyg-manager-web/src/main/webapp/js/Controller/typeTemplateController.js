 //控制层 
app.controller('typeTemplateController' ,function($scope,$controller,typeTemplateService,brandService,specificationService){
	
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
                $scope.entity.brandIds=JSON.parse($scope.entity.brandIds);//转换品牌数据
                $scope.entity.specIds=JSON.parse($scope.entity.specIds);//转换规格数据
                $scope.entity.customAttributeItems=JSON.parse($scope.entity.customAttributeItems);//转换扩展属性
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

//    品牌列表数据
    $scope.brandList={data:[]};

    $scope.findBrandList=function () {
        brandService.selectOptionList().success(
            function (response) {
                $scope.brandList={data:response};//品牌列表

            }
        )
    }
    //规格数据
    $scope.specList={data:[]};

    $scope.findSpecList=function () {
        specificationService.selectOptionList().success(
            function (response) {
                $scope.specList={data:response};
            }
        )

    }
    //新增拓展属性行
    $scope.addTableRow=function () {
        $scope.entity.customAttributeItems.push({});

    }
    //删除拓展属性行
    $scope.deleTableRow=function (index) {
        $scope.entity.customAttributeItems.splice(index,1)//删除
    }
});	
