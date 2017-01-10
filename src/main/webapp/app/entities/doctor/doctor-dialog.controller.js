(function() {
    'use strict';

    angular
        .module('eheartApp')
        .controller('DoctorDialogController', DoctorDialogController);

    DoctorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Doctor', 'Title', 'Hospital', 'Department', 'Domain', 'Upload', 'Ahdin'];

    function DoctorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Doctor, Title, Hospital, Department, Domain, Upload, Ahdin) {
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
                    vm.doctor.photo = data.file;

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
