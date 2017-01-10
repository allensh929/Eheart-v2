'use strict';

describe('Controller Tests', function() {

    describe('Doctor Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDoctor, MockTitle, MockHospital, MockDepartment, MockDomain;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDoctor = jasmine.createSpy('MockDoctor');
            MockTitle = jasmine.createSpy('MockTitle');
            MockHospital = jasmine.createSpy('MockHospital');
            MockDepartment = jasmine.createSpy('MockDepartment');
            MockDomain = jasmine.createSpy('MockDomain');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Doctor': MockDoctor,
                'Title': MockTitle,
                'Hospital': MockHospital,
                'Department': MockDepartment,
                'Domain': MockDomain
            };
            createController = function() {
                $injector.get('$controller')("DoctorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'eheartApp:doctorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
