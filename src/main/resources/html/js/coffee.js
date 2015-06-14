var coffeeApp = angular.module('coffeeApp', []);

coffeeApp.controller('OrderController', function ($scope) {
    $scope.types = [
        {name: 'Cappuccino', family: 'coffee'},
        {name: 'Latte', family: 'coffee'},
        {name: 'Tea', family: 'that other drink'},
        {name: 'Espresso', family: 'coffee'}
    ];
    $scope.sizes = ['Small', 'Medium', 'Large'];
});
