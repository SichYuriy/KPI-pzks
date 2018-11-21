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
            vm.analysisResult = {
                expression: response.data.expression,
                errors: response.data.errors
            };
            let unoptimizedRootConfig = {
                chart: {
                    container: "#unoptimized-tree"
                },
                nodeStructure: null
            };
            let optimizedRootConfig = {
                chart: {
                    container: "#optimized-tree"
                },
                nodeStructure: null
            };

            if (vm.analysisResult.errors.length === 0) {
                vm.analysisResult.polishNotation = response.data.polishNotation
                    .map((token) => token.value)
                    .reduce((s1, s2) => s1 + ',' + s2, "");
                unoptimizedRootConfig.nodeStructure = response.data.root;
                optimizedRootConfig.nodeStructure = response.data.optimizedRoot;
            }
            new Treant(unoptimizedRootConfig);
            new Treant(optimizedRootConfig);
        });
    };

    vm.getEqualForms = function () {
        $http.get('/pass-brackets', {
            params: {
                expression: vm.expression
            }
        }).then(function (response) {
            vm.equalForms = response.data;
        })
    }
}).component('expressionAnalyzer', {
    templateUrl: '/expression-analyzer.html',
    controller: 'ExpressionAnalyzerController',
    controllerAs: 'vm'
});