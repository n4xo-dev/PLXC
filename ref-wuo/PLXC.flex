import java_cup.runtime.*;
%%
%cup

entero = 0|[1-9][0-9]*
real = [0-9]*\.[0-9]+([eE][-+]?[0-9]+)?
ident = [a-zA-Z][a-zA-Z0-9]*
charText = '(\\([\"'\\bfnrt]|u[0-9A-Fa-f]{4})|[^\\'])'
stringText = \"([a-zA-Z]|[\\\"]*|u[0-9A-Fa-f]{4})+\"
comentario = (\/\*([^*]|[\r\n]|(\*+([^*/]|[\r\n])))*\*+\/)|(\/\/.*)
     
%%
";"          {return new Symbol(sym.PYC);}
","          {return new Symbol(sym.COMA);}
"("          {return new Symbol(sym.AP);}
")"          {return new Symbol(sym.CP);}
"{"          {return new Symbol(sym.ALL);}
"}"          {return new Symbol(sym.CLL);}
"["          {return new Symbol(sym.AC);}
"]"          {return new Symbol(sym.CC);}
"+"          {return new Symbol(sym.MAS);}
"++"         {return new Symbol(sym.INCR);}
"--"         {return new Symbol(sym.DECR);}
"+="         {return new Symbol(sym.MASIGUAL);}
"-="         {return new Symbol(sym.MENOSIGUAL);}
"*="         {return new Symbol(sym.PORIGUAL);}
"/="         {return new Symbol(sym.ENTREIGUAL);}
"-"          {return new Symbol(sym.MENOS);}
"*"          {return new Symbol(sym.POR);}
"/"          {return new Symbol(sym.DIV);}
"="          {return new Symbol(sym.ASIG);}
"%"          {return new Symbol(sym.MOD);}
"?"          {return new Symbol(sym.INTERR);}
":"          {return new Symbol(sym.DP);}
"?:"         {return new Symbol(sym.ELVIS);}
"&"          {return new Symbol(sym.AMPERSAND);}
"~"          {return new Symbol(sym.MONTE);}
"~!"         {return new Symbol(sym.MAYUS);}
"."          {return new Symbol(sym.PUNTO);}
"=="         {return new Symbol(sym.IGUAL);}
"!="         {return new Symbol(sym.DIST);}
"!"          {return new Symbol(sym.NEG);}
"<"          {return new Symbol(sym.MENOR);}
">"          {return new Symbol(sym.MAYOR);}
"<="         {return new Symbol(sym.MENOREQ);}
">="         {return new Symbol(sym.MAYOREQ);}
"&&"         {return new Symbol(sym.AND);}
"||"         {return new Symbol(sym.OR);}
"if"         {return new Symbol(sym.IF);}
"in"         {return new Symbol(sym.IN);}
"else"       {return new Symbol(sym.ELSE);}
"while"      {return new Symbol(sym.WHILE);}
"do"         {return new Symbol(sym.DO);}
"for"        {return new Symbol(sym.FOR);}
"print"      {return new Symbol(sym.PRINT);}
"int"        {return new Symbol(sym.INT);}
"float"      {return new Symbol(sym.FLOAT);}
"char"       {return new Symbol(sym.CHAR);}
"(int)"      {return new Symbol(sym.CINT);}
"(char)"     {return new Symbol(sym.CCHAR);}
"(float)"    {return new Symbol(sym.CFLOAT);}
"switch"     {return new Symbol(sym.SWITCH);}
"case"       {return new Symbol(sym.CASE);}
"default"    {return new Symbol(sym.DEFAULT);}
"break"      {return new Symbol(sym.BREAK);}
"length"     {return new Symbol(sym.LENGTH);}
"string"     {return new Symbol(sym.STRING);}
{entero}     {return new Symbol(sym.ENTERO,yytext());}
{real}       {return new Symbol(sym.REAL,yytext());}
{ident}      {return new Symbol(sym.IDENT,yytext());}
{charText}   {return new Symbol(sym.CHARTEXT,yytext());}
{stringText} {return new Symbol(sym.STRINGTEXT,yytext());}
{comentario} {}
[\s]         {}
[^]          {throw new Error("ilegal char: "+yytext());}
  