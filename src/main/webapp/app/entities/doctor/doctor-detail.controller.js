(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('DoctorDetailController', DoctorDetailController);

    DoctorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Doctor', 'Title', 'Hospital', 'Department', 'Domain'];

    function DoctorDetailController($scope, $rootScope, $stateParams, previousState, entity, Doctor, Title, Hospital, Department, Domain) {
        var vm = this;

        vm.doctor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eheartApp:doctorUpdate', function(event, result) {
            vm.doctor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
