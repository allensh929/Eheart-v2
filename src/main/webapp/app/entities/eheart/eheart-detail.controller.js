(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('EheartDetailController', EheartDetailController);

    EheartDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Eheart'];

    function EheartDetailController($scope, $rootScope, $stateParams, previousState, entity, Eheart) {
        var vm = this;

        vm.eheart = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eheartApp:eheartUpdate', function(event, result) {
            vm.eheart = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
