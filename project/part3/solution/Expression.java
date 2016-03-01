package edu.sjsu.fwjs;

import java.util.ArrayList;
import java.util.List;

/**
 * FWJS expressions.
 */
public interface Expression {
  /**
   * Evaluate the expression in the context of the specified environment.
   */
  public Value evaluate(Environment env);
}

// NOTE: Using package access so that all implementations of Expression
// can be included in the same file.

/**
 * FWJS constants.
 */
class ValueExpr implements Expression {
  private Value val;

  public ValueExpr(Value v) {
    this.val = v;
  }

  public Value evaluate(Environment env) {
    return this.val;
  }
}

/**
 * Expressions that are a FWJS variable.
 */
class VarExpr implements Expression {
  private String varName;

  public VarExpr(String varName) {
    this.varName = varName;
  }

  public Value evaluate(Environment env) {
    return env.resolveVar(varName);
  }
}

/**
 * A print expression.
 */
class PrintExpr implements Expression {
  private Expression exp;

  public PrintExpr(Expression exp) {
    this.exp = exp;
  }

  public Value evaluate(Environment env) {
    Value v = exp.evaluate(env);
    System.out.println(v.toString());
    return v;
  }
}

/**
 * Binary operators (+, -, *, etc). Currently only numbers are supported.
 */
class BinOpExpr implements Expression {
  private Op op;
  private Expression e1;
  private Expression e2;

  public BinOpExpr(Op op, Expression e1, Expression e2) {
    this.op = op;
    this.e1 = e1;
    this.e2 = e2;
  }

  public Value evaluate(Environment env) {

    IntVal theVal1 = (IntVal) e1.evaluate(env);
    IntVal theVal2 = (IntVal) e2.evaluate(env);
    Value theret = new IntVal(0);

    if (op == Op.ADD) {
      theret = new IntVal(theVal1.toInt() + theVal2.toInt());
    } else if (op == Op.SUBTRACT) {
      theret = new IntVal(theVal1.toInt() - theVal2.toInt());
    }

    else if (op == Op.MULTIPLY) {
      theret = new IntVal(theVal1.toInt() * theVal2.toInt());
    }

    else if (op == Op.DIVIDE) {
      theret = new IntVal(theVal1.toInt() / theVal2.toInt());
    }

    else if (op == Op.MOD) {
      theret = new IntVal(theVal1.toInt() % theVal2.toInt());
    }

    else if (op == Op.EQ) {
      theret = new BoolVal(theVal1.toInt() == theVal2.toInt());
    }

    else if (op == Op.GT) {
      theret = new BoolVal(theVal1.toInt() > theVal2.toInt());
    }

    else if (op == Op.GE) {
      theret = new BoolVal(theVal1.toInt() >= theVal2.toInt());
    }

    else if (op == Op.LT) {
      theret = new BoolVal(theVal1.toInt() < theVal2.toInt());
    } else if (op == Op.LE) {
      theret = new BoolVal(theVal1.toInt() >= theVal2.toInt());
    }

    else if (op == Op.GT) {
      theret = new BoolVal(theVal1.toInt() > theVal2.toInt());
    }
    env.updateVar(theret.toString(), theret);
    return theret;
  }
}

/**
 * If-then-else expressions. Unlike JS, if expressions return a value.
 */
class IfExpr implements Expression {
  private Expression cond;
  private Expression thn;
  private Expression els;

  public IfExpr(Expression cond, Expression thn, Expression els) {
    this.cond = cond;
    this.thn = thn;
    this.els = els;
  }

  public Value evaluate(Environment env) {
    BoolVal thebool = (BoolVal) cond.evaluate(env);
    env.updateVar(thebool.toString(), thebool);

    if (thebool.toBoolean()) {
      Value theret = thn.evaluate(env);
      env.updateVar(theret.toString(), theret);
      return theret;
    } else {
      Value theret = els.evaluate(env);
      env.updateVar(theret.toString(), theret);
      return theret;
    }
  }
}

/**
 * While statements (treated as expressions in FWJS, unlike JS).
 */
class WhileExpr implements Expression {
  private Expression cond;
  private Expression body;

  public WhileExpr(Expression cond, Expression body) {
    this.cond = cond;
    this.body = body;
  }

  public Value evaluate(Environment env) {
    Value theval = new BoolVal(false); // initiate the Value object that we
                      // return

    while (((BoolVal) cond.evaluate(env)).toBoolean()) {
      theval = body.evaluate(env);
    }
    return theval;
  }
}

/**
 * Sequence expressions (i.e. 2 back-to-back expressions).
 */
class SeqExpr implements Expression {
  private Expression e1;
  private Expression e2;

  public SeqExpr(Expression e1, Expression e2) {
    this.e1 = e1;
    this.e2 = e2;
  }

  public Value evaluate(Environment env) {
    Value theval = e1.evaluate(env);

    if (theval instanceof BoolVal) {
      env.updateVar(((BoolVal) theval).toString(), theval);
    } else if (theval instanceof IntVal) {
      env.updateVar(((IntVal) theval).toString(), theval);
    }

    else if (theval instanceof ClosureVal) {
      env.updateVar(((ClosureVal) theval).toString(), theval);
    }

    else if (theval instanceof NullVal) {
      env.updateVar(((NullVal) theval).toString(), theval);
    }

    Value theVal2 = e2.evaluate(env);
    return theVal2;
  }
}

/**
 * Declaring a variable in the local scope.
 */
class VarDeclExpr implements Expression {
  private String varName;
  private Expression exp;

  public VarDeclExpr(String varName, Expression exp) {
    this.varName = varName;
    this.exp = exp;
  }

  public Value evaluate(Environment env) {
    Value theVal = exp.evaluate(env);
    env.createVar(varName, theVal);
    return theVal;
  }
}

/**
 * Updating an existing variable. If the variable is not set already, it is
 * added to the global scope.
 */
class AssignExpr implements Expression {
  private String varName;
  private Expression e;

  public AssignExpr(String varName, Expression e) {
    this.varName = varName;
    this.e = e;
  }

  public Value evaluate(Environment env) {
    Value theVal = e.evaluate(env);
    env.updateVar(varName, theVal);
    return theVal;
  }
}

/**
 * A function declaration, which evaluates to a closure.
 */
class FunctionDeclExpr implements Expression {
  private List<String> params;
  private Expression body;

  public FunctionDeclExpr(List<String> params, Expression body) {
    this.params = params;
    this.body = body;
  }

  public Value evaluate(Environment env) {
    ClosureVal theval = new ClosureVal(params, body, env);
    env.updateVar(theval.toString(), theval);
    return theval;
  }
}

/**
 * Function application.
 */
class FunctionAppExpr implements Expression {
  private Expression f;
  private List<Expression> args;

  public FunctionAppExpr(Expression f, List<Expression> args) {
    this.f = f;
    this.args = args;
  }

  public Value evaluate(Environment env) {

    List<Value> argsVals = new ArrayList<Value>();
    for (Expression e : args) {
      argsVals.add(e.evaluate(env));
    }

    ClosureVal v = (ClosureVal) f.evaluate(env);
    Value retv = v.apply(argsVals);
    return retv;
  }
}