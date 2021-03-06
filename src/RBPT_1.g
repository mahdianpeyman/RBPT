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
  {Manager.enterContext () ;}
  (
    sorts
    | funcs
    | maps
    | vars
    | eqns
  )*
  msgs? acts? locs procs? init
  {Manager.createML() ;}
  {Manager.exitContext() ;}
  {Manager.finalTest() ;}
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
  $value=Manager.retType () ;
}
  :
  (tuple {
    Manager.setTypeFirst($value,$tuple.value);
  })?
  POINTER
  (
    ID {$tS = $ID.text;}
    | LOCSORT {$tS = $ID.text;}
  )
  { Manager.setTypeSecond($value,$tS) ;   }
  ;

/*** return type should be of a sort ('Loc' is a predefined sort), it should be checked in semantics ***/ /*s? does'nt ID match 'Loc' ? */
tuple returns [Tuple value] locals [String tS=""]

  :
  {Vector <String> strs = new Vector <String>();}
  (
    ID {$tS = $ID.text;}
    | LOCSORT {$tS = $LOCSORT.text;}
  )
  { strs.add($tS) ;}
  (
    SHARP
    (
    ID {$tS = $ID.text;}
    | LOCSORT {$tS = $LOCSORT.text;}
  )
  { strs.add($tS) ;}
  )*
  { $value = Manager.retTupleSortList(strs) ;}
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
    Manager.addVariables(tempV,$tS) ; 
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
  :
  instance {$value=$instance.value ;}
  ;
  /*
  {Vector<SimpleExpression> args = new Vector<SimpleExpression> () ;}
  (ID {$value = Manager.retSimpleExpressionSingle ($ID.text);}
  | (ID LPAREN left=simpleExpression {args.add($left.value);}
      ( COMMA right=simpleExpression {args.add($right.value);})*
       {$value=Manager.retSimpleExpressionComplex($ID.text,args);} 
  RPAREN  ))
  ;
  */

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
  {Manager.enterContext();}
  processDecl {$p= Manager.addProcessDeclaration($processDecl.value) ;}
    EQLS processTerm  {Manager.setProcessTerm ($p,$processTerm.value) ;} SEMIC
  {Manager.exitContext();}
  ;

/*** proc section contains a list of process declaration ***/
processDecl returns [ProcessDeclaration value] locals [String i,String p, String ret;]
 @init {
  
 } :
  ID {$value = Manager.retProcessDeclaration ($ID.text) ;}
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
      COMMA ID {$i=$ID.text;} ':'
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
  //left=processTermSingle right=pTP 
  left=pTP right=processTermSingle
    { //if ($right.value==null) $value = $left.value;
        if ($left.value==null) $value = $right.value ;
    else $value=new ProcessTermChoice($left.value,$right.value) ;}
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

pName returns [ProcessTerm value]
  :
  instance {$value =  Manager.instanceToProcessTerm ($instance.value) ;}
  ; /* in semantics, process instantiation should be checked ***/ /*s What does it mean?*/
  
sum returns [ProcessTerm value]
  :
  {Manager.enterContext() ;}
  {String var,sort ;}
  SUM ID {var = $ID.text;}COLON
  (
    ID {sort=$ID.text;}
    | LOCSORT {sort=$LOCSORT.text;}
  ) {Manager.addProcessTermSumVariable (var,sort);}
  '.' pts=processTermSingle {$value = Manager.retProcessTermSum  (var,sort,$pts.value) ;}
  {Manager.exitContext();}
  ; /* first ID is a var name and second ID is its sort ***/

pTP returns [ProcessTerm value]
@init{
  $value = null ;
}
  :
  | //PLUS l=processTermSingle r=pTP
    l = pTP r=processTermSingle PLUS
    { //if ($r.value == null) $value = $l.value ;
        if ($l.value == null) $value = $r.value ;
      else $value = new ProcessTermChoice ($l.value,$r.value);
    }
  ;
/*networkTerm : deploy |
            networkTerm PARAL networkTerm | // || is left associative
            hide |
            encap |
            abs ;
*/


networkTerm returns [NetworkTerm value]
  :
  ntp=networkTermParallel {$value = Manager.retNetworkTermSingle($ntp.value);}
  
  ;

networkTermParallel returns [NetworkTerm value]:
  l=networkTermSingle r=nTP
  {if ($r.value != null) $value =Manager.retNetworkTermParallel ($l.value,$r.value);
   else $value=Manager.retNetworkTermSingle($l.value) ;}
  ;

networkTermSingle returns [NetworkTerm value]
  :
  (deploy {$value=$deploy.value;}
  | hide {$value=$hide.value;}
  | encap {$value=$encap.value;}
  | s=abs {$value=$abs.value;}
  | LPAREN nt=networkTerm {$value=$nt.value;} RPAREN)
  
  ;

nTP returns [NetworkTerm value] 
@init{
$value = null ;
}
:
  | PARAL l=networkTermSingle r=nTP
  {if ($r.value != null) $value =Manager.retNetworkTermParallel ($l.value,$r.value);
   else $value =Manager.retNetworkTermSingle($l.value) ;}
  ;

deploy returns [NetworkTerm value]
  :
  DEPLOY LPAREN ID COMMA processTerm RPAREN {$value = Manager.retNetworkTermDeploy($ID.text,$processTerm.value);}
  ; /* ID is of 'Loc' type, it should be checked in semantics */

hide returns [NetworkTerm value]
  :
  HIDE LPAREN ID COMMA networkTerm RPAREN {$value = Manager.retNetworkTermHide($ID.text,$networkTerm.value);}
  ; /* ID is of 'Loc' type, it should be checked in semantics */

encap returns [NetworkTerm value]
  :
  ENCAP LPAREN ID COMMA networkTerm RPAREN {$value = Manager.retNetworkTermEncap ($ID.text,$networkTerm.value);}
  ; /* ID is a message constructor, it should be checked in semantics */

abs returns [NetworkTerm value]
  :
  ABS LPAREN ID COMMA networkTerm RPAREN {$value = Manager.retNetworkTermAbs ($ID.text,$networkTerm.value);}
  ; /* ID is a message constructor, it should be checked in semantics */

init
  :
  INIT networkTerm {Manager.setInitial($networkTerm.value);} SEMIC
  ;

/*** I added the reserved word and the ending ; ***/
/* Operator priorities:
    a.p > cond > sum > +
*/

cond returns [SimpleExpression value]
  :
  simpleExpression {$value= $simpleExpression.value ;}
  ; /* I made it more efficient  ***/



instance returns [Instance value]
  :
  {Vector<SimpleExpression> ses = new Vector<SimpleExpression> () ;}
  ID (LPAREN l=simpleExpression {ses.add($l.value) ;} (COMMA r=simpleExpression {ses.add($r.value);})* RPAREN)? 
  {$value=Manager.makeInstance ($ID.text,ses);}
  ; /* a map/action/process/msg/variable/Function instantiation can have zero/more than one parameter ***/

action returns [Instance value]
  :
  instance {$value =  $instance.value;}
  | sndAction {$value = $sndAction.value ;}
  | rcvAction {$value = $rcvAction.value ;}
  ;

sndAction returns [Instance value] :
  SND LPAREN instance RPAREN {$value = Manager.makeInstanceSND ( $instance.value ) ;}
  ;
rcvAction returns [Instance value]:
  RCV LPAREN instance RPAREN {$value = Manager.makeInstanceRCV ($instance.value) ;}
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
