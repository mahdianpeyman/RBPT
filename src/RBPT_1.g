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
  {Manager.createDatatypesSortsFunctions();}
  {Manager.createMsgSort_Msg();}
  {Manager.createLocSortLocs() ;}
  {Manager.createActionSortAction() ;}
  {Manager.createVars () ;}
  {Manager.createMLFuncEQN () ;}
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
  CONS function+  
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
  VAR var+ 
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
  EQN equation+ 
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
  {
  Vector<String> msgs = new Vector<String>() ;
  Tuple t = new Tuple() ;
  }
  ID  { msgs.add($ID.text) ;} 
  (COMMA ID{msgs.add($ID.text);})* (COLON tuple {t = $tuple.value;} )? 
  {
    Manager.addMessages (msgs,t);
  }SEMIC
  ; /*s*/

acts
  :
  ACT act+ 
  ;

/*** action can have no parameter, There is no need to name the param ***/
act
  :
  {Vector <String> ids = new Vector <String> () ;
  Tuple t = new Tuple() ;}
  ID { ids.add ($ID.text) ;}
  (COMMA ID{ids.add($ID.text);})* 
  (COLON tuple {t = $tuple.value;})? SEMIC
  {Manager.addActions(ids,t) ;}
  ;

locs
  :
  {Vector<String> ids = new Vector <String> () ;}
  LOC ID {ids.add ($ID.text) ;}
    (COMMA ID{ids.add($ID.text) ;})* 
    {Manager.addLocations (ids) ;}SEMIC
    
  ;

procs
  :
  PROC proc+
  ; /*s*/

proc locals [Process p]
  :
  processDecl {$p= Manager.addProcessDeclaration($processDecl.value) ;}EQLS processTerm  {Manager.setProcessTerm ($p,$processTerm.value) ;} SEMIC
  ;

/*** proc section contains a list of process declaration ***/
processDecl returns [ProcessDeclaration value] locals [String i,String p, String ret;]
 @init {
  
 } :
  ID {$value = new ProcessDeclaration ($ID.text) ;}
  (
    LPAREN ID {$i=$ID.text;}COLON
    (
      ID {$p=$ID.text;}
      | LOCSORT{$p=$LOCSORT.text;}
    )
    { $ret=Manager.addParameterProcessDeclaration ($value,$i,$p);
      if ( $ret !=null )
        System.out.println ( $ret) ;
    }
    (
      COMMA ID ':'
      (
        ID {$p=$ID.text;}
        | LOCSORT{$p=$LOCSORT.text;}
      )
      { $ret=Manager.addParameterProcessDeclaration ($value,$i,$p);
      if ( $ret !=null )
        System.out.println ( $ret) ;
        }
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

processTerm returns [ProcessTerm value] :
    processTermChoice {$value=$processTermChoice.value;} ;
    
processTermChoice returns [ProcessTerm value]
  :
  left=processTermSingle right=pTP {$value=new ProcessTermChoice($left.value,$right.value) ;}
  ;

processTermSingle returns [ProcessTerm value]
  :
  sum {$value = $sum.value ;}
  | action DOT ap=processTermSingle {$value = new ProcessTermAction ($action.value,$ap.value) ;}
  | cond POINTER cl=processTerm ORELSE cr=processTermSingle {$value = new ProcessTermConditional($cond.value,$cl.value,$cr.value) ;}
  | pn=pName {$value=$pn.value;}
  | d=DELTA {$value=new ProcessTermDelta() ;}
  | LPAREN spt=processTerm RPAREN {$value=$spt.value;}
  ;

sum returns [ProcessTerm value]
  :
  {String var,sort ;}
  SUM ID {var = $ID.text;}COLON
  (
    ID {sort=$ID.text;}
    | LOCSORT {sort=$LOCSORT.text;}
  )
  '.' pts=processTermSingle {$value = Manager.retProcessTermSum  (var,sort,$pts.value) ;}
  ; /* first ID is a var name and second ID is its sort ***/

pTP returns [ProcessTerm value]
  :
  | PLUS l=processTermSingle r=pTP {$value = new ProcessTermChoice ($l.value,$r.value);}
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

cond returns [SimpleExpression value]
  :
  simpleExpression {$value= $simpleExpression.value ;}
  ; /* I made it more efficient  ***/

pName returns [ProcessTerm value]
  :
  instance {$value =  Manager.instanceToProcessTerm ($instance.value) ;}
  ; /* in semantics, process instantiation should be checked ***/ /*s What does it mean?*/

instance returns [Instance value]
  :
  {Vector<SimpleExpression> ses = new Vector<SimpleExpression> () ;}
  ID (LPAREN l=simpleExpression {ses.add($l.value) ;} (COMMA r=simpleExpression {ses.add($r.value);})* RPAREN)? 
  {$value=Manager.makeInstance ($ID.text,ses);}
  ; /* a map/action/process/msg/variable/Function instantiation can have zero/more than one parameter ***/

action returns [ActionUse value]
  :
  instance {$value = new ActionUseInstance ($instance.value) ;}
  | sndAction {$value = $sndAction.value ;}
  | rcvAction {$value = $rcvAction.value ;}
  ;

sndAction returns [ActionUse value] :
  SND LPAREN instance RPAREN {$value = new ActionUseSnd ( $instance.value ) ;}
  ;
rcvAction returns [ActionUse value]:
  RCV LPAREN instance RPAREN {$value = new ActionUseRcv ($instance.value) ;}
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
