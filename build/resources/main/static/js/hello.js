/**
 * Created by haoxu on 5/24/17.
 */

angular.module('hello', [])
    .controller('home', function($scope) {
        $scope.greeting = {id: 'xxx', content: 'Hello World! This is Hao'}
    })