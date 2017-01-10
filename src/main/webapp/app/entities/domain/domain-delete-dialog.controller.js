(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('DomainDeleteController',DomainDeleteController);

    DomainDeleteController.$inject = ['$uibModalInstance', 'entity', 'Domain'];

    function DomainDeleteController($uibModalInstance, entity, Domain) {
        var vm = this;

        vm.domain = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Domain.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
