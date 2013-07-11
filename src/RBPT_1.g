grammar RBPT_1;

@header {
import java.util.*;
}

@members {
/** Map variable name to Integer object holding value */
}

@init {

}
//Rules :

program
  :
  (
    sorts
    | funcs
    | maps
    | vars
    | eqns
  )*
  msgs? acts? locs procs? init
  ;

sorts
  :
  SORT ID 
         {
         Manager.addSort($ID.text);
         }
  (COMMA ID 
           {
            Manager.addSort($ID.text) ;
           })* SEMIC
  ;

funcs
  :
  CONS function+ {Manager.createDatatypesSortsFucntions();} 
  ; 
  
  /* I added a reserved word for giving constructors */ /*s*/

function
  :
  {
   Vector<String> tempF = new Vector<String>();
  }
  ID 
    {
     tempF.add($ID.text);
    }
  (COMMA ID 
           {
            tempF.add($ID.text);
           })* COLON type {Manager.addFunctions (tempF,$type.value) ;}SEMIC /*s*/
  ;

maps
  :
  MAP map+
  ; /*s*/

map
  :
  ID COLON type {Manager.addMap ($ID.text,$type.value);}SEMIC
  ; /* I added a reserved word for giving functions over ADTs */ /*s*/

type returns [Type value] locals [String tS=""]
@init {
$value = new Type();
}
  :
  (tuple {
    $value.setFirst($tuple.value) ;
  })?
  POINTER
  (
    ID {$tS = $ID.text;}
    | LOCSORT {$tS = $ID.text;}
  )
  {
      String ret = $value.setSecond ($tS) ;
      if ( ret != null ) 
        System.out.println( ret ) ;  
  }
  ;

/*** return type should be of a sort ('Loc' is a predefined sort), it should be checked in semantics ***/ /*s? does'nt ID match 'Loc' ? */
tuple returns [Tuple value] locals [String tS=""]
@init {
$value = new Tuple();
}
  :
  (
    ID {$tS = $ID.text;}
    | LOCSORT {$tS = $LOCSORT.text;}
  )
  {
      String ret = $value.addSort($tS)  ;
      if ( ret != null )
        System.out.println (ret) ;
      
  }
  (
    SHARP
    (
    ID {$tS = $ID.text;}
    | LOCSORT {$tS = $LOCSORT.text;}
  )
  {
      ret = $value.addSort($tS)  ;
      if ( ret != null)
        System.out.println (ret) ;
      
  }
  )*
  ;

/*** I removed ID in definition of Type***/
vars 
  :
  VAR var+ { Manager.createVars () ;}
  ;

/*** vars should have a sort ***/ /*s*/
var locals[String tS=""]
  :
  {
   Vector<String> tempV = new Vector<String>();
  }
  
  ID {tempV.add($ID.text);}(COMMA ID{tempV.add($ID.text);})* COLON
  (
    ID {$tS=$ID.text;}
    | LOCSORT {$tS=$LOCSORT.text;}
  )
  {
    String ret = Manager.addVariables(tempV,$tS) ;
    if ( ret != null ) 
        System.out.println( ret) ; 
  }
  SEMIC
  ; /*swhy Loc for the actual type? */

eqns
  :
  EQN equation+ {Manager.createMLFuncEQN () ;}
  ;

equation locals [SimpleExpression left]

  :
  simpleExpression {$left = $simpleExpression.value;}EQLS simpleExpression {Manager.addEquation($left,$simpleExpression.value);}SEMIC
  ;

simpleExpression returns [SimpleExpression value] 
@init {
}
  :
  ID 
  {
    SimpleExpression se = Manager.setSimpleExpression($ID.text);
    if ( se == null ) 
      System.out.println ( Manager.simpleExpressionError($ID.text) ) ;
    else
      $value = se ;
    
  }
  (LPAREN left=simpleExpression {$value.addExpr($left.value);}
    (COMMA right=simpleExpression {$value.addExpr($right.value);})* 
  RPAREN)?
  ;

msgs
  :
  MSG msg+
  ;

/*** message can have no parameter, There is no need to name the param ***/ /*s no need or should'nt have?*/
msg
  :
  ID (COMMA ID)* (COLON tuple)? SEMIC
  ; /*s*/

acts
  :
  ACT act+
  ;

/*** action can have no parameter, There is no need to name the param ***/
act
  :
  ID (COMMA ID)* (COLON tuple)? SEMIC
  ;

locs
  :
  LOC ID (COMMA ID)* SEMIC
  ;

procs
  :
  PROC proc+
  ; /*s*/

proc
  :
  processDecl EQLS processTerm SEMIC
  ;

