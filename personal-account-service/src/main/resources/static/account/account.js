angular.module('account').controller('accountController', function ($scope, $http) {

    $scope.loadDeclarations = function (page = 1) {
        $http({
            url: 'http://localhost:5555/core//api/v1/declarations',
            method: 'GET',
            params: {
                page: page,
            }
        }).then(function (response) {
            $scope.declarationPage = response.data;
            $scope.generatePagesList($scope.declarationPage.totalPages);
        });
    };

    $scope.generatePagesList = function (totalPages) {
        out = [];
        for (let i = 0; i < totalPages; i++) {
            out.push(i + 1);
        }
        $scope.pagesList = out;
    }

    $scope.loadDeclarations();

});