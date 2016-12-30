(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('EheartDialogController', EheartDialogController);

    EheartDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Eheart'];

    function EheartDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Eheart) {
        var vm = this;

        vm.eheart = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.eheart.id !== null) {
                Eheart.update(vm.eheart, onSaveSuccess, onSaveError);
            } else {
                Eheart.save(vm.eheart, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eheartApp:eheartUpdate', result);
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
