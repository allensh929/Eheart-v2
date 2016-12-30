(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('EheartDeleteController',EheartDeleteController);

    EheartDeleteController.$inject = ['$uibModalInstance', 'entity', 'Eheart'];

    function EheartDeleteController($uibModalInstance, entity, Eheart) {
        var vm = this;

        vm.eheart = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Eheart.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
