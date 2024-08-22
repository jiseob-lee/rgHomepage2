var myModule = angular.module('myModule', [])
    .directive('myComponent', function () {
        return {
            restrict: 'A',
            scope: {
                isolatedExpressionFoo:'&'
            },
            link: function(scope, element, attr) {
                scope.isolatedExpressionFoo({temp: "some value"});
            }
        };
    })
    .controller('MyCtrl', ['$scope', function ($scope) {        
        $scope.items=[{id: 1, value: "test"},{id: 2, value: "TEst2"}];
        $scope.updateItem = function (item, temp) {
            console.log("Item param " + item.id);
            console.log("Item param " + item.value);
            console.log("temp param " + temp);
        }
    }]);