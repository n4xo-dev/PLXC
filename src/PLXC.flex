import java_cup.runtime.*;
%%
%cup

int = 0|[1-9][0-9]*
float = [0-9]*\.[0-9]+([eE][-+]?[0-9]+)?
id = [a-zA-Z][a-zA-Z0-9]*
charText = '(\\([\"'\\bfnrt]|u[0-9A-Fa-f]{4})|[^\\'])'
stringText = \"([a-zA-Z]|[\\\"]*|u[0-9A-Fa-f]{4})+\"
comment = (\/\*([^*]|[\r\n]|(\*+([^*/]|[\r\n])))*\*+\/)|(\/\/.*)
     
%%
";"          	{return new Symbol(sym.SEMICOLON);}
","          	{return new Symbol(sym.COMA);}
"("          	{return new Symbol(sym.O_PAR);}
")"          	{return new Symbol(sym.C_PAR);}
"{"          	{return new Symbol(sym.O_CURL);}
"}"          	{return new Symbol(sym.C_CURL);}
"["          	{return new Symbol(sym.O_BRAC);}
"]"          	{return new Symbol(sym.C_BRAC);}
"+"          	{return new Symbol(sym.PLUS);}
"-"          	{return new Symbol(sym.MINUS);}
"++"         	{return new Symbol(sym.PLUS_PLUS);}
"--"         	{return new Symbol(sym.MINUS_MINUS);}
"+="         	{return new Symbol(sym.PLUS_EQ);}
"-="         	{return new Symbol(sym.MINUS_EQ);}
"*"          	{return new Symbol(sym.MULT);}
"*="         	{return new Symbol(sym.MULT_EQ);}
"/"          	{return new Symbol(sym.DIV);}
"/="         	{return new Symbol(sym.DIV_EQ);}
"="          	{return new Symbol(sym.ASSIG);}
"%"          	{return new Symbol(sym.MOD);}
"?"          	{return new Symbol(sym.QUESTION);}
":"          	{return new Symbol(sym.COLON);}
"?:"         	{return new Symbol(sym.ELVIS);}
"&"          	{return new Symbol(sym.AMPERSAND);}
"."          	{return new Symbol(sym.DOT);}
"=="         	{return new Symbol(sym.EQ);}
"!="         	{return new Symbol(sym.NOT_EQ);}
"!"          	{return new Symbol(sym.NOT);}
"<"          	{return new Symbol(sym.LT);}
">"          	{return new Symbol(sym.GT);}
"<="         	{return new Symbol(sym.LTE);}
">="         	{return new Symbol(sym.GTE);}
"&&"         	{return new Symbol(sym.AND);}
"||"         	{return new Symbol(sym.OR);}
"if"         	{return new Symbol(sym.IF);}
"else"       	{return new Symbol(sym.ELSE);}
"while"      	{return new Symbol(sym.WHILE);}
"do"         	{return new Symbol(sym.DO);}
"for"        	{return new Symbol(sym.FOR);}
"print"      	{return new Symbol(sym.PRINT);}
"int"        	{return new Symbol(sym.INT);}
"float"      	{return new Symbol(sym.FLOAT);}
"char"       	{return new Symbol(sym.CHAR);}
"(int)"      	{return new Symbol(sym.CAST_INT);}
"(char)"     	{return new Symbol(sym.CAST_CHAR);}
"(float)"    	{return new Symbol(sym.CAST_FLOAT);}
"switch"     	{return new Symbol(sym.SWITCH);}
"case"       	{return new Symbol(sym.CASE);}
"default"    	{return new Symbol(sym.DEFAULT);}
"break"      	{return new Symbol(sym.BREAK);}
"length"     	{return new Symbol(sym.LENGTH);}
"string"     	{return new Symbol(sym.STRING);}
{int}      	 	{return new Symbol(sym.INT_NUM,yytext());}
{float}      	{return new Symbol(sym.FLOAT_NUM,yytext());}
{id}         	{return new Symbol(sym.ID,yytext());}
{charText}   	{return new Symbol(sym.CHARTEXT,yytext());}
{stringText} 	{return new Symbol(sym.STRINGTEXT,yytext());}
{comment} 		{}
[\s]         	{}
[^]          	{throw new Error("ilegal char: "+yytext());}
  