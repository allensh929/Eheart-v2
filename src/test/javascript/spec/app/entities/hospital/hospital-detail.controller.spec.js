'use strict';

describe('Controller Tests', function() {

    describe('Hospital Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockHospital, MockDepartment, MockClinic;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockHospital = jasmine.createSpy('MockHospital');
            MockDepartment = jasmine.createSpy('MockDepartment');
            MockClinic = jasmine.createSpy('MockClinic');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Hospital': MockHospital,
                'Department': MockDepartment,
                'Clinic': MockClinic
            };
            createController = function() {
                $injector.get('$controller')("HospitalDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'eheartApp:hospitalUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
