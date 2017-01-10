(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('DoctorDialogController', DoctorDialogController);

    DoctorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Doctor', 'Title', 'Hospital', 'Department', 'Domain'];

    function DoctorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Doctor, Title, Hospital, Department, Domain) {
        var vm = this;

        vm.doctor = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.titles = Title.query();
        vm.hospitals = Hospital.query();
        vm.departments = Department.query();
        vm.domains = Domain.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.doctor.id !== null) {
                Doctor.update(vm.doctor, onSaveSuccess, onSaveError);
            } else {
                Doctor.save(vm.doctor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eheartApp:doctorUpdate', result);
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
