import java_cup.runtime.*;
%%

/*  Declaraciones */
%cup

/*
%debug
*/

%state CARACTER
%state CADENA

%%   

/* Expresiones y reglas */
<YYINITIAL>{
   
	"="						{ return new Symbol(sym.ASIG); }

	"+"						{ return new Symbol(sym.MAS); }
	"-"						{ return new Symbol(sym.MENOS); }

	"*"						{ return new Symbol(sym.POR); }
	"/"						{ return new Symbol(sym.ENTRE); }

	"&&"					{ return new Symbol(sym.Y); }
	"||"					{ return new Symbol(sym.O); }
	"!"						{ return new Symbol(sym.NEGADO); }

	"=="					{ return new Symbol(sym.IGUAL); }
	"!="					{ return new Symbol(sym.DIFERENTE); }
	"<="					{ return new Symbol(sym.MENOR_IGUAL); }
	">="					{ return new Symbol(sym.MAYOR_IGUAL); }
	"<"						{ return new Symbol(sym.MENOR); }
	">"						{ return new Symbol(sym.MAYOR); }


	"("						{ return new Symbol(sym.AP); }
	")"						{ return new Symbol(sym.CP); }
	"{"						{ return new Symbol(sym.ALL); }
	"}"						{ return new Symbol(sym.CLL); }
	"["						{ return new Symbol(sym.AC); }
	"]"						{ return new Symbol(sym.CC); }

	"."						{ return new Symbol(sym.PUNTO); }
	","						{ return new Symbol(sym.COMA); }
	";"						{ return new Symbol(sym.PYC); }

	"print"					{ return new Symbol(sym.PRINT); }

	"length"				{ return new Symbol(sym.LENGTH); }

	"if"					{ return new Symbol(sym.IF); }
	"else"					{ return new Symbol(sym.ELSE); }
	"do"					{ return new Symbol(sym.DO); }
	"while"					{ return new Symbol(sym.WHILE); }
	"for"					{ return new Symbol(sym.FOR); }

	"int"					{ return new Symbol(sym.INT); }
	"float"					{ return new Symbol(sym.FLOAT); }
	"char"					{ return new Symbol(sym.CHAR); }
	"string"				{ return new Symbol(sym.STRING); }
	
	"'"						{ yybegin(CARACTER);
							  return new Symbol(sym.COMILLA_SIMPLE); }
	"\""					{ yybegin(CADENA);
							  return new Symbol(sym.COMILLA_DOBLE); }

	[A-Za-z][A-Za-z0-9_]*	{ return new Symbol(sym.IDENT, yytext() ); }

	0|[1-9][0-9]*			{ return new Symbol(sym.ENTERO, yytext() ); }

	(([0-9]+\. | \.[0-9]+ | [0-9]+\.[0-9]+) (E(-|\+)?[0-9]+)?) | ([0-9]+(E(-|\+)?[0-9]+))
							{ return new Symbol(sym.REAL, yytext() ); }

	\r|\n					{  }   
	\ |\t|\f				{  }

	[^]						{ throw new Error("Illegal character <"+yytext()+">"); }

}

<CARACTER> {
	"'"						{ yybegin(YYINITIAL);
							  return new Symbol(sym.COMILLA_SIMPLE); }

	.						{ return new Symbol(sym.ASCII, (int) yytext().charAt(0) +""); }
		\\[\\bnfrt\'\"]		{ String ascii = "";
							  switch (yytext().substring(1, 2)) {
								case "b":
							  		ascii = (int) '\b' +"";
							  		break;
							  	case "n":
							  		ascii = (int) '\n' +"";
							  		break;
							  	case "f":
							  		ascii = (int) '\f' +"";
							  		break;
							  	case "r":
							  		ascii = (int) '\r' +"";
							  		break;
							  	case "t":
							  		ascii = (int) '\t' +"";
							  		break;
							  	case "'":
							  		ascii = (int) '\'' +"";
							  		break;
							  	case "\"":
							  		ascii = (int) '\"' +"";
							  		break;
							  	case "\\":
							  		ascii = (int) '\\' +"";
							  		break;
							  	default:
							  		System.out.println("# ERROR: Caracter no reconocido");
							  		break;
							  }
							  return new Symbol(sym.ASCII, ascii); }
	\\u[0-9aAbBcCdDeEfF]{4}	{ return new Symbol(sym.ASCII, Integer.parseInt(yytext().substring(2,6), 16) +""); }

	[^]						{ throw new Error("Illegal character <"+yytext()+">"); }

}

<CADENA> {
	"\""					{ yybegin(YYINITIAL);
							  return new Symbol(sym.COMILLA_DOBLE); }

	.						{ return new Symbol(sym.ASCII, (int) yytext().charAt(0) +""); }
		\\[\\bnfrt\'\"]		{ String ascii = "";
							  switch (yytext().substring(1, 2)) {
								case "b":
							  		ascii = (int) '\b' +"";
							  		break;
							  	case "n":
							  		ascii = (int) '\n' +"";
							  		break;
							  	case "f":
							  		ascii = (int) '\f' +"";
							  		break;
							  	case "r":
							  		ascii = (int) '\r' +"";
							  		break;
							  	case "t":
							  		ascii = (int) '\t' +"";
							  		break;
							  	case "'":
							  		ascii = (int) '\'' +"";
							  		break;
							  	case "\"":
							  		ascii = (int) '\"' +"";
							  		break;
							  	case "\\":
							  		ascii = (int) '\\' +"";
							  		break;
							  	default:
							  		System.out.println("# ERROR: Caracter no reconocido");
							  		break;
							  }
							  return new Symbol(sym.ASCII, ascii); }
	\\u[0-9aAbBcCdDeEfF]{4}	{ return new Symbol(sym.ASCII, Integer.parseInt(yytext().substring(2,6), 16) +""); }

	[^]						{ throw new Error("Illegal character <"+yytext()+">"); }

}
