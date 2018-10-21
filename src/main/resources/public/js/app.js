angular.module('app', ['ngRoute']);
angular.module('app').config(function ($locationProvider, $routeProvider) {
        $locationProvider.hashPrefix('!');
        $routeProvider.when('/expression-analyzer', {
            template: '<expression-analyzer></expression-analyzer>'
        }).otherwise('/expression-analyzer');
    }
);

angular.module('app').controller('ExpressionAnalyzerController', function ($http) {
    let vm = this;

    vm.analyzeExpression = function () {
        $http.get('/analyze', {
            params: {expression: vm.expression}
        }).then(function (response) {
            vm.analysisResult = response.data;
        });
    }
}).component('expressionAnalyzer', {
    templateUrl: '/expression-analyzer.html',
    controller: 'ExpressionAnalyzerController',
    controllerAs: 'vm'
});