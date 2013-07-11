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
          Sort s = new Sort($ID.text);
          SortSingleton.getInstance().addSort(s);
          System.out.println($ID.text);
         }
  (COMMA ID 
           {
            s = new Sort($ID.text);
            SortSingleton.getInstance().addSort(s);
            System.out.println($ID.text);
           })* SEMIC
  ;

funcs
  :
  CONS function+
  ; /* I added a reserved word for giving constructors */ /*s*/

function
  :
  
  {
   Vector<Function> tempF = new Vector<Function>();
  }
  ID 
    {
     tempF.add(new Function($ID.text));
    }
  (COMMA ID 
           {
            tempF.add(new Function($ID.text));
           })* COLON type {Manager.addFuntions (tempF,type) ;}SEMIC /*s*/
  ;

maps
  :
  MAP map+
  ; /*s*/

map
  :
  ID COLON type SEMIC
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
      Sort tempS = SortSingleton.getInstance().getSort ($tS) ;
      if (tempS == null ) 
        System.out.println ( "not a valid Sort " ) ;
      else
            $value.setSecond(tempS) ;
      
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
      Sort tempS = SortSingleton.getInstance().getSort ($tS) ;
      if (tempS == null ) 
        System.out.println ( "not a valid Sort " ) ;
      else
            $value.addSort(tempS) ;
      
  }
  (
    SHARP
    (
    ID {$tS = $ID.text;}
    | LOCSORT {$tS = $LOCSORT.text;}
  )
  {
      tempS = SortSingleton.getInstance().getSort ($tS) ;
      if (tempS == null ) 
        System.out.println ( "not a valid Sort " ) ;
      else
        $value.addSort(tempS) ;
  }
  )*
  ;

/*** I removed ID in definition of Type***/
vars
  :
  VAR var+
  ;

/*** vars should have a sort ***/ /*s*/
var
  :
  ID (COMMA ID)* COLON
  (
    ID
    | LOCSORT
  )
  SEMIC
  ; /*swhy Loc for the actual type? */

eqns
  :
  EQN equation+
  ;

equation
  :
  simpleExpression EQLS simpleExpression SEMIC
  ;

simpleExpression
  :
  ID (LPAREN simpleExpression (COMMA simpleExpression)* RPAREN)?
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
