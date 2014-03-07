var coffeeApp = angular.module('coffeeApp', ['ngResource', 'ui.bootstrap']);

coffeeApp.factory('CoffeeOrder', function ($resource) {
    return $resource('/service/coffeeshop/:id/order/',
        {id: '@coffeeShopId'}, {}
    );
});

coffeeApp.controller('DrinksController', function ($scope, CoffeeOrder) {
    $scope.types = [
        {name: 'Americano', family: 'Coffee'},
        {name: 'Latte', family: 'Coffee'},
        {name: 'Tea', family: 'that other drink'},
        {name: 'Cappuccino', family: 'Coffee'}
    ]
    $scope.sizes = ['Small', 'Medium', 'Large']
    $scope.availableOptions = [
        {name: 'soy', appliesTo: 'milk'} ,
        {name: 'skimmed', appliesTo: 'milk'},
        {name: 'caramel', appliesTo: 'syrup'},
        {name: 'decaf', appliesTo: 'caffeine'},
        {name: 'whipped Cream', appliesTo: 'extras'},
        {name: 'vanilla', appliesTo: 'syrup'},
        {name: 'hazelnut', appliesTo: 'syrup'},
        {name: 'sugar free', appliesTo: 'syrup'},
        {name: 'non fat', appliesTo: 'milk'},
        {name: 'half fat', appliesTo: 'milk'},
        {name: 'half and half', appliesTo: 'milk'},
        {name: 'half caf', appliesTo: 'caffeine'},
        {name: 'chocolate powder', appliesTo: 'extras'},
        {name: 'double shot', appliesTo: 'preparation'},
        {name: 'wet', appliesTo: 'preparation'},
        {name: 'dry', appliesTo: 'preparation'},
        {name: 'organic', appliesTo: 'milk'},
        {name: 'extra hot', appliesTo: 'preparation'}
    ]

    $scope.messages = [];

    $scope.giveMeCoffee = function () {
        CoffeeOrder.save({id:1}, $scope.drink,
            function (order) {
                $scope.messages.push({type: 'success', msg: 'Order sent!', orderId: order.id})
            }
        )
    }
    $scope.closeAlert = function (index) {
        $scope.messages.splice(index, 1);
    };
    $scope.addOption = function () {
        if (!$scope.drink.selectedOptions) {
            $scope.drink.selectedOptions = [];
        }
        $scope.drink.selectedOptions.push($scope.newOption);
        $scope.newOption = '';
    };

})
