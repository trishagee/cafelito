var coffeeApp = angular.module('coffeeApp', ['ngResource', 'ui.bootstrap']);

coffeeApp.controller('OrderController', function ($scope, $resource) {
    $scope.types = [
        {name: 'Americano', family: 'Coffee'},
        {name: 'Latte', family: 'Coffee'},
        {name: 'Tea', family: 'that other drink'},
        {name: 'Cappuccino', family: 'Coffee'}
    ];
    $scope.sizes = ['Small', 'Medium', 'Large'];
    $scope.messages = [];
    
    $scope.giveMeCoffee = function() {
        var coffeeOrder = $resource('/service/coffeeshop/order/');
        $scope.drink.coffeeShopId = 1;
        coffeeOrder.save($scope.drink, function (order) {
                $scope.messages.push({type: 'success', msg: 'Order sent!'})
            }
        )
    };

    $scope.closeAlert = function (index) {
        $scope.messages.splice(index, 1);
    };


});
