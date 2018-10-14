app.controller("loginController", function (loginService, $scope, $controller) {

    $scope.showLoginName = function () {
        loginService.loginName().success(
            function (response) {
                $scope.loginName = response.loginName;
            }
        )
    }
})
    
    
