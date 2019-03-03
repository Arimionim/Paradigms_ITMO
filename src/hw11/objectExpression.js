function Expr(f, myDiff, s, args) {
    this.f = f;
    this.args = args;
    this.s = s;
    this.myDiff = myDiff;
}

Expr.prototype.diff = function (x) {
    var diffArgs = [];
    for (var i = 0; i < this.args.length; i++) {
        diffArgs.push(this.args[i]);
    }
    for (i = 0; i < this.args.length; i++) {
        diffArgs.push(this.args[i].diff(x));
    }
    return this.myDiff.apply(null, diffArgs);
};

Expr.prototype.evaluate = function () {
    var tmp = [];
    for (var i = 0; i < this.args.length; i++)
        tmp.push(this.args[i].evaluate.apply(this.args[i], arguments))
    return this.f.apply(null, tmp);
};
Expr.prototype.toString = function () {
    var str = '';
    for (var i = 0; i < this.args.length; i++) {
        str += this.args[i].toString() + ' ';
    }
    return str + this.s;
};
Expr.prototype.postfix = function () {
    var str = '';
    for (var i = 0; i < this.args.length; i++) {
        str += this.args[i].postfix() + ' ';
    }
    return '(' + str + this.s + ')';
};

Expr.prototype.prefix = function () {
    var str = '';
    for (var i = 0; i < this.args.length; i++) {
        str += ' ' + this.args[i].prefix();
    }
    return '(' + this.s + str + ')';
};

ExprFactory = function (f, diff, s) {
    var exp = function () {
        Expr.call(this, f, diff, s, arguments);
    };
    exp.prototype = Object.create(Expr.prototype);
    return exp;
};


var ONE = new Const(1);
var TWO = new Const(2);
var ZERO = new Const(0);
var E = new Const(Math.E);

var Add = ExprFactory(
    function (a, b) {
        return a + b;
    },
    function (a, b, da, db) {
        return new Add(
            da,
            db
        );
    }, '+');

var Subtract = ExprFactory(
    function (a, b) {
        return a - b;
    },
    function (a, b, da, db) {
        return new Subtract(
            da,
            db
        );
    }, '-');

var Divide = ExprFactory(
    function (a, b) {
        return a / b;
    },
    function (a, b, da, db) {
        return new Divide(
            new Subtract(
                new Multiply(
                    da, b
                ),
                new Multiply(
                    db, a
                )
            ),
            new Multiply(
                b, b
            )
        )
    }, '/');

var Multiply = ExprFactory(
    function (a, b) {
        return a * b;
    },
    function (a, b, da, db) {
        return new Add(
            new Multiply(
                da, b
            ),
            new Multiply(
                db, a
            )
        )
    }, '*');

var Negate = ExprFactory(
    function (a) {
        return -a;
    },
    function (a, da) {
        return new Negate(da);
    }, 'negate');

var Arctan = ExprFactory(
    function (a) {
        return Math.atan(a);
    },
    function (a, da) {
        return new Divide(
            da,
            new Add(
                new Multiply(
                    a,
                    a
                ),
                ONE
            )
        )
    }, 'atan');

var Exp = ExprFactory(
    function (a) {
        return Math.exp(a);
    },
    function (a, da) {
        return new Multiply(
            new Exp(a),
            da
        )
    }, 'exp');


var Log = ExprFactory(
    function (a, b) {
        return Math.log(Math.abs(b)) / Math.log(Math.abs(a));
    },
    function (a, b, da, db) {
        return new Divide(
            new Subtract(
                new Divide(
                    new Multiply(
                        new Log(E, a),
                        db
                    ),
                    b
                ),
                new Divide(
                    new Multiply(
                        da,
                        new Log(E, b)
                    ),
                    a
                )
            ),
            new Square(
                new Log(E, a)
            )
        )
    }, 'log');


var Power = ExprFactory(
    function (a, b) {
        return Math.pow(a, b);
    },
    function (a, b, da, db) {
        return new Multiply(
            new Power(
                a,
                new Subtract(
                    b,
                    ONE
                )
            ),
            new Add(
                new Multiply(
                    b,
                    da
                ),
                new Multiply(
                    a,
                    new Multiply(
                        new Log(E, a),
                        db
                    )
                )
            )
        )
    }, 'pow');


var Square = ExprFactory(
    function (a) {
        return a * a;
    },
    function (a, da) {
        return new Multiply(
            TWO,
            new Multiply(
                a,
                da
            )
        )
    }, 'square');

var Abs = ExprFactory(
    function (a) {
        return Math.abs(a);
    },
    function (a, da) {
        return new Divide(
            new Multiply(
                a,
                da
            ),
            new Abs(a)
        )
    }, 'abs');


var Sqrt = ExprFactory(
    function (a) {
        return Math.sqrt(Math.abs(a));
    },
    function (a, da) {
        return new Divide(
            new Multiply(
                da,
                a
            ),
            new Multiply(
                TWO,
                new Abs(
                    new Multiply(
                        new Sqrt(a),
                        a
                    )
                )
            )
        )
    }, 'sqrt');

function Const(a) {
    this.evaluate = function () {
        return Number(a);
    };

    this.toString = function () {
        return a.toString();
    };
    this.prefix = this.toString;
    this.diff = function () {
        return ZERO;
    };
    this.postfix = this.toString;
}

function Variable(a) {
    this.evaluate = function () {
        var i = {"x": 0, "y": 1, "z": 2};
        return arguments[i[a]];
    };
    this.toString = function () {
        return a;
    };
    this.prefix = this.toString;
    this.diff = function (x) {
        return (x === a ? ONE : ZERO);
    };
    this.postfix = this.toString;
}

