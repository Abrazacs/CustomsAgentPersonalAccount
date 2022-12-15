angular.module('account').controller('adminController',function ($scope, $http) {

    var roles = [];

        $scope.getAllUsers = function () {
            $http.get('http://localhost:5555/auth/api/v1/secure/users')
                .then(function (response) {
                    $scope.usersList = response.data;
                });
        }

        $scope.addUser = function () {
            $scope.entrant.rolesNames = roles;
            $http.post('http://localhost:5555/auth/api/v1/secure/register', $scope.entrant)
                .then(function successCallback(response) {
                        $scope.getAllUsers();
                    },
                    function errorCallback(response) {
                    alert("заполните все поля и выберите роли")
                    })
        }

        $scope.removeUser = function (id){
            $http.delete('http://localhost:5555/auth/api/v1/secure/'+id)
                .then(function successCallback(response) {
                        $scope.getAllUsers();
                    })
        }

        $scope.collectTheRoles = function (role){
            if(roles.length !== 0){
                let repeat = false;
                for (let i = 0; i < roles.length; i++) {
                    if(roles[i] === role){
                        delete roles[i];
                        repeat = true;
                        break;
                    }
                }
                if(!repeat){
                    roles.push(role);
                }
            }else {
                roles.push(role)
            }
        }

        $scope.getAllUsers()

});