(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('DomainDialogController', DomainDialogController);

    DomainDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Domain', 'Doctor'];

    function DomainDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Domain, Doctor) {
        var vm = this;

        vm.domain = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.doctors = Doctor.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.domain.id !== null) {
                Domain.update(vm.domain, onSaveSuccess, onSaveError);
            } else {
                Domain.save(vm.domain, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eheartApp:domainUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
