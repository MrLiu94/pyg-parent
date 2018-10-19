//控制层
app.controller('goodsController', function ($scope, $controller, $location, goodsService, itemCatService, typeTemplateService) {

    $controller('baseController', {$scope: $scope});//继承

    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        )
    };
    //    新增数据
    $scope.save = function () {
        $scope.entity.goodsDesc.introduction = editor.html();
        var serviceObject;//服务层对象
        if ($scope.entity.goods.id!=null){
            serviceObject=goodsService.update($scope.entity);
        }else {
            serviceObject=goodsService.add($scope.entity);
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    alert(response.message)
                    location.href="goods.html";//跳转到商品列表页
                } else {
                    alert(response.message);
                }
            }
        )
    };
    // $scope.findOne = function (id) {
    //     goodsService.findOne(id).success(
    //         function (response) {
    //             $scope.entity = response;
    //         }
    //     )
    // }


    //删除
    $scope.dele = function () {
        if (confirm('确定要删除吗？')) {
            goodsService.dele($scope.selectList).success(
                function (response) {
                    if (response.success) {
                        $scope.reloadList();//刷新
                    } else {
                        alert(response.message);
                    }
                }
            );
        }

    }
    //查询&搜索
    $scope.findPage = function (page, size) {
        goodsService.findPage(page, size, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.page;
            }
        )
    }
    // //文件上传
    // $scope.uploadFile = function () {
    //     uploadService.uploadFile().success(
    //         function (response) {
    //             if (response.success) {//如果上传成功，取出 url
    //                 $scope.image_entity.url = response.message;//设置文件地址
    //             } else {
    //                 alert(response.message);
    //             }
    //         })
    // };


    $scope.entity = {goodsDesc: {itemImages: [], specificationItems: []}};//定义页面实体结构

    //将当前上传的图片实体存入图片列表
    $scope.add_image_entity = function () {

        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);

        $scope.dd = $scope.entity.goodsDesc.itemImages;

    }

    //移除图片
    $scope.remove_image_entity = function (index) {
        $scope.entity.goodsDesc.itemImages.splice(index, 1);
    }


    //读取一级分类
    $scope.selectItemCat1List = function () {
        itemCatService.findParentId(0).success(
            function (response) {
                $scope.itemCat1List = response;
            }
        )

    }
    //读取二级分类
    $scope.$watch('entity.goods.category1Id', function (newValue, oldValue) {
        itemCatService.findParentId(newValue).success(
            function (response) {
                $scope.itemCat2List = response;
            }
        )

    })
    //读取三级分类
    $scope.$watch('entity.goods.category2Id', function (newValue, oldValue) {
        itemCatService.findParentId(newValue).success(
            function (response) {
                $scope.itemCat3List = response;
            }
        )

    });
    //读取模板
    $scope.$watch('entity.goods.category3Id', function (newValue, oldValue) {
        itemCatService.findOne(newValue).success(
            function (response) {
                $scope.entity.goods.typeTemplateId = response.typeId;

            }
        )
    });
    //读取模板后更新模板对象
    $scope.$watch('entity.goods.typeTemplateId', function (newValue, oldValue) {
        typeTemplateService.findOne(newValue).success(
            function (response) {
                $scope.typeTemplate = response;//获取类型模板
                $scope.typeTemplate.brandIds =
                    JSON.parse($scope.typeTemplate.brandIds);//品牌列表
                if ($location.search()['id'] == null) {
                    $scope.entity.goodsDesc.customAttributeItems =
                        JSON.parse($scope.typeTemplate.customAttributeItems);//扩展属性
                }
            }
        );
        //查询规格列表
        typeTemplateService.findSpecList(newValue).success(
            function (response) {
                $scope.specList = response;
            }
        )

    });

    //添加选中规格元素

    $scope.updateSpecAttribute = function ($event, name, value) {
        var object = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems, 'attributeName', name);
        if (object != null) {
            if ($event.target.checked) {
                object.attributeValue.push(value);
            } else {//取消勾选
                object.attributeValue.splice(object.attributeValue.indexOf(value), 1);//移除选中
                //如果选项都取消了，将此条记录移除
                if (object.attributeValue.length == 0) {
                    $scope.entity.goodsDesc.specificationItems.splice(
                        $scope.entity.goodsDesc.specificationItems.indexOf(object), 1);//移除记录
                }
            }

        } else {
            $scope.entity.goodsDesc.specificationItems.push(
                {"attributeName": name, "attributeValue": [value]});
        }

    }

    //创建 SKU 列表

    $scope.createItemList = function () {
        $scope.entity.itemList = [{spec: {}, price: 0, num: 9999, status: 0, isDefault: '0'}];//初始化

        var items = $scope.entity.goodsDesc.specificationItems;

        for (var i = 0; i < items.length; i++) {

            $scope.entity.itemList = addColumn($scope.entity.itemList, items[i].attributeName, items[i].attributeValue);

        }


    }
    //添加新列
    addColumn = function (list, columnName, columnValues) {
        var newList = [];//新的集合

        for (var i = 0; i < list.length; i++) {
            var oldRow = list[i];
            for (var j = 0; j < columnValues.length; j++) {
                var newRow = JSON.parse(JSON.stringify(oldRow));//深克隆
                newRow.spec[columnName] = columnValues[j];
                newList.push(newRow);
            }
        }
        return newList;

    }
    //状态的显示
    $scope.status = ['未审核', '已审核', '审核未通过', '关闭'];//商品状态

    //商品分类列表
    $scope.itemCatList = [];

    //加载商品分类列表
    $scope.findItemCatList = function () {
        itemCatService.findAll().success(
            function (response) {
                for (var i = 0; i < response.length; i++) {
                    $scope.itemCatList[response[i].id] = response[i].name;
                }
            }
        )
    }
    //查询实体
    $scope.findOne = function () {
        var id = $location.search()['id'];//获取参数值
        if (id == null) {
            return;
        }
        goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
                editor.html($scope.entity.goodsDesc.introduction);//获得富文本编辑框数据
                $scope.entity.goodsDesc.itemImages = JSON.parse($scope.entity.goodsDesc.itemImages);//装换为json,得到图片详情
                $scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.entity.goodsDesc.customAttributeItems);//获取拓展属性
                $scope.entity.goodsDesc.specificationItems = JSON.parse($scope.entity.goodsDesc.specificationItems);//获取规格
                //遍历获得的sku数据
                for (var i=0;i<$scope.entity.itemList.length;i++){
                    $scope.entity.itemList[i].spec=JSON.parse($scope.entity.itemList[i].spec);
                }
            }
        )

    }

    //根据获得的值判断是否被勾选
    $scope.checkAttributeValue = function (specName, optionName) {
        var items = $scope.entity.goodsDesc.specificationItems;

        var object = $scope.searchObjectByKey(items, 'attributeName', specName);
        if (object == null) {//判断对象中是否包含规格类名
            return false;
        } else {
            if (object.attributeValue.indexOf(optionName) >= 0) {//判断传回的OptionName是否存在与object对象中(是否勾选)
                return true;
            } else {
                return false;
            }
        }

    }
    //更新状态
    $scope.updateStatus=function (status) {
        goodsService.updateStatus($scope.selectIds,status).success(
            function (response) {
                if (response.success){
                    $scope.reloadList();//刷新
                    $scope.selectIds=[];//清空选择
                }else{
                    alert(response.message);
                }
            }
        )
    }


});	
