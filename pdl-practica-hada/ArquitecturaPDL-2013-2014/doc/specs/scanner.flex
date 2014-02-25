/*  Autor: Paula Remirez Ruiz (premirez1)
    DNI:72680972Y
    Proyecto: Practica de Procesadores de Lenguaje (Curso 2013/2014)
    Analizador lexico para el lenguaje HAda
    Especificacion A
*/

package compiler.lexical;

import compiler.syntax.sym;
import compiler.lexical.Token;
import es.uned.lsi.compiler.lexical.ScannerIF;
import es.uned.lsi.compiler.lexical.LexicalError;
import es.uned.lsi.compiler.lexical.LexicalErrorManager;

// incluir aqui, si es necesario otras importaciones

%%
 
%public
%class Scanner
%char
%line
%column
%cup
%implements ScannerIF
%scanerror LexicalError
%ignorecase
%full
%notunix

// incluir aqui, si es necesario otras directivas
%state COMENTARIO_LINEA

%{
  LexicalErrorManager lexicalErrorManager = new LexicalErrorManager ();
  private int commentCount = 0;
  
  Token createToken (){
  	Token token = new Token (sym.MENOS);
    token.setLine (yyline + 1);
    token.setColumn (yycolumn + 1);
    token.setLexema (yytext ());
    return token;
  }
%}  


//Macros necesarias (Directivas)

