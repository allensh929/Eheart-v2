(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('HospitalDetailController', HospitalDetailController);

    HospitalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Hospital', 'Department', 'Clinic'];

    function HospitalDetailController($scope, $rootScope, $stateParams, previousState, entity, Hospital, Department, Clinic) {
        var vm = this;

        vm.hospital = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eheartApp:hospitalUpdate', function(event, result) {
            vm.hospital = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
