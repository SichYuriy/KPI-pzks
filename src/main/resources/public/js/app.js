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

    vm.addTime = 1;
    vm.subtractTime = 1;
    vm.multiplyTime = 2;
    vm.divideTime = 3;
    vm.sinTime = 4;
    vm.cosTime = 4;
    vm.layersCount = 4;

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
    };

    vm.simulate = function () {
        $http.get('/simulate', {
            params: {
                expression: vm.expression,
                layersCount: vm.layersCount,
                addTime: vm.addTime,
                subtractTime: vm.subtractTime,
                multiplyTime: vm.multiplyTime,
                divideTime: vm.divideTime,
                sinTime: vm.sinTime,
                cosTime: vm.cosTime
            }
        }).then(function (response) {
            setSimulationResult(response.data);
        })
    };

    vm.simulateAll = function () {
        let allExpressions = [vm.expression];
        allExpressions = allExpressions.concat(vm.equalForms);
        $http.get('/simulate-all', {
            params: {
                expressions: allExpressions,
                layersCount: vm.layersCount,
                addTime: vm.addTime,
                subtractTime: vm.subtractTime,
                multiplyTime: vm.multiplyTime,
                divideTime: vm.divideTime,
                sinTime: vm.sinTime,
                cosTime: vm.cosTime
            }
        }).then(function (response) {
            vm.groupSimulationResult = response.data;
            setSimulationResult(response.data[0].simulationResult);

        });
    };

    vm.setSimulationResult = setSimulationResult;

    function setSimulationResult(simulationResult) {
        vm.layers = [];
        for (let i = 0; i < vm.layersCount; i++) {
            vm.layers.push(i + 1);
        }
        vm.simulationResult = simulationResult;
        let simulationRootConfig = {
            chart: {
                container: "#simulation-tree"
            },
            nodeStructure: simulationResult.root
        };
        new Treant(simulationRootConfig);
    }

}).component('expressionAnalyzer', {
    templateUrl: '/expression-analyzer.html',
    controller: 'ExpressionAnalyzerController',
    controllerAs: 'vm'
});