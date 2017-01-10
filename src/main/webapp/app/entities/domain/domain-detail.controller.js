(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('DomainDetailController', DomainDetailController);

    DomainDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Domain', 'Doctor'];

    function DomainDetailController($scope, $rootScope, $stateParams, previousState, entity, Domain, Doctor) {
        var vm = this;

        vm.domain = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eheartApp:domainUpdate', function(event, result) {
            vm.domain = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
