(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('HospitalDialogController', HospitalDialogController);

    HospitalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Hospital', 'Department'];

    function HospitalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Hospital, Department) {
        var vm = this;

        vm.hospital = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.departments = Department.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.hospital.id !== null) {
                Hospital.update(vm.hospital, onSaveSuccess, onSaveError);
            } else {
                Hospital.save(vm.hospital, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eheartApp:hospitalUpdate', result);
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
