(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('ClinicDialogController', ClinicDialogController);

    ClinicDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Clinic', 'Department', 'Hospital', 'Upload', 'Ahdin'];

    function ClinicDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Clinic, Department, Hospital, Upload, Ahdin) {
        var vm = this;

        vm.clinic = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.departments = Department.query();
        vm.hospitals = Hospital.query();
        vm.onFileSelect = onFileSelect;

        function onFileSelect (uploadFile){

            var uploadImageFile = function(compressedBlob) {
                Upload.upload({

                    url: '/api/upload',
                    fields: {},
                    file: compressedBlob,
                    method: 'POST'

                }).progress(function (evt) {

                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    console.log('progress: ' + progressPercentage + '% ');

                }).success(function (data, status, headers, config) {

                    //update the url
                    vm.clinic.img = data.file;

                }).error(function (data, status, headers, config) {

                    console.log('error status: ' + status);
                });
            };


            //TODO gif no compress
            if (uploadFile != null) {
                Ahdin.compress({
                    sourceFile: uploadFile,
                    maxWidth: 1280,
                    maxHeight:1000,
                    quality: 0.8
                }).then(function(compressedBlob) {
                    console.log('compressed image by ahdin.');
                    uploadImageFile(compressedBlob);
                });
            }


        }

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
