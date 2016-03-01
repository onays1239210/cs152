grammar FeatherweightJavaScript;


@header { package edu.sjsu.fwjs.parser; }

// Reserved words
IF        : 'if' ;
ELSE      : 'else' ;
WHILE     : 'while' ;
FUNCTION  : 'function' ;
VAR       : 'var' ;
PRINT     : 'print' ;

// Literals
INT       : [1-9][0-9]* | '0' ;
BOOL      : 'true' | 'false';
NULL      : 'null';

//Identifier
ID : [a-zA-Z|'_'][a-zA-Z0-9|'_']* ;
// Symbols
MUL       : '*' ;
DIV       : '/' ;
ADD       : '+' ;
SUB       : '-' ;
MOD       : '%' ;
BIGGER    : '>' ;
SMALLER   : '<' ;
BIGEQUAL  : '>=';
SMALLEQUAL: '<=';
EQUAL     : '==';
SEPARATOR : ';' ;


// Whitespace and comments
NEWLINE   : '\r'? '\n' -> skip ;
BLOCK_COMMENT : '/*' .*? '*/' -> skip;
LINE_COMMENT  : '//' ~[\n\r]* -> skip ;
WS            : [ \t]+ -> skip ; // ignore whitespace


// ***Paring rules ***

/** The start rule */
prog: stat+ ;

stat: expr SEPARATOR                                    # bareExpr
    | IF '(' expr ')' block ELSE block                  # ifThenElse
    | IF '(' expr ')' block                             # ifThen
    | WHILE '(' expr ')' block                          # while
    | PRINT '(' expr ')' SEPARATOR                      # print
    | SEPARATOR                                         # empty
    ;

expr: expr op=( '*' | '/' | '%' ) expr                  # MulDivMod
    | expr op=( '+' | '-') expr                         # addsub
    | expr op=( '<' | '<='| '>'| '>=' | '==') expr      # biggersmallerequal
    | FUNCTION params '{' stat* '}'                     # functionDeclaration
    | expr args                                         # functionapplication
    | VAR ID '=' expr                                   # variableDeclaration
    | ID                                                # id
    | ID '=' expr                                       # assign
    | INT                                               # int
    | BOOL                                              # bool
    | NULL                                              # null
    | '(' expr ')'                                      # parens
    ;

params: '()'											# emptyparams
    |   '(' (ID ',' )* ID ')'                           # multyParams
	;
	
args: '(' (expr ',' )* expr ')'							# multiArge
	| '()'												# emptyargs
	;
		
block: '{' stat* '}'                                    # fullBlock
     | stat                                             # simpBlock
     ;
