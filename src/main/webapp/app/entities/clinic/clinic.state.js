(function() {
    'use strict';

    angular
        .module('eheartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('clinic', {
            parent: 'entity',
            url: '/clinic?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eheartApp.clinic.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/clinic/clinics.html',
                    controller: 'ClinicController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('clinic');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('clinic-detail', {
            parent: 'entity',
            url: '/clinic/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eheartApp.clinic.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/clinic/clinic-detail.html',
                    controller: 'ClinicDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('clinic');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Clinic', function($stateParams, Clinic) {
                    return Clinic.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'clinic',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('clinic-detail.edit', {
            parent: 'clinic-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clinic/clinic-dialog.html',
                    controller: 'ClinicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Clinic', function(Clinic) {
                            return Clinic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('clinic.new', {
            parent: 'clinic',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clinic/clinic-dialog.html',
                    controller: 'ClinicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                img: null,
                                createdDate: null,
                                createdBy: null,
                                lastModifiedDate: null,
                                lastModifiedBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('clinic', null, { reload: 'clinic' });
                }, function() {
                    $state.go('clinic');
                });
            }]
        })
        .state('clinic.edit', {
            parent: 'clinic',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clinic/clinic-dialog.html',
                    controller: 'ClinicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Clinic', function(Clinic) {
                            return Clinic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('clinic', null, { reload: 'clinic' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('clinic.delete', {
            parent: 'clinic',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clinic/clinic-delete-dialog.html',
                    controller: 'ClinicDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Clinic', function(Clinic) {
                            return Clinic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('clinic', null, { reload: 'clinic' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
