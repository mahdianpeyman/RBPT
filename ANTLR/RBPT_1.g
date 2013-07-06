grammar RBPT_1;

//Rules : 

program : (sorts|funcs|maps|vars|eqns)* (act|msg)? locs proc? init;

sorts : SORT ID ( COMMA ID) * SEMIC;

funcs : CONS function+ ; /* I added a reserved word for giving constructors */ /*s*/

function : ID ( COMMA ID ) * COLON Type SEMIC /*s*/;

maps : MAP map+ ; /*s*/

map : ID COLON type SEMIC ; /* I added a reserved word for giving functions over ADTs */ /*s*/

type : touple? POINTER (ID|LOCSORT); /*** return type should be of a sort ('Loc' is a predefined sort), it should be checked in semantics ***/ /*s? does'nt ID match 'Loc' ? */

touple : (ID|LOCSORT) ( SHARP (ID|LOCSORT))*;      /*** I removed ID in definition of Type***/

vars : VAR var+; /*** vars should have a sort ***/ /*s*/

var : ID (COMMA ID) * COLON (ID|LOCSORT) SEMIC ; /*swhy Loc for the actual type? */

eqns : EQN equation+ ;

equation : simpleExpression EQN simpleExpression SEMIC ;

simpleExpression : ID | ID LPAREN simpleExpression (COMMA simpleExpression) * RPAREN ;

msgs : MSG msg+ ; /*** message can have no parameter, There is no need to name the param ***/ /*s no need or should'nt have?*/

msg : ID (COMMA ID)* (COLON Touple)? SEMIC ; /*s*/

acts : ACT act+ ;/*** action can have no parameter, There is no need to name the param ***/

act : ID ( COMMA ID)* (COLON touple)? SEMIC;

locs : LOC ID (COMMA ID)* SEMIC;

procs : PROC proc+ ;/*s*/

proc : processDecl EQN processTerm; /*** proc section contains a list of process declaration ***/

processDecl : ID (LPAREN ID COLON (ID|LOCSORT) (, ID ':' (ID|LOCSORT))* RPAREN )?; /*** a process name can have no parameter ***/

processTerm : DELTA |
            processTerm PLUS processTerm | /* + is left associative*/
            sum |
            action DOT processTerm |
            cond POINTER processTerm ORESLE processTerm |
            pName ;

networkTerm : deploy |
            networkTerm PARAL networkTerm | /* || is left associative*/
            hide |
            encap |
            abstract ;

/* Operator priorities:
    a.p > cond > sum > +
*/

cond : simpleExpression ;  /*** I made it more efficient  ***/

sum : SUM ID COLON (ID|LOCSORT) '.' ProcessTerm ;/*** first ID is a var name and second ID is its sort ***/

instance : ID (LPAREN simpleExpression (, simpleExpression)* RPAREN)?; /*** a process/action/msg instantiation can have zero/more than one parameter ***/

pName : instance ; /*** in semantics, process instantiation should be checked ***/ /*s What does it mean?*/

action : instance ; /*** in semantics, action instantiation should be checked ***/
        | SND LPAREN instance RPAREN | RCV LPAREN instance RPAREN ;/*** in semantics, msg instantiation should be checked ***/
                                                            /*** rcv action should be in context of sum operator, it can be checked either in semantics or forced by grammar*/

deploy : DEPLOY LPAREN ID COMMA processTerm  RPAREN;

hide : HIDE LPAREN ID COMMA networkTerm RPAREN; /* ID is of 'Loc' type, it should be checked in semantics */

encap : ENCPA LPAREN ID COMMA networkTerm RPAREN; /* ID is a message constructor, it should be checked in semantics */

abstract -> 'abs' '(' ID',' networkTerm ')'; /* ID is a message constructor, it should be checked in semantics */

init -> 'init' networkTerm ';'; /*** I added the reserved word and the ending ; ***/

// checked !!!



//Language signs:


//Signs

LPAREN  : '(';
RPAREN  : ')';
COMMA   : ',';
SEMIC   : ';';
COLON : ':';
POINTER : '->';
SHARP : '#';
PLUS  : '+';
DOT   : '.';
PARAL : '||';
ORELSE  : '<>';//OrElse?
EQLS  : '=';




//reserved_words

SORT  : 'sort';
CONS  : 'cons';
MAP   : 'map';
VAR   : 'var';
EQN   : 'eqn';
MSG   : 'msg';
ACT   : 'act';
PROC  : 'proc';
DELTA : 'delta';
SUM   : 'sum';
DEP   : 'dep';
HIDE  : 'hide';
ENCAP : 'encap';
SND   : 'snd';
RCV   : 'rcv';
LOCSORT   : 'Loc';
LOC   : 'loc' ;
MSG   : 'msg';
DEPLOY    : 'deploy';

//ID

ID  : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ; //As in C IDs

