(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('SubMenuDeleteController',SubMenuDeleteController);

    SubMenuDeleteController.$inject = ['$uibModalInstance', 'entity', 'SubMenu'];

    function SubMenuDeleteController($uibModalInstance, entity, SubMenu) {
        var vm = this;

        vm.subMenu = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SubMenu.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
