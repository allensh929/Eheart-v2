'use strict';

describe('Controller Tests', function() {

    describe('Clinic Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockClinic, MockDepartment, MockHospital;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockClinic = jasmine.createSpy('MockClinic');
            MockDepartment = jasmine.createSpy('MockDepartment');
            MockHospital = jasmine.createSpy('MockHospital');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Clinic': MockClinic,
                'Department': MockDepartment,
                'Hospital': MockHospital
            };
            createController = function() {
                $injector.get('$controller')("ClinicDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'eheartApp:clinicUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
