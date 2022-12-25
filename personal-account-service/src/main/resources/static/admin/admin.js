angular.module('account').controller('adminController',function ($scope, $http) {

    var roles = [];
    var emails = [];

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
                    let msg = ''
                    let violations = response.data.violations;
                    if(violations !== null){
                        violations.forEach(v => {
                            msg = msg+v.message+"\n"
                            console.log(msg)
                        })
                    }else {
                        msg = response.data.message
                    }
                        alert(msg)
                    })
        }

        $scope.removeUser = function (id){
            $http.delete('http://localhost:5555/auth/api/v1/secure/'+id)
                .then(function successCallback() {
                        $scope.getAllUsers();
                    })
        }

        $scope.getNotificationList = function (){
            $http.get('http://localhost:5555/notification/api/v1/notification')
                .then(function (response){
                    $scope.notifyList = response.data
                    emails = response.data
            })
        }

        $scope.addToNotificationList = function (){
            emails.push($scope.recipient)
            $http.post('http://localhost:5555/notification/api/v1/notification',emails)
                .then(function successCallback(){
                    $scope.getNotificationList()
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
        $scope.getNotificationList()

});