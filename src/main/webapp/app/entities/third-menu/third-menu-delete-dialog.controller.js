(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('ThirdMenuDeleteController',ThirdMenuDeleteController);

    ThirdMenuDeleteController.$inject = ['$uibModalInstance', 'entity', 'ThirdMenu'];

    function ThirdMenuDeleteController($uibModalInstance, entity, ThirdMenu) {
        var vm = this;

        vm.thirdMenu = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ThirdMenu.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
