// Generated from Expr.g4 by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExprParser}.
 */
public interface ExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code parens}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParens(@NotNull ExprParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParens(@NotNull ExprParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blank}
	 * labeled alternative in {@link ExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterBlank(@NotNull ExprParser.BlankContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blank}
	 * labeled alternative in {@link ExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitBlank(@NotNull ExprParser.BlankContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMulDiv(@NotNull ExprParser.MulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMulDiv(@NotNull ExprParser.MulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(@NotNull ExprParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(@NotNull ExprParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(@NotNull ExprParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(@NotNull ExprParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code int}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterInt(@NotNull ExprParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code int}
	 * labeled alternative in {@link ExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitInt(@NotNull ExprParser.IntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link ExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterPrintExpr(@NotNull ExprParser.PrintExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link ExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitPrintExpr(@NotNull ExprParser.PrintExprContext ctx);
}