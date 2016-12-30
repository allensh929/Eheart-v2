(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('ThirdMenuDetailController', ThirdMenuDetailController);

    ThirdMenuDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ThirdMenu', 'SubMenu'];

    function ThirdMenuDetailController($scope, $rootScope, $stateParams, previousState, entity, ThirdMenu, SubMenu) {
        var vm = this;

        vm.thirdMenu = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eheartApp:thirdMenuUpdate', function(event, result) {
            vm.thirdMenu = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