var exp = Object.freeze({
    "-": [Subtract, 2], "+": [Add, 2], "/": [Divide, 2], "*": [Multiply, 2],
    "negate": [Negate, 1], "square": [Square, 1], "sqrt": [Sqrt, 1],
    "pow": [Power, 2], "log": [Log, 2],
    "exp": [Exp, 1], "atan": [Arctan, 1]
});

var vars = Object.freeze({
    'x': 1, 'y': 1, 'z': 1
});

var parse = function (str, mode) {
    function check_bracket(br, mode) {
        if (mode === 0) {
            if (br === '(') {
                if (opr === '(') {
                    throw new ParseError(str, "Invalid operation", pos).message;
                }
            }
            else if (br === ')') {
                if (opr === '(' || exp[opr] !== undefined) {
                    throw new ParseError(str, "Invalid operation", pos).message;
                }
            }
        } else if (mode === 1) {
            if (br === '(') {
                if (exp[opr] !== undefined) {
                    throw new ParseError(str, "Invalid operation", pos).message;
                }
            }
            else if (br === ')') {
                if (exp[opr] === undefined) {
                    throw new ParseError(str, "Invalid operation", pos).message;
                }
            }
        }
    }

    function check_opr(mode) {
        if (mode === 0) {
            if (opr !== '(')
                throw new ParseError(str, "Invalid operation", pos).message;
        }
        else if (mode === 1) {
            if (opr === '(')
                throw new ParseError(str, "Invalid operation", pos).message;
        }
    }

    function check_arg() {
        if (mode === 0) {
            if (opr === '(')
                throw new ParseError(str, "Invalid argument", pos).message;
        }
        else if (mode === 1) {
            if (exp[opr] !== undefined) {
                throw new ParseError(str, "Invalid argument", pos).message;
            }
        }
    }

    function get_expr(mode) {
        var t = [];
        var tmp = exp[opr];
        if (mode === 0) {
            stack.pop();
            var size = stack.length;
            make_expr();
            if (stack.length - size !== tmp[1]) {
                throw new ParseError(str, "Invalid number of args", pos).message;
            }
            for (var i = 0; i < tmp[1]; i++) {
                t.push(stack.pop());
            }
        } else if (mode === 1) {
            next_expr();
            if (opr !== ')') {
                throw new ParseError(str, "Invalid operation", pos).message;
            }
            var s = stack.pop();
            while (s !== '(') {
                t.push(s);
                s = stack.pop();
            }
            if (t.length !== tmp[1]) {
                throw new ParseError(str, "Invalid number of args", pos).message;
            }
        }
        t.reverse();
        var obj = Object.create(tmp[0].prototype);
        tmp[0].apply(obj, t);
        stack.push(obj);
    }

    str = str.trim();
    var pos = 0;
    var balance = 0;
    var opr = '';
    var stack = [];
    var skip_spaces = function () {
        while (str[pos] === ' ' && pos < str.length)
            pos++;
    };
    var take_opr = function () {
        var opr = '';
        if (str[pos] === '(' || str[pos] === ')') {
            opr = str[pos];
            pos++;
            return opr;
        }
        opr = '';
        while (str[pos] !== ' ' && str[pos] !== '(' && str[pos] !== ')' && pos < str.length) {
            opr += str[pos];
            pos++;
        }
        return opr;
    };
    var next_expr = function () {
        skip_spaces();
        if (pos === str.length) {
            if (opr !== ')' && Number(opr) === undefined && vars[opr] === undefined)
                throw new ParseError(str, "Missed argument", pos).message;
            if (balance !== 0)
                throw new ParseError(str, "Missed closing bracket", pos).message;
            opr = '$$$';
            return;
        }
        if (balance === 0 && pos > 0)
            throw new ParseError(str, "Invalid expression", pos).message;
        var tmp = take_opr(mode);
        if (exp[tmp] !== undefined) {
            check_opr(mode);
            opr = tmp;
            return;
        }
        if (tmp === '(') {
            check_bracket('(', mode);
            opr = tmp;
            balance++;
            return;
        }
        if (tmp === ')') {
            check_bracket(')', mode);
            opr = tmp;
            balance--;
            if (balance < 0)
                throw new ParseError(str, "Missed opening bracket", pos).message;
            return;
        }
        if (vars[tmp]) {
            check_arg(mode);
            opr = tmp;
            return;
        }
        if (Number(tmp)) {
            check_arg(mode);
            opr = tmp;
            return;
        }
        throw new ParseError(str, "Invalid argument", pos).message;
    };
    var make_expr = function () {
        next_expr();
        if (opr === '(') {
            stack.push('(');
            make_expr();
        }
        else if (exp[opr] !== undefined) {
            get_expr(mode);
            make_expr();
        } else if (vars[opr] !== undefined) {
            stack.push(new Variable(opr));
            make_expr();
        } else if (Number(opr)) {
            stack.push(new Const(opr));
            make_expr();
        }
    };
    make_expr();
    if (stack.length === 0)
        throw new ParseError(str, "Invalid expression", pos).message;
    return stack.pop();
};

var ParseError = function (str, message, pos) {
    s = message + '\n' + str.substr(0, pos) + ' <--- ' + str.substr(pos, str.length);
    this.message = s;
    this.name = 'ParseError';
};

var parsePrefix = function (s) {
    return parse(s, 0);
};

var parsePostfix = function (s) {
    return parse(s, 1);
};