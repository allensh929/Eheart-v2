(function() {
    'use strict';

    angular
        .module('eheartApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('title', {
            parent: 'entity',
            url: '/title?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eheartApp.title.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/title/titles.html',
                    controller: 'TitleController',
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
                    $translatePartialLoader.addPart('title');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('title-detail', {
            parent: 'entity',
            url: '/title/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'eheartApp.title.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/title/title-detail.html',
                    controller: 'TitleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('title');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Title', function($stateParams, Title) {
                    return Title.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'title',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('title-detail.edit', {
            parent: 'title-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/title/title-dialog.html',
                    controller: 'TitleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Title', function(Title) {
                            return Title.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('title.new', {
            parent: 'title',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/title/title-dialog.html',
                    controller: 'TitleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                titlePlaceholder1: null,
                                titlePlaceholder2: null,
                                createdDate: null,
                                createdBy: null,
                                lastModifiedDate: null,
                                lastModifiedBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('title', null, { reload: 'title' });
                }, function() {
                    $state.go('title');
                });
            }]
        })
        .state('title.edit', {
            parent: 'title',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/title/title-dialog.html',
                    controller: 'TitleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Title', function(Title) {
                            return Title.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('title', null, { reload: 'title' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('title.delete', {
            parent: 'title',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/title/title-delete-dialog.html',
                    controller: 'TitleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Title', function(Title) {
                            return Title.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('title', null, { reload: 'title' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
