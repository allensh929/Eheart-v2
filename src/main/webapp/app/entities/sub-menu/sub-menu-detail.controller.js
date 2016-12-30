(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('SubMenuDetailController', SubMenuDetailController);

    SubMenuDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SubMenu', 'ThirdMenu', 'Menu'];

    function SubMenuDetailController($scope, $rootScope, $stateParams, previousState, entity, SubMenu, ThirdMenu, Menu) {
        var vm = this;

        vm.subMenu = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('eheartApp:subMenuUpdate', function(event, result) {
            vm.subMenu = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
