grammar RBPT_1;

//Rules : 

program : (sorts|funcs|maps|vars|eqns)* (act|msg)? locs proc? init

sorts : 'sort' ID ( ',' ID) * ';'

funcs : 'cons' function+ /* I added a reserved word for giving constructors */ /*s*/

function : ID ( ',' ID ) * ':' Type ';' /*s*/

maps -> 'map' map+ /*s*/

map -> ID ':' type ';' /* I added a reserved word for giving functions over ADTs */ /*s*/

type ->  touple? '->' (ID|'Loc') /*** return type should be of a sort ('Loc' is a predefined sort), it should be checked in semantics ***/ /*s? does'nt ID match 'Loc' ? */

.Touple -> (ID|'Loc') ( '#' (ID|'Loc'))*      /*** I removed ID in definition of Type***/

.Vars -> 'var' Var+ /*** vars should have a sort ***/ /*s*/

.Var -> ID (',' ID) * ':' (ID|'Loc') ';' /*swhy Loc for the actual type? */

.Eqns -> 'eqn' Equation+

.Equation -> SimpleExpression = SimpleExpression ';'

.SimpleExpression -> ID | ID '(' SimpleExpression (',' SimpleExpression) * ')'

.Msgs -> 'msg' Msg+ /*** message ..can have no parameter, There is no need to name the param ***/ /*s no need or should'nt have?*/

.Msg -> ID (','ID)* (':' Touple)? ';' /*s*/

.Acts -> 'act' Act+ /*** action can have no parameter, There is no need to name the param ***/

.Act -> ID (','ID)* (':' Touple)? ';'

.Locs -> 'loc' ID (','ID)* ';'

.Procs -> 'proc' Proc+ /*s*/

.Proc -> processDecl '=' processTerm /*** proc section contains a list of process declaration ***/

.processDecl -> ID ('(' ID ':' (ID|'Loc') (, ID ':' (ID|'Loc'))* ')' )? /*** a process name can have no parameter ***/

.ProcessTerm -> 'delta' |
            ProcessTerm '+' ProcessTerm | /* + is left associative*/
            Sum |
            Action '.' ProcessTerm |
            Cond '->' ProcessTerm '<>' ProcessTerm |
            PName

.NetworkTerm -> Deploy |
            NetworkTerm '||' NetworkTerm | /* || is left associative*/
            Hide |
            Encap |
            Abstract

/* Operator priorities:
    a.p > cond > sum > +
*/

.Cond ->  SimpleExpression  /*** I made it more efficient  ***/

.Sum -> 'sum' ID ':' (ID|'Loc') '.' ProcessTerm /*** first ID is a var name and second ID is its sort ***/

.Instance - > ID ('(' SimpleExpression (, SimpleExpression)* ')')? /*** a process/action/msg instantiation can have zero/more than one parameter ***/

.PName -> Instance /*** in semantics, process instantiation should be checked ***/ /*s What does it mean?*/

.Action -> Instance /*** in semantics, action instantiation should be checked ***/
        | 'snd' '(' Instance ')' | 'rcv' '(' Instance ')' /*** in semantics, msg instantiation should be checked ***/
                                                            /*** rcv action should be in context of sum operator, it can be checked either in semantics or forced by grammar*/

.Deploy -> 'deploy' '(' ID ', 'ProcessTerm  ')'

.Hide -> 'hide' '(' ID ',' NetworkTerm ')' /* ID is of 'Loc' type, it should be checked in semantics */

.Encap -> 'encap' '(' ID ',' NetworkTerm ')' /* ID is a message constructor, it should be checked in semantics */

.Abstract -> 'abs' '(' ID',' NetworkTerm ')' /* ID is a message constructor, it should be checked in semantics */

.Init -> 'init' NetworkTerm ';' /*** I added the reserved word and the ending ; ***/

//

sorts : SORT ID ( COMMA ID) * SEMIC ;


funcs : function + SEMIC ;

function : ID COLON type ;

type :  ID |
    tuple? COLON tuple ;

tuple : ID ( SHARP ID ) * ;
     /* inja ebham hast chon age tuple ye ID khaali baashe, ba ID ke alternative Type hast ghati mishe */

maps : map + SEMIC ;

map : ID ( COMMA ID ) * COLON type ;

vars : VAR ID (COMMA ID) * SEMIC ;

eqns : EQN equation + ;

//equation : simpleExpression = simpleExpression SEMIC ;
equation : simpleExpression EQLS simpleExpression SEMIC ;

simpleExpression : ID | ID LPAREN simpleExpression (COMMA simpleExpression) * RPAREN ;

msg : MSG (ID COLON sig)(COMMA ID COLON sig)* SEMIC ;

sig : LPAREN ID ( SHARP ID ) * RPAREN ;

act : ACT (ID COLON sig) (COMMA ID COLON sig)* SEMIC ;

proc : PROC processTerm ;

processTerm : DELTA | 
  processTerm PLUS processTerm |
  processTerm PARAL processTerm |
  sum |
  action DOT processTerm |
  cond GIVES processTerm ORELSE processTerm |
  pName |
  deploy |
  hide |
  encap ;

      /* maslaeye olaviate amal gar ha ro bayad dorost konim inja be in soorat ke : 
  a.p   >   sum   >   ||  >   + 
*/ 

cond : dataTerm ;

dataTerm : simpleExpression ;

sum : SUM COLON ID DOT processTerm ; // oh, god! why?

pName : ID | ID LPAREN simpleExpression RPAREN ;

/* in jahaei ke ... ro daghigh motmaen nistam khanoom doctor chi madde nazareshoone vali ba farze inke ina khalian felan mibarim jelo karo */ 

action : ID | ID LPAREN /*...*/ RPAREN |
    SND LPAREN ID RPAREN | SND LPAREN ID LPAREN /*...*/ RPAREN RPAREN |
    RCV LPAREN ID RPAREN | RCV LPAREN ID LPAREN /*...*/ RPAREN RPAREN ;


//deploy : DEP LPAREN PT COMMA loc RPAREN ;
deploy : DEP LPAREN processTerm COMMA loc RPAREN ;

loc : ID; //ridims

hide : HIDE LPAREN /*...*/ COMMA processTerm RPAREN ;

encap : ENCAP LPAREN /*...*/ COMMA processTerm RPAREN ;

init : processTerm ;


//Language signs:


//Signs

LPAREN  : '(';
RPAREN  : ')';
COMMA   : ',';
SEMIC   : ';';
COLON : ':';
GIVES : '->';//Gives?
SHARP : '#';
PLUS  : '+';
DOT   : '.';
PARAL : '||';
ORELSE  : '<>';//OrElse?
EQLS  : '=';




//reserved_words

SORT  : 'sort';
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

//ID

ID  : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ; //As in C IDs

