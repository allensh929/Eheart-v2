(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('DepartmentDetailController', DepartmentDetailController);

    DepartmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Department', 'Hospital', 'Clinic'];

    function DepartmentDetailController($scope, $rootScope, $stateParams, previousState, entity, Department, Hospital, Clinic) {
        var vm = this;

        vm.department = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eheartApp:departmentUpdate', function(event, result) {
            vm.department = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
