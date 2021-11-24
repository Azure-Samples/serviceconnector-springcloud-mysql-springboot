/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
angular.module('todoApp')
    .controller('todoListCtrl', ['$scope', '$location', 'todoListSvc', function ($scope, $location, todoListSvc) {
        $scope.error = '';
        $scope.loadingMessage = '';
        $scope.todoList = null;
        $scope.editingInProgress = false;
        $scope.newTodoCaption = 'add new todo item';

        $scope.editInProgressTodo = {
            description: '',
            id: 0,
            done: false
        };

        $scope.populate = function () {
            todoListSvc.getItems().success(function (results) {
                $scope.todoList = results;
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };
        
        $scope.add = function () {

            todoListSvc.postItem({
                'description': $scope.newTodoCaption,
                'details': $scope.newTodoCaption
            }).success(function (results) {
                $scope.newTodoCaption = '';
                $scope.populate();
                $scope.loadingMessage = results;
                $scope.error = '';
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMsg = '';
            })
        };
    }]);
