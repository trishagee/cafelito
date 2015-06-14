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
                function (foundCoffeeShop) {
                    $scope.nearestCoffeeShop = foundCoffeeShop;
                    LocalCoffeeShop.setShop(foundCoffeeShop);
                });
        });
    };
    $scope.findCoffeeShopNearestToMe();
});

coffeeApp.controller('OrderController', function ($scope, $resource, LocalCoffeeShop) {
    $scope.types = [
        {name: 'Cappuccino', family: 'coffee'},
        {name: 'Latte', family: 'coffee'},
        {name: 'Tea', family: 'that other drink'},
        {name: 'Espresso', family: 'coffee'}
    ];
    $scope.sizes = ['Small', 'Medium', 'Large'];

    $scope.giveMeCoffee = function() {
        $scope.drink.coffeeShopId = LocalCoffeeShop.getShop().openStreetMapId;
        var CoffeeOrder = $resource('/service/coffeeshop/order/');
        $scope.messages = [];

        CoffeeOrder.save($scope.drink, function (order) {
            $scope.messages.push({type: 'success', msg: 'Order sent!'})
        });
    };

    $scope.closeAlert = function (index) {
        $scope.messages.splice(index, 1);
    };
});
