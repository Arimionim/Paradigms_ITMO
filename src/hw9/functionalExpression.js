//cnst, variable, add, subtract, multiply, divide, negate


var operator = function (operation) {
    return function () {
        var args = arguments;
        return function () {
            var operands = [];
            for (var i = 0; i < args.length; i++){
                operands[i] = args[i].apply(null, arguments);
            }
            return operation.apply(null, operands);
        }
    }
};

var add = operator(function (x, y) {
    return x + y;
});

var subtract = operator(function (x, y) {
    return x - y;
});

var multiply = operator(function (x, y) {
    return x * y;
});

var divide = operator(function (x, y) {
    return x / y;
});

var negate = operator (function (x) {
    return -x;
});

var max5 = operator(function () {
   return Math.max.apply(null, arguments);
});

var min3 = operator(function () {
    return Math.min.apply(null, arguments);
});


var variable = function(name){
    return function () {
        return arguments[VARIABLES[name]];
    }
};

var x = variable('x'), y = variable('y'), z = variable('z');

var VARIABLES = {
    'x': 0,
    'y': 1,
    'z': 2
};

var OPERATION = {
    '+': 2,
    '-': 2,
    '*': 2,
    '/': 2,
    "negate": 1,
    "min3": 3,
    "max5": 5
};

var MATH_CONSTS = {
    "pi": Math.PI,
    "e": Math.E
};

var cnst = function (x) {
    return function () {
        return x;
    };
};

var pi = function () {
    return MATH_CONSTS["pi"];
};

var e = function () {
    return MATH_CONSTS["e"];
};

var OP_FUNCTIONS = {
    "+": add,
    "-": subtract,
    "*": multiply,
    "/": divide,
    "min3": min3,
    "max5": max5,
    "negate": negate
};

var parse = function (input) {
    var operands = input.split(" ").filter(function (t) {
        return t.length > 0;
    });

    var stack = [];

    for (var i = 0; i < operands.length; i++){
        if (operands[i] in OPERATION) {
            cntArgs = OPERATION[operands[i]];
            var arg = [];
            for (var j = 0; j < cntArgs; j++){
                arg[cntArgs - j - 1] = stack.pop();
            }
            stack.push(OP_FUNCTIONS[operands[i]].apply(null, arg));
        }
        else if (operands[i] in VARIABLES){
            stack.push(variable(operands[i]));
        }
        else if (operands[i] in MATH_CONSTS){
            stack.push(cnst(MATH_CONSTS[operands[i]]));
        }
        else {
            stack.push(cnst(parseInt(operands[i])));
        }
    }
    return stack.pop();
};