/*** proc section contains a list of process declaration ***/
processDecl
  :
  ID
  (
    LPAREN ID COLON
    (
      ID
      | LOCSORT
    )
    (
      COMMA ID ':'
      (
        ID
        | LOCSORT
      )
    )*
    RPAREN
  )?
  ;

/*** a process name can have no parameter ***/
/*processTerm : DELTA|
            processTerm PLUS processTerm | // + is left associative
            sum |
            action DOT processTerm |
            cond POINTER processTerm ORESLE processTerm |
            pName ;
*/

processTerm
  :
  processTermSingle pTP
  ;

processTermSingle
  :
  sum
  | action DOT processTermSingle
  | cond POINTER processTerm ORELSE processTermSingle
  | pName
  | DELTA
  | LPAREN processTerm RPAREN
  ;

sum
  :
  SUM ID COLON
  (
    ID
    | LOCSORT
  )
  '.' processTermSingle
  ; /* first ID is a var name and second ID is its sort ***/

pTP
  :
  | PLUS processTermSingle pTP
  ;
/*networkTerm : deploy |
            networkTerm PARAL networkTerm | // || is left associative
            hide |
            encap |
            abs ;
*/


networkTerm
  :
  networkTermSingle nTP
  ;

networkTermSingle
  :
  deploy
  | hide
  | encap
  | abs
  | LPAREN networkTerm RPAREN
  ;

nTP
  :
  | PARAL networkTermSingle nTP
  ;

deploy
  :
  DEPLOY LPAREN ID COMMA processTerm RPAREN
  ;

hide
  :
  HIDE LPAREN ID COMMA networkTerm RPAREN
  ; /* ID is of 'Loc' type, it should be checked in semantics */

encap
  :
  ENCAP LPAREN ID COMMA networkTerm RPAREN
  ; /* ID is a message constructor, it should be checked in semantics */

abs
  :
  ABS LPAREN ID COMMA networkTerm RPAREN
  ; /* ID is a message constructor, it should be checked in semantics */

init
  :
  INIT networkTerm SEMIC
  ;

/*** I added the reserved word and the ending ; ***/
/* Operator priorities:
    a.p > cond > sum > +
*/

cond
  :
  simpleExpression
  ; /* I made it more efficient  ***/

pName
  :
  instance
  ; /* in semantics, process instantiation should be checked ***/ /*s What does it mean?*/

instance
  :
  ID (LPAREN simpleExpression (COMMA simpleExpression)* RPAREN)?
  ; /* a process/action/msg instantiation can have zero/more than one parameter ***/

action
  :
  instance
  | SND LPAREN instance RPAREN
  | RCV LPAREN instance RPAREN
  ;
/* in semantics, action instantiation should be checked ***/
/* in semantics, msg instantiation should be checked ***/
/* rcv action should be in context of sum operator, it can be checked either in semantics or forced by grammar*/

// checked !!!

//Language signs:

//Signs

LPAREN
  :
  '('
  ;

RPAREN
  :
  ')'
  ;

COMMA
  :
  ','
  ;

SEMIC
  :
  ';'
  ;

COLON
  :
  ':'
  ;

POINTER
  :
  '->'
  ;

SHARP
  :
  '#'
  ;

PLUS
  :
  '+'
  ;

DOT
  :
  '.'
  ;

PARAL
  :
  '||'
  ;

ORELSE
  :
  '<>'
  ; //OrElse?

EQLS
  :
  '='
  ;

//reserved_words

SORT
  :
  'sort'
  ;

CONS
  :
  'cons'
  ;

MAP
  :
  'map'
  ;

VAR
  :
  'var'
  ;

EQN
  :
  'eqn'
  ;

MSG
  :
  'msg'
  ;

ACT
  :
  'act'
  ;

PROC
  :
  'proc'
  ;

DELTA
  :
  'delta'
  ;

SUM
  :
  'sum'
  ;

DEP
  :
  'dep'
  ;

HIDE
  :
  'hide'
  ;

ENCAP
  :
  'encap'
  ;

SND
  :
  'snd'
  ;

RCV
  :
  'rcv'
  ;

LOCSORT
  :
  'Loc'
  ;

LOC
  :
  'loc'
  ;

DEPLOY
  :
  'deploy'
  ;

ABS
  :
  'abs'
  ;

INIT
  :
  'init'
  ;

//ID

ID
  :
  (
    'a'..'z'
    | 'A'..'Z'
    | '_'
  )
  (
    'a'..'z'
    | 'A'..'Z'
    | '0'..'9'
    | '_'
  )*
  ; //As in C IDs

WS
  :
  (
    ' '
    | '\t'
    | '\r'
    | '\n'
  )+
    -> skip
  ;
