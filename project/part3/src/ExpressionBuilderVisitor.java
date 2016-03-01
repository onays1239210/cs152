package edu.sjsu.fwjs;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.fwjs.parser.FeatherweightJavaScriptBaseVisitor;
import edu.sjsu.fwjs.parser.FeatherweightJavaScriptParser;

public class ExpressionBuilderVisitor extends FeatherweightJavaScriptBaseVisitor<Expression>{
	
	private Expression body;
	private Expression f;
	
	
    @Override
    public Expression visitProg(FeatherweightJavaScriptParser.ProgContext ctx) {
        List<Expression> stmts = new ArrayList<Expression>();
        for (int i=0; i<ctx.stat().size(); i++) {
            Expression exp = visit(ctx.stat(i));
            if (exp != null) stmts.add(exp);
        }
        return listToSeqExp(stmts);
    }

    @Override
    public Expression visitBareExpr(FeatherweightJavaScriptParser.BareExprContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Expression visitIfThenElse(FeatherweightJavaScriptParser.IfThenElseContext ctx) {
        Expression cond = visit(ctx.expr());
        Expression thn = visit(ctx.block(0));
        Expression els = visit(ctx.block(1));
        return new IfExpr(cond, thn, els);
    }

    @Override
    public Expression visitIfThen(FeatherweightJavaScriptParser.IfThenContext ctx) {
        Expression cond = visit(ctx.expr());
        Expression thn = visit(ctx.block());
        return new IfExpr(cond, thn, new ValueExpr(new NullVal()));
    }

    @Override
    public Expression visitWhile(FeatherweightJavaScriptParser.WhileContext ctx){
    	Expression cond = visit(ctx.expr());
    	Expression body = visit(ctx.block());
    	return new WhileExpr(cond, body);
    }
    
    @Override
    public Expression visitPrint(FeatherweightJavaScriptParser.PrintContext ctx) {
       return new PrintExpr(visit(ctx.expr()));
    }
    
    @Override
    public Expression visitEmpty(FeatherweightJavaScriptParser.EmptyContext ctx){
       return null;
    }
    
    @Override
    public Expression visitParens(FeatherweightJavaScriptParser.ParensContext ctx) {
        return visit(ctx.expr());
    }
  
    private BinOpExpr binOpExpHelper(int type, Expression lhs, Expression rhs) {
        Op op = null;
        switch (type) {
        case FeatherweightJavaScriptParser.ADD:
            op = Op.ADD;
            break;
        case FeatherweightJavaScriptParser.SUB:
            op = Op.SUBTRACT;
            break;
        case FeatherweightJavaScriptParser.MUL:
            op = Op.MULTIPLY;
            break;
        case FeatherweightJavaScriptParser.DIV:
            op = Op.DIVIDE;
            break;
        case FeatherweightJavaScriptParser.MOD:
            op = Op.MOD;
            break;
        case FeatherweightJavaScriptParser.SMALLER:
            op = Op.LT;
            break;
        case FeatherweightJavaScriptParser.SMALLEQUAL:
            op = Op.LE;
            break;
        case FeatherweightJavaScriptParser.BIGGER:
            op = Op.GT;
            break;
        case FeatherweightJavaScriptParser.BIGEQUAL:
            op = Op.GE;
            break;
        case FeatherweightJavaScriptParser.EQUAL:
            op = Op.EQ;
         
            break;
        }
        return new BinOpExpr(op, lhs, rhs);
    }
    
    
    
    @Override
    public Expression visitMulDivMod(FeatherweightJavaScriptParser.MulDivModContext ctx){
    	Expression left = visit(ctx.expr(0));
        Expression right = visit(ctx.expr(1));
        return binOpExpHelper(ctx.op.getType(), left, right);
    }
    
    @Override
    public Expression visitBiggersmallerequal(FeatherweightJavaScriptParser.BiggersmallerequalContext ctx){
    	Expression left = visit(ctx.expr(0));
        Expression right = visit(ctx.expr(1));
        return binOpExpHelper(ctx.op.getType(), left, right);
    }
    
    @Override
    public Expression visitBool(FeatherweightJavaScriptParser.BoolContext ctx){
    	Boolean val = Boolean.valueOf(ctx.BOOL().getText());
    	return new ValueExpr(new BoolVal(val));
    }
    
    @Override
    public Expression visitNull(FeatherweightJavaScriptParser.NullContext ctx){
    	return new ValueExpr(new NullVal());
    }
    
    @Override
    public Expression visitAddsub(FeatherweightJavaScriptParser.AddsubContext ctx){
    	Expression left = visit(ctx.expr(0));
        Expression right = visit(ctx.expr(1));
        return binOpExpHelper(ctx.op.getType(), left, right);
    }
    
    @Override
    public Expression visitFunctionapplication(FeatherweightJavaScriptParser.FunctionapplicationContext ctx){
    	
    	f = visit(ctx.expr());
        return visit(ctx.args());
    }
    
    @Override
    public Expression visitId(FeatherweightJavaScriptParser.IdContext ctx){
    	String id = ctx.ID().getText();
    	return new VarExpr(id);
    }
    
    @Override
    public Expression visitFunctionDeclaration(FeatherweightJavaScriptParser.FunctionDeclarationContext ctx){
    	//Expression temp = visit(ctx.params());
    	
    	List<Expression> stmts = new ArrayList<Expression>();
        for (int i=0; i<ctx.stat().size(); i++) {
            Expression exp = visit(ctx.stat(i));
            stmts.add(exp);
           // System.out.println("declaration "+ exp);
        }
    	
    	body = listToSeqExp(stmts);
    	return visit(ctx.params());
        
    	
    }
    
    @Override
    public Expression visitInt(FeatherweightJavaScriptParser.IntContext ctx) {
        int val = Integer.valueOf(ctx.INT().getText());
        return new ValueExpr(new IntVal(val));
    }

    @Override
    public Expression visitVariableDeclaration(FeatherweightJavaScriptParser.VariableDeclarationContext ctx){
    	String id = ctx.ID().getText();
    	Expression exp = visit(ctx.expr());
    	
    	return new VarDeclExpr(id, exp); 
    }
    
    @Override
    public Expression visitAssign(FeatherweightJavaScriptParser.AssignContext ctx){
    	String id = ctx.ID().getText();
    	Expression exp = visit(ctx.expr());
    	
    	return new AssignExpr(id, exp);
    }
    
    @Override
    public Expression visitEmptyparams(FeatherweightJavaScriptParser.EmptyparamsContext ctx){
    	List<String> params = new ArrayList<String>();
    	return getFunctionDeclExpr(params);
    }
    
    @Override
    public Expression visitMultyParams(FeatherweightJavaScriptParser.MultyParamsContext ctx){
    	List<String> params = new ArrayList<String>();
    	for(int i = 0; i < ctx.ID().size(); i++){
    		String id = ctx.ID(i).getText();
    		params.add(id);
    	}
    	
    	return getFunctionDeclExpr(params);
    }
    
    @Override
    public Expression visitMultiArge(FeatherweightJavaScriptParser.MultiArgeContext ctx){
        List<Expression> args = new ArrayList<Expression>();
    	for (int i=0; i<ctx.expr().size(); i++) {
            Expression exp = visit(ctx.expr(i));
            args.add(exp);
        }

    	return getFunctionAppExpr(args);
    }
    
    @Override
    public Expression visitEmptyargs(FeatherweightJavaScriptParser.EmptyargsContext ctx){
    	List<Expression> args = new ArrayList<Expression>();
    	return getFunctionAppExpr(args);
    }
    
    @Override
    public Expression visitFullBlock(FeatherweightJavaScriptParser.FullBlockContext ctx) {
        List<Expression> stmts = new ArrayList<Expression>();
        for (int i=1; i<ctx.getChildCount()-1; i++) {
            Expression exp = visit(ctx.getChild(i));
            stmts.add(exp);
        }
        return listToSeqExp(stmts);
    }

   
    @Override
    public Expression visitSimpBlock(FeatherweightJavaScriptParser.SimpBlockContext ctx) {
        return visit(ctx.stat());
    }
    
	 /**
     * Converts a list of expressions to one sequence expression,
     * if the list contained more than one expression.
     */
    private Expression listToSeqExp(List<Expression> stmts) {
        if (stmts.isEmpty()) return null;
        Expression exp = stmts.get(0);
        for (int i=1; i<stmts.size(); i++) {
            exp = new SeqExpr(exp, stmts.get(i));
        }
        return exp;
    }

    /*
     * return list of string from the params
     */
    private Expression getFunctionDeclExpr(List<String> params){
    	return new FunctionDeclExpr(params, body);
    }
    

    /*
     * return list of string from the params
     */
    private Expression getFunctionAppExpr(List<Expression> args){
    	return new FunctionAppExpr(f, args);
    }

}