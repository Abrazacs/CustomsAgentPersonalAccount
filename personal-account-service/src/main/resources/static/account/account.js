angular
    .module('account')
    .controller('accountController', function ($scope, $http) {

    $scope.loadDeclarations = function (page = 1) {
        $http({
            url: 'http://localhost:5555/core/api/v1/declarations',
            method: 'GET',
            params: {
                page: page,
            }
        }).then(function (response) {
            $scope.declarationPage = response.data;
            $scope.generatePagesList($scope.declarationPage.totalPages);
        });
    }

    $scope.loadStatistic = function () {
        $http.get('http://localhost:5555/core/api/v1/declarations/statistic')
        .then(function (response) {
            $scope.statistic = response.data;
        });
    }

    $scope.generatePagesList = function (totalPages) {
        out = [];
        for (let i = 0; i < totalPages; i++) {
            out.push(i + 1);
        }
        $scope.pagesList = out;
    }

    // $scope.download =function (){
    //     $http.get('http://localhost:5555/core/api/v1/declarations/export')
    //         .then(function (result){
    //
    //     });
    // }

    $scope.refresh = function (){
        $scope.loadDeclarations()
        $scope.loadStatistic()
    }

    $scope.loadDeclarations()
    $scope.loadStatistic()

});
