package edu.sjsu.fwjs;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.fwjs.parser.FeatherweightJavaScriptBaseVisitor;
import edu.sjsu.fwjs.parser.FeatherweightJavaScriptParser;

public class ExpressionBuilderVisitor extends FeatherweightJavaScriptBaseVisitor<Expression>{
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
        return new IfExpr(cond, thn, null);
    }

    @Override 
    public Expression visitWhile(FeatherweightJavaScriptParser.WhileContext ctx)
    {
        Expression cond = visit(ctx.expr());
        Expression thn = visit(ctx.block());
        return new WhileExpr(cond, thn);
    }
    
    
    @Override 
    public Expression visitPrint(FeatherweightJavaScriptParser.PrintContext ctx)
    {
        Expression prnt = visit(ctx.expr());
        PrintExpr prntexpr = new PrintExpr(prnt);
        return prntexpr;
    }
    
    /*
    @Override
    public Expression visitEmpty(FeatherweightJavaScriptParser.EmptyContext ctx)
    {
        //Need to process the SEPARATOR (";") what kind of expression should be returned?

    }*/

    private Op opconverter(String conv)
    {
        Op theop;
        if(conv == "+")
        {
            theop = Op.ADD;
        }
        else if(conv == "-")
        {
            theop = Op.SUBTRACT;
        }
        else if(conv == "*")
        {
            theop = Op.MULTIPLY;
        }
        else if(conv == "/")
        {
            theop = Op.DIVIDE;
        }
        else if(conv == "%")
        {
            theop = Op.MOD;
        }
        else if(conv == "==")
        {
            theop = Op.EQ;
        }
        else if(conv == ">")
        {
            theop = Op.GT;
        }
        else if(conv == "<")
        {
            theop = Op.LT;
        }
        else if(conv == ">=")
        {
            theop = Op.GE;
        }
        else// if(conv == "<=")
        {
            theop = Op.LE;
        }
        return theop;
    }

    @Override 
    public Expression visitMulDivMod(FeatherweightJavaScriptParser.MulDivModContext ctx)
    {
        Expression exp1 = visit(ctx.expr(0));
        Expression exp2 = visit(ctx.expr(1));
        Op theOp = opconverter(ctx.getChild(1).getText()); 

        return new BinOpExpr(theOp, exp1, exp2);
    }

    @Override
    public Expression visitAddsub(FeatherweightJavaScriptParser.AddsubContext ctx)
    {
        Expression exp1 = visit(ctx.expr(0));
        Expression exp2 = visit(ctx.expr(1));
        Op theOp = opconverter(ctx.getChild(1).getText()); 

        return new BinOpExpr(theOp, exp1, exp2);
    }

    @Override
    public Expression visitBiggersmallerequal(FeatherweightJavaScriptParser.BiggersmallerequalContext ctx)
    {
        Expression exp1 = visit(ctx.expr(0));
        Expression exp2 = visit(ctx.expr(1));
        Op theOp = opconverter(ctx.getChild(1).getText()); 

        return new BinOpExpr(theOp, exp1, exp2);
    }
    
    @Override
    public Expression visitFunctionDeclaration(FeatherweightJavaScriptParser.FunctionDeclarationContext ctx)
    {
        //Capture Params by getting params().getChild(x), making sure to skip comas      
        
        List<String> params = new ArrayList<String>(); 
        int limit = ctx.params().getChildCount();

        for(int i = 1; i < limit; i+=2) 
        {
            String param = ctx.params().getChild(i).getText(); 
            params.add(param);
        }

        List<Expression> stmts = new ArrayList<Expression>(); 
        int limitTwo = ctx.getChildCount();

        for(int j = 3; j < limitTwo; j++) 
        {
            Expression exp = visit(ctx.getChild(j)); 
            stmts.add(exp);
        }

        Expression body = listToSeqExp(stmts); //convert list to Sequence Expression
        return new FunctionDeclExpr(params, body);
    }

    @Override
    public Expression visitFunctionApplication(FeatherweightJavaScriptParser.FunctionApplicationContext ctx)
    {
        Expression thefunc = visit(ctx.expr()); 
        
        List<Expression> theArgs = new ArrayList<Expression>(); 
        int limit = ctx.args().getChildCount();

        for(int i = 1; i < limit; i+=2) 
        {
            Expression arg = visit(ctx.args().getChild(i)); 
            theArgs.add(arg);
        }

        return new FunctionAppExpr(thefunc, theArgs);
    }

    @Override
    public Expression visitVariableDeclaration(FeatherweightJavaScriptParser.VariableDeclarationContext ctx)
    {
        String theID = ctx.ID().getText(); 
        Expression theExpr = visit(ctx.expr()); 
        return new VarDeclExpr(theID, theExpr);
    }

    @Override
    public Expression visitId(FeatherweightJavaScriptParser.IdContext ctx)
    {
        String theID = ctx.ID().getText();
        return new VarExpr(theID);
    }

    @Override
    public Expression visitAssign(FeatherweightJavaScriptParser.AssignContext ctx)
    {
        String theID = ctx.ID().getText();
        Expression theExpr = visit(ctx.expr());

        return new AssignExpr(theID, theExpr);
    }

    @Override
    public Expression visitInt(FeatherweightJavaScriptParser.IntContext ctx) {
        int val = Integer.valueOf(ctx.INT().getText());
        return new ValueExpr(new IntVal(val));
    }

    @Override
    public Expression visitBool(FeatherweightJavaScriptParser.BoolContext ctx) {
        Boolean bool = new Boolean(ctx.BOOL().getText());
        return new ValueExpr(new BoolVal(bool));
    }

    @Override
    public Expression visitNull(FeatherweightJavaScriptParser.NullContext ctx) {
        return new ValueExpr(new NullVal());
    }

    @Override
    public Expression visitParens(FeatherweightJavaScriptParser.ParensContext ctx) {
        return visit(ctx.expr());
    }
    /*
    @Override
    public List<String> visitEmptyParams(FeatherweightJavaScriptParser.EmptyParamsContext ctx)
    {
        return new ArrayList<String>();
    }

    @Override 
    public List<String> visitMultyParams(FeatherweightJavaScriptParser.MultyParamsContext ctx)
    {
        //Capture IDs of the parameters and return the list 
        //Need to filter out the commas in the pattern: '(' (ID ',' )* ID ')' **TODO**
        List<String> params = new ArrayList<String>();
        for (int i=1; i < ctx.getChildCount()-1; i++) 
        {
            String param = visit(ctx.getChild(i)); //Cast or convert from child tree to string
            if(!param.contains(","))
            {
                params.add(param);
            }
            else
            {
                param = param.substring(0,param.length()-1);
                params.add(param);
            }
        }
        return params;
    }

    
    @Override
    public List<Expression> visitMultiArgs(FeatherweightJavaScriptParser.MultiArgsContext ctx)
    {
        //Capture args of the function and return the list 
        //Need to filter out the commas in the pattern: '(' (expr ',' )* expr ')'  **TODO**
        
        List<Expression> args = new ArrayList<Expression>();
        for (int i=1; i < ctx.getChildCount()-1; i++) 
        {
            Expression arg = visit(ctx.getChild(i));
            args.add(arg);
        }
        return args;
        List<Expression> args = new ArrayList<Expression>
        for(int i = 0; i < ctx.getChildCount(); i++)
        {

        }
    }

    @Override
    public List<Expression> visitEmptyArgs(FeatherweightJavaScriptParser.EmptyArgsContext ctx)
    {
        //Return empty list of arguments 
        return new ArrayList<Expression>();
    }*/

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
}
