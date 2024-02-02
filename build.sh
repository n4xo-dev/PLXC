#!/bin/sh
echo "BUILD:";
rm *.class;
rm Yylex.java;
rm sym.java;
rm parser.java;
jflex $1.flex &&
cup $1.cup &&
javac *.java &&
echo "RUN:" &&
java $1 $2 &&
echo "PACKAGE:" &&
zip $1.zip *.flex *.java *.cup;