LETRA = [A-Za-z] 										//Caracteres alfanumericos
DIGITO = [0-9] 											//Digitos
ESPACIO = [\ \t\r\n]+									//Espacios
CARACTERESCADENA = [^\"]* 								//Caracteres de una cadena
ENTERO = {DIGITO}+										//Valor entero
ID = {LETRA}({LETRA}|{DIGITO})*							//Identificador
COMENTARIOLINEA=--.*\r\n								//Comentario

%%

<YYINITIAL> 
{
/************************
** PALABRAS RESERVADAS
*************************/
//Declaracion de un vector                        
    "array"				{  
                           Token token = new Token (sym.ARRAY);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Comienzo de un bloque de sentencias en un subprograma 
    "begin"				{  
                           Token token = new Token (sym.BEGIN);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Tipo logico                      
    "Boolean"			{  
                           Token token = new Token (sym.BOOLEAN);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Declaracion de constantes simbolicas 
    "constant"				{  
                           Token token = new Token (sym.CONSTANT);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Comienzo del cuerpo de alternativa de una condicional if                       
    "else"				{  
                           Token token = new Token (sym.ELSE);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Final de subprograma o de una estructura de control
    "end"				{  
                           Token token = new Token (sym.END);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Constante logica que representa falso                    
    "false"				{  
                           Token token = new Token (sym.FALSE);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Comienzo de una funcion                        
    "function"			{  
                           Token token = new Token (sym.FUNCTION);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Comienzo de una sentencia condicional if
    "if"				{  
                           Token token = new Token (sym.IF);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Tipo entero
    "Integer"			{  
                           Token token = new Token (sym.INTEGER);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Comienzo del cuerpo de un subprograma y parte de la declaraci�n de un registro                     
    "is"				{  
                           Token token = new Token (sym.IS);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Comienzo/fin del bloque de sentencias de un bucle for y while
    "loop"				{  
                           Token token = new Token (sym.LOOP);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Parte de la declaraci�n de un vector                   
    "of"				{  
                           Token token = new Token (sym.OF);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Operador logico
    "or	"				{  
                           Token token = new Token (sym.OR);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Indica paso por referencia                   
    "out"				{  
                           Token token = new Token (sym.OUT);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Comienzo de un procedimiento  
    "procedure"				{  
                           Token token = new Token (sym.PROCEDURE);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Procedimiento predefinido para mostrar informaci�n por pantalla                      
    "Put_line"			{  
                           Token token = new Token (sym.PUTLINE);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Parte de la declaraci�n de una funci�n y sentencia de retorno                    
    "return"			{  
                           Token token = new Token (sym.RETURN);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Comienzo de bloque de sentencias en un if
    "then"			{  
                           Token token = new Token (sym.THEN);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Constante logica                   
    "True"				{  
                           Token token = new Token (sym.TRUE);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Declaracion de un tipo estructurado
    "type"				{  
                           Token token = new Token (sym.TYPE);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Comienzo de un bucle while                
    "while"				{  
                           Token token = new Token (sym.WHILE);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

/******************
** DELIMITADORES
*******************/
//Delimitador de constante literal de cadena
    "\""			    {  
                           Token token = new Token (sym.COMILLASDOBLES);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Delimitadores de expresiones, de parametros, rango y acceso a vectores
    "("				    {  
                           Token token = new Token (sym.PARENTESISIZQ);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
    ")"				    {  
                           Token token = new Token (sym.PARENTESISDER);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Delimitadores entre vectores de un rango de vector y de sentencia for
    ".."				    {  
                           Token token = new Token (sym.PUNTOPUNTO);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Delimitadores de comentario 
    "--"				    {  
                           Token token = new Token (sym.INICIOCOMENTARIO);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "\n"				    {  
                           Token token = new Token (sym.SALTOLINEA);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Delimitador en listas de identificadores
    ","				    {  
                           Token token = new Token (sym.COMA);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Delimitador en secuencias de sentencias y secuencias de parametros
    ";"				    {  
                           Token token = new Token (sym.PUNTOYCOMA);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Delimitador de tipo en una declaraci�n de variable o constante y en par�metros
    ":"				    {  
                           Token token = new Token (sym.DOSPUNTOS);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }  

                     
/**************
** OPERADORES
***************/
//ARITM�TICOS 
//Resta aritmetica
   "-"                {  
                           Token token = new Token (sym.MENOS);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Suma aritmetica
   "+"                {  
                           Token token = new Token (sym.MAS);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//RELACIONALES
//Menor
   "<"                {  
                           Token token = new Token (sym.MENORQUE);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Mayor
   ">"                {  
                           Token token = new Token (sym.MAYORQUE);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Distinto
   "/="                {  
                           Token token = new Token (sym.DISTINTOQUE);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//Igual
   "="                {  
                           Token token = new Token (sym.IGUALQUE);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//LOGICOS
//Conjuncion logica
   "and"                {  
                           Token token = new Token (sym.AND);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
//DE ASIGNACION
//Asignacion
   ":="                {  
                           Token token = new Token (sym.ASIGNACION);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }                        
//DE ACCESO
//Acceso al campo de un registro
   "."                {  
                           Token token = new Token (sym.PUNTO);
                           token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }  
                        
/*************************************************************************************************************
** PATRONES
*************************************************************************************************************/
		\"{CARACTERESCADENA}\"  {  Token token = new Token (sym.CARACTERESCADENA); 
							token.setLine (yyline + 1); 
							token.setColumn (yycolumn + 1); 
							token.setLexema (yytext ()); 
							return token;
						}
		

        {ID} {
                            Token token = new Token (sym.ID);
                            token.setLine (yyline + 1);
                            token.setColumn (yycolumn + 1);
                            token.setLexema (yytext ());
                            return token;
                        }
        {ENTERO} {
                            Token token = new Token (sym.ENTERO);
                            token.setLine (yyline + 1);
                            token.setColumn (yycolumn + 1);
                            token.setLexema (yytext ());
                            return token;
                        }

	{ESPACIO}  { } //ESPACIOS EN BLANCO (NO HACEMOS NADA) //
         
    {COMENTARIOLINEA} { } //NO HACEMOS NADA

   //     {CADENA}        {
   //                         Token token = new Token (sym.tCADENA);
   //                         token.setLine (yyline + 1);
   //                         token.setColumn (yycolumn + 1);
   //                         token.setLexema (yytext ());
   //                         return token;
   //                     }

  //      \"{CADENACTE}   {
  //                          LexicalError error = new LexicalError();
  //                          error.setLine (yyline + 1);
  //                          error.setColumn (yycolumn + 1);
  //                          error.setLexema (yytext ());
  //                          lexicalErrorManager.lexicalError (error);
  //                          lexicalErrorManager.lexicalInfo("Cadena no finalizada \"" + yytext()+"\"");
  //                      } 
  
    
    // incluir aqui el resto de las reglas patron - accion
    
    
    // error en caso de coincidir con ning�n patr�n
	[^]     
                        {                                               
                           LexicalError error = new LexicalError ();
                           error.setLine (yyline + 1);
                           error.setColumn (yycolumn + 1);
                           error.setLexema (yytext ());
                           lexicalErrorManager.lexicalError (error);
                        }
    
}//Fin YYNITIAL
//<COMENTARIO_LINEA>
//{
//	{SALTO_LINEA}	{ yybegin(YYINITIAL); }
//		
//	// Cualquier otra cosa
//	[^]               {}    
//}


