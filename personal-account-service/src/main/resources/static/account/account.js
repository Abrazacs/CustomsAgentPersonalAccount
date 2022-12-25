angular
    .module('account')
    .controller('accountController', function ($scope, $http) {

    $scope.loadDeclarations = function (page = 1) {
        $http({
            url: 'http://localhost:5555/core/api/v1/declarations',
            method: 'GET',
            params: {
                page: page,
                numberPart: $scope.filter ? $scope.filter.numberPart : null
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

    $scope.download =function () {
        $http({
            method: 'GET',
            url: 'http://localhost:5555/core/api/v1/declarations/export',
            responseType: 'arraybuffer'
        }) .then(function(response){
            console.log(response.data);
            var blob = new Blob([response.data], {type: 'application'});
            saveAs(blob, 'statistic.xlsx');
        })

    }

    function saveAs(blob, fileName){
        var url = window.URL.createObjectURL(blob);
        var doc = document.createElement("a");
        doc.href = url;
        doc.download = fileName;
        doc.click();
        window.URL.revokeObjectURL(url);
    }


    $scope.refresh = function (){
        $scope.filter = null;
        $scope.loadDeclarations()
        $scope.loadStatistic()
    }

    $scope.loadDeclarations()
    $scope.loadStatistic()

});
