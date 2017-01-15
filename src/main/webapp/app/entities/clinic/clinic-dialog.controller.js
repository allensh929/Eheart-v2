(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('ClinicDialogController', ClinicDialogController);

    ClinicDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Clinic', 'Department', 'Hospital'];

    function ClinicDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Clinic, Department, Hospital) {
        var vm = this;

        vm.clinic = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.departments = Department.query();
        vm.hospitals = Hospital.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.clinic.id !== null) {
                Clinic.update(vm.clinic, onSaveSuccess, onSaveError);
            } else {
                Clinic.save(vm.clinic, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eheartApp:clinicUpdate', result);
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
