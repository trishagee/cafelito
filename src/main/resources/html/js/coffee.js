var coffeeApp = angular.module('coffeeApp', ['ngResource', 'ui.bootstrap']);

coffeeApp.service('LocalCoffeeShop', function () {
    var localCoffeeShop;

    this.setShop = function (shop) {
        localCoffeeShop = shop;
    };

    this.getShop = function () {
        return localCoffeeShop;
    }
});

coffeeApp.controller('CoffeeShopController', function ($scope, $window, $resource, LocalCoffeeShop) {
    var CoffeeShopLocator = $resource('/service/coffeeshop/nearest/:latitude/:longitude',
        {latitude: '@latitude', longitude: '@longitude'}, {});

    $scope.findCoffeeShopNearestToMe = function () {
        window.navigator.geolocation.getCurrentPosition(function (position) {
            CoffeeShopLocator.get({latitude: position.coords.latitude, longitude: position.coords.longitude},
                function (value) {
                    $scope.nearestCoffeeShop = value;
                    LocalCoffeeShop.setShop(value);
                });
        });
    };
    $scope.findCoffeeShopNearestToMe();
});

coffeeApp.controller('OrderController', function ($scope, $resource, LocalCoffeeShop) {
    $scope.types = [
        {name: 'Americano', family: 'Coffee'},
        {name: 'Latte', family: 'Coffee'},
        {name: 'Tea', family: 'that other drink'},
        {name: 'Cappuccino', family: 'Coffee'}
    ];
    $scope.sizes = ['Small', 'Medium', 'Large'];
    $scope.availableOptions = [
        {name: 'soy milk', appliesTo: 'milk'} ,
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
    ];

    $scope.messages = [];

    $scope.giveMeCoffee = function () {
        $scope.drink.coffeeShopId = LocalCoffeeShop.getShop().openStreetMapId;
        var CoffeeOrder = $resource('/service/coffeeshop/order/');
        CoffeeOrder.save($scope.drink, function (order) {
                $scope.messages.push({type: 'success', msg: 'Order sent! Order ID: ' + order.id})
            }
        );
    };

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


});
