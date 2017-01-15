(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('ClinicDeleteController',ClinicDeleteController);

    ClinicDeleteController.$inject = ['$uibModalInstance', 'entity', 'Clinic'];

    function ClinicDeleteController($uibModalInstance, entity, Clinic) {
        var vm = this;

        vm.clinic = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Clinic.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
