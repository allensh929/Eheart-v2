(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('ClinicDetailController', ClinicDetailController);

    ClinicDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Clinic', 'Department', 'Hospital'];

    function ClinicDetailController($scope, $rootScope, $stateParams, previousState, entity, Clinic, Department, Hospital) {
        var vm = this;

        vm.clinic = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eheartApp:clinicUpdate', function(event, result) {
            vm.clinic = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
