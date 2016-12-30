(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('ThirdMenuDialogController', ThirdMenuDialogController);

    ThirdMenuDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ThirdMenu', 'SubMenu'];

    function ThirdMenuDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ThirdMenu, SubMenu) {
        var vm = this;

        vm.thirdMenu = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.submenus = SubMenu.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.thirdMenu.id !== null) {
                ThirdMenu.update(vm.thirdMenu, onSaveSuccess, onSaveError);
            } else {
                ThirdMenu.save(vm.thirdMenu, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('eheartApp:thirdMenuUpdate', result);
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
