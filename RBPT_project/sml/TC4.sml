Control.Print.printDepth := 100;

(* All datatypes are created from sorts and funcs*)
datatype Bool = true_Bool
 | false_Bool
;
datatype Nat = zero_Nat
 | succ_Nat of Nat
;
datatype Loc = unknown_Loc
 | l1_Loc
 | l2_Loc
|adr_1
|adr_2
|lx_3
;

(* Msg created from msgs rule *)
datatype Msg  = ack_Msg
 | rep_Msg of Loc * Loc
 | req_Msg of Loc
 ; 

(* Msg_Type created from msgs rule *)
datatype Msg_Type  = ack_Msg_t
 | rep_Msg_t
 | req_Msg_t
 ; 

(* Msg_Type_equal function *)
 fun Msg_Type_equal (ack_Msg_t , ack_Msg_t ) = true
 | Msg_Type_equal (rep_Msg_t , rep_Msg_t ) = true
 | Msg_Type_equal (req_Msg_t , req_Msg_t ) = true
 | Msg_Type_equal (x,y) = false
 ; 

(*Pair Datatype*)
datatype Pair = conn of Loc * Loc
 | disconn of Loc * Loc
;

(* NC Datatype *) 
datatype NC = empNC 
 | inn of Pair*NC
 ;

(* NetworkAction Datatype *)
datatype NetworkAction = nsnd_NA of Msg * Loc
 | nrcv_NA of Msg
 | tau_NA
 ; 

(* Action created from acts rule*)
datatype Action  = snd_Action of Msg
 | rcv_Action of Msg
 | succ_Action
 | na_Action of NC*NetworkAction
 ; 

(* VarName Datatype*)
datatype VarName 
 = Bool_Var of Bool
 | Nat_Var of Nat
 | Loc_Var of Loc
 | Msg_Var of Msg
 | Action_Var of Action
;

(* Datatype RecName*) 
datatype RecName 
 = X_RecName
 | Y_RecName
 | Init_RecName
 | rn_RecName of int
;

(* Datatype RecVar*) 
 datatype RecVar = rv of RecName*VarName list; 

(* DataType Porcess*)
datatype Process = p_nil
| p_choice of Process * Process
| p_prefix of Action * Process
| p_cond of Bool * Process*Process
| p_recvar of RecVar
| p_sum of VarName * Process
| n_encap of Msg_Type * Process
| n_abs of Msg_Type * Process
| n_hide of Loc * Process
| n_deploy of Loc * Process
| n_parallel of Process * Process
;

(* datatype RSE *)
datatype RSE = def of RecVar*Process;

(*SameSort function*)
fun SameSort ( Bool_Var (x) , Bool_Var(y) ) = true 
 | SameSort ( Nat_Var (x) , Nat_Var(y) ) = true 
 | SameSort ( Loc_Var (x) , Loc_Var(y) ) = true 
 | SameSort ( Msg_Var (x) , Msg_Var(y) ) = true 
 | SameSort ( Action_Var (x) , Action_Var(y) ) = true 
 | SameSort (x,y) = false	
 ;

(* isType Function *)
fun  isType ( ack_Msg_t , ack_Msg) = true
 |  isType ( rep_Msg_t , rep_Msg(x0,x1)) = true
 |  isType ( req_Msg_t , req_Msg(x0)) = true
 |  isType(x,y) = false
 ; 

(* Equations of add_map*)
fun add_map ( zero_Nat , m_0 ) = m_0
 | add_map ( succ_Nat ( n_0 ) , m_0 ) = succ_Nat ( add_map ( n_0 , m_0 ) )
 ; 

(* Equations of not_map*)
fun not_map ( true_Bool ) = false_Bool
 | not_map ( false_Bool ) = true_Bool
 ; 

(* Equations of and_map*)
fun and_map ( true_Bool , true_Bool ) = true_Bool
 | and_map ( x_0 , false_Bool ) = false_Bool
 | and_map ( false_Bool , x_0 ) = false_Bool
 ; 

 (* equal function for Sort  <Bool> :*)
fun Bool_equal( true_Bool , true_Bool) = true 
 | Bool_equal( false_Bool , false_Bool) = true 
| Bool_equal (x,y) = false 
;

 (* equal function for Sort  <Nat> :*)
fun Nat_equal( zero_Nat , zero_Nat) = true 
 | Nat_equal( succ_Nat( x1)  , succ_Nat( y1) ) = true  andalso Nat_equal( x1 , y1) 
| Nat_equal (x,y) = false 
;

 (* equal function for Sort  <Loc> :*)
fun Loc_equal( unknown_Loc , unknown_Loc) = true 
 | Loc_equal( l1_Loc , l1_Loc) = true 
 | Loc_equal( l2_Loc , l2_Loc) = true 
| Loc_equal (x,y) = false 
;

 (* equal function for Sort  <Msg> :*)
fun Msg_equal( ack_Msg , ack_Msg) = true 
 | Msg_equal( rep_Msg( x1 , x2)  , rep_Msg( y1 , y2) ) = true  andalso Loc_equal( x1 , y1)  andalso Loc_equal( x2 , y2) 
 | Msg_equal( req_Msg( x1)  , req_Msg( y1) ) = true  andalso Loc_equal( x1 , y1) 
| Msg_equal (x,y) = false 
;

(* p_equal fun *) 
fun p_equal(conn(x1,x2),conn(x3,x4)) = Loc_equal(x1,x3) andalso Loc_equal(x2,x4) 
 | p_equal(disconn(x1,x2),disconn(x3,x4)) = Loc_equal(x1,x3) andalso Loc_equal(x2,x4)
 | p_equal(x,y) = false
 ;

(* nc_include *)
fun nc_include(p,empNC) = false 
 | nc_include(p1,inn(p2,nc)) = p_equal(p1,p2) orelse nc_include(p1,nc);

(*nc_equal function*)
fun nc_equal(empNC,empNC) = true 
 | nc_equal(empNC,inn(p,nc)) = false
 | nc_equal(inn(p,nc),empNC) = false
 | nc_equal(inn(p,nc1),nc2) = nc_include(p,nc2) andalso nc_equal(nc1,nc2)
 ;

 (* equal function for Sort  <Action> :*)
fun Action_equal( snd_Action( x1)  , snd_Action( y1) ) = true  andalso Msg_equal( x1 , y1) 
| Action_equal( rcv_Action( x1)  , rcv_Action( y1) ) = true  andalso Msg_equal( x1 , y1) 
| Action_equal( succ_Action , succ_Action) = true 
|  Action_equal(na_Action(nc1,nsnd_NA(m1,l1)),na_Action(nc2,nsnd_NA(m2,l2))) =
     nc_equal(nc1,nc2) andalso Msg_equal(m1,m2) andalso Loc_equal(l1,l2)
| Action_equal(na_Action(nc1,nrcv_NA(m1)),na_Action(nc2,nrcv_NA(m2))) =
     nc_equal(nc1,nc2) andalso Msg_equal(m1,m2)
| Action_equal(na_Action(nc1,tau_NA),na_Action(nc2,tau_NA)) = nc_equal(nc1,nc2)
| Action_equal (x,y) = false 
;

(* nc_filter func *) 
fun nc_filter(empNC,l) = empNC |
    nc_filter(inn(conn(x1,x2),c),x) =
        if Loc_equal(x1,x) then inn(conn(unknown_Loc,x2),nc_filter(c,x))
        else if Loc_equal(x2,x) then nc_filter(c,x)
            else inn(conn(x1,x2),nc_filter(c,x)) |
    nc_filter(inn(disconn(x1,x2),c),x) =
        if Loc_equal(x1,x) then inn(disconn(unknown_Loc,x2),nc_filter(c,x))
        else if Loc_equal(x2,x) then nc_filter(c,x)
            else inn(disconn(x1,x2),nc_filter(c,x));

(* m_filter func *)
fun m_filter(nrcv_NA(m),l) = nrcv_NA(m) |
    m_filter(nsnd_NA(m,x1),x2) =
        if Loc_equal(x1,x2) then nsnd_NA(m,unknown_Loc)
        else nsnd_NA(m,x1) |
    m_filter(tau_NA,l) = tau_NA;
    
fun
    rew(p_choice(x,p_nil)) = rew(x) |
    rew(p_choice(p_nil,x)) = rew(x) |
    rew(p_sum(d,p_nil)) = p_nil |
    rew(p_sum(d,p_choice(x,y))) = rew(p_choice(rew(p_sum(d,x)),rew(p_sum(d,y)))) |
    rew(p_cond(b,p_nil,p_nil)) = p_nil |
    rew(p_cond(b,p_choice(x,y),p_nil)) = rew(p_choice(rew(p_cond(b,x,p_nil)),rew(p_cond(b,y,p_nil)))) |
    rew(p_cond(b,p_sum(x,y),p_nil)) = rew(p_sum(x,rew(p_cond(b,y,p_nil)))) |
    rew(p_cond(b1,p_cond(b2,x,p_nil),p_nil)) = rew(p_cond(and_map(b1 , b2), x,p_nil)) |
    rew(p_cond(b,x,p_nil)) = p_cond(b,x,p_nil) |
    rew(p_cond(b,x,y)) =
        rew(p_choice(rew(p_cond(b,x,p_nil)),rew(p_cond(not_map(b),y,p_nil)))) |
    rew(n_deploy(l,p_nil)) = p_nil |
    rew(n_deploy(l,p_choice(x,y))) = rew(p_choice(rew(n_deploy(l,x)),rew(n_deploy(l,y)))) |
    rew(n_deploy(l,p_prefix(snd_Action(m),x))) = p_prefix(na_Action(empNC,nsnd_NA(m,l)),rew(n_deploy(l,x))) |
    rew(n_deploy(l,p_prefix(rcv_Action(m),x))) =
        p_prefix(na_Action(inn(conn(unknown_Loc,l),empNC),nrcv_NA(m)),rew(n_deploy(l,x))) |
    rew(n_deploy(l,p_prefix(x,y))) = p_prefix(na_Action(empNC,tau_NA),rew(n_deploy(l,y))) |
    rew(n_deploy(l,p_cond(b,x,y))) = rew(p_cond(b,rew(n_deploy(l,x)),rew(n_deploy(l,y)))) |
    rew(n_deploy(l,p_sum(x,y))) = rew(p_sum(x,rew(n_deploy(l,y)))) |
    rew(n_deploy(l,p_recvar(x))) = n_deploy(l,p_recvar(x)) |
    rew(n_encap(m,p_nil)) = p_nil |
    rew(n_encap(mt, p_prefix(na_Action(n,nrcv_NA(m)),x))) =
        if isType(mt,m) then p_nil else p_prefix(na_Action(n,nrcv_NA(m)),rew(n_encap(mt,x))) |
    rew(n_encap(m,p_choice(x,y))) = rew(p_choice(rew(n_encap(m,x)),rew(n_encap(m,y)))) |
    rew(n_encap(m,p_sum(x,y))) = rew(p_sum(x,rew(n_encap(m,y)))) |
    rew(n_encap(m,p_cond(b,x,p_nil))) = rew(p_cond(b,rew(n_encap(m,x)),p_nil)) |
    rew(n_encap(m,n_deploy(l,p_recvar(x)))) = n_encap(m,n_deploy(l,p_recvar(x))) |
    rew(n_encap(m,n_deploy(l,x))) = rew(n_encap(m,rew(n_deploy(l,x)))) |
    rew(n_encap(m,n_abs(n,x))) = if Msg_Type_equal(m,n) then rew(n_abs(n,x))
                             else rew(n_abs(n,rew(n_encap(m,x)))) |
    rew(n_encap(m,n_hide(l,x))) = rew(n_hide(l,rew(n_encap(m,x)))) |
    rew(n_encap(m,n_encap(n,x))) = rew(n_encap(m,rew(n_encap(n,x)))) |
    rew(n_encap(m,n_parallel(x,y))) = n_encap(m,rew(n_parallel(rew(x),rew(y)))) |
    rew(n_abs(m,p_nil)) = p_nil |
    rew(n_abs(mt,p_prefix(na_Action(c,nsnd_NA(m,l)),x))) =
        if isType(mt,m) then p_prefix(na_Action(c,tau_NA),rew(n_abs(ack_Msg_t,x)))
            else p_prefix(na_Action(c,nsnd_NA(m,l)),rew(n_abs(mt,x))) |
    rew(n_abs(mt,p_prefix(na_Action(c,nrcv_NA(m)),x))) =
        if isType(mt,m) then p_prefix(na_Action(c,tau_NA),rew(n_abs(ack_Msg_t,x)))
            else p_prefix(na_Action(c,nrcv_NA(m)),rew(n_abs(mt,x)))|
    rew(n_abs(m,p_choice(x,y))) = rew(p_choice(rew(n_abs(m,x)),rew(n_abs(m,y)))) |
    rew(n_abs(m,p_sum(x,y))) = rew(p_sum(x,rew(n_abs(m,y)))) |
    rew(n_abs(m,p_cond(b,x,p_nil))) = rew(p_cond(b,rew(n_abs(m,x)),p_nil)) |
    rew(n_abs(m,n_deploy(l,p_recvar(x)))) = n_abs(m,n_deploy(l,p_recvar(x))) |
    rew(n_abs(m,n_deploy(l,x))) = rew(n_abs(m,rew(n_deploy(l,x)))) |
    rew(n_abs(m,n_hide(l,x))) = rew(n_hide(l,rew(n_abs(m,x)))) |
    rew(n_abs(m,n_abs(n,x))) = rew(n_abs(m,rew(n_abs(n,x)))) |
    rew(n_abs(m,n_encap(n,x))) = rew(n_abs(m,rew(n_encap(n,x)))) |
    rew(n_abs(m,n_parallel(x,y))) = n_abs(m,rew(n_parallel(rew(x),rew(y)))) |
    rew(n_abs(m,x)) = n_abs(m,x) |
    rew(n_hide(l,p_nil)) = p_nil |
    rew(n_hide(l,p_prefix(na_Action(c,m),x))) = p_prefix(na_Action(nc_filter(c,l),m_filter(m,l)),rew(x)) |
    rew(n_hide(m,p_choice(x,y))) = rew(p_choice(rew(n_hide(m,x)),rew(n_hide(m,y)))) |
    rew(n_hide(m,p_sum(x,y))) = rew(p_sum(x,rew(n_hide(m,y)))) |
    rew(n_hide(m,p_cond(b,x,p_nil))) = rew(p_cond(b,rew(n_hide(m,x)),p_nil)) |
    rew(n_hide(m,n_deploy(l,p_recvar(x)))) = n_hide(m,n_deploy(l,p_recvar(x))) |
    rew(n_hide(m,n_deploy(l,x))) = rew(n_hide(m,rew(n_deploy(l,x)))) |
    rew(n_hide(m,n_hide(l,x))) = rew(n_hide(l,rew(n_hide(m,x)))) |
    rew(n_hide(m,n_abs(n,x))) = rew(n_hide(m,rew(n_abs(n,x)))) |
    rew(n_hide(m,n_encap(n,x))) = rew(n_hide(m,rew(n_encap(n,x)))) |
    rew(n_hide(m,n_parallel(x,y))) = n_hide(m,rew(n_parallel(rew(x),rew(y)))) |
    rew(n_hide(m,x)) = n_hide(m,x) |
    rew(n_parallel(x,p_nil)) = rew(x) |
    rew(n_parallel(p_nil,x)) = rew(x) |
    rew(n_parallel(x,y)) =
        if x=rew(x) andalso y=rew(y) then n_parallel(x,y)
        else rew(n_parallel(rew(x),rew(y))) |
    rew(x) = x ;

(* reduce function *) 
fun reduce([]) = [] |
    reduce(def(x,y)::g) = def(x,rew(y))::g ;

(*rn_cnt + fresh_var + get_fvar*) 
val rn_cnt = ref 0;
fun fresh_var() = (rn_cnt := !rn_cnt+1; rn_RecName(!rn_cnt));
fun get_fvar() = rn_RecName(!rn_cnt);

(* exception *)
exception WrongFormat of Process;

(* val_new_add.sml *)
val new_add = ref []: RSE list ref;

(* s1.sml *)
fun S1(S,p_nil,k) = p_nil |
    S1(S,p_recvar(x),k) = p_recvar(x) |
    S1(S,p_prefix(x,y),k) =
        if k then p_prefix(x,S1(S,y,false)) else p_prefix(x,S1(S,y,k))|
    S1(S,p_choice(x,y),k) =
        if k then p_choice(S1(S,x,k),S1(S,y,k))
            else (new_add := def(rv(fresh_var(),S),S1(S,p_choice(x,y),true)):: !new_add; p_recvar(rv(get_fvar(),S))) |
    S1(S,p_cond(b,x,p_nil),k) =
        if k then p_cond(b,S1(S,x,false),p_nil)
            else (new_add := def(rv(fresh_var(),S),S1(S,p_cond(b,x,p_nil),true)):: !new_add; p_recvar(rv(get_fvar(),S))) |
    S1(S,p_sum(x,y),k) =
        if k then p_sum(x,S1(x::S,y,k))
            else (new_add := def(rv(fresh_var(),S),S1(S,p_sum(x,y),true)):: !new_add;p_recvar(rv(get_fvar(),S))) |
    S1(S,n_deploy(l,p_recvar(x)),k) =
        (new_add := def(rv(fresh_var(),S),n_deploy(l,p_recvar(x))):: !new_add; p_recvar(rv(get_fvar(),S)))  |
    S1(S,n_parallel(x,y),k) = if k then S1(S,n_parallel(x,y),false) else n_parallel(S1(S,x,k),S1(S,y,k)) |
    S1(S,n_encap(m,x),k) = if k then S1(S,n_encap(m,x),false) else n_encap(m,S1(S,x,k)) |
    S1(S,n_abs(m,x),k) = if k then S1(S,n_abs(m,x),false) else n_abs(m,S1(S,x,k)) |
    S1(S,n_hide(l,x),k) = if k then S1(S,n_hide(l,x),false) else n_hide(l,S1(S,x,k)) |
    S1(S,x,k) = raise WrongFormat(x);

(* hadNewPE.sml *)
fun haddNewPE([]) = [] |
    haddNewPE(def(rv(n,l),y)::g) = def(rv(n,l),S1(l,y,true))::haddNewPE(g);

(* mergeRSE.sml *) 
fun mergeRSE([],g) = g |
    mergeRSE(x::g1,g2) = x::mergeRSE(g1,g2)
;

(*addNewPE.sml*)
fun addNewPE(g) = mergeRSE(haddNewPE(g),!new_add);

(* linearize.sml *) 
fun linearize(g) = addNewPE(reduce(g));

(*call Linearize*)

linearize ( [ def ( rv ( X_RecName ,    [ Loc_Var (adr_1 )  ] ) , p_prefix(snd_Action ( req_Msg ( adr_1 ) ),p_recvar ( rv ( X_RecName ,  [ Loc_Var (adr_1) ] )  )) ) 
 , def ( rv ( Y_RecName ,    [ Loc_Var (adr_2 )  ] ) , p_sum ( Loc_Var(lx_3) , p_prefix(rcv_Action ( req_Msg ( lx_3 ) ),p_prefix(snd_Action ( rep_Msg ( adr_2 , lx_3 ) ),p_prefix(succ_Action,p_recvar ( rv ( Y_RecName ,  [ Loc_Var (adr_2) ] )  ))))) ) 
 , def ( rv ( Init_RecName ,    [  ] ) , n_abs (req_Msg_t,n_encap(rep_Msg_t,n_hide( l1_Loc , n_parallel( n_deploy(l1_Loc,p_recvar ( rv ( X_RecName ,  [ Loc_Var (l1_Loc) ] )  )) , n_deploy(l2_Loc,p_recvar ( rv ( Y_RecName ,  [ Loc_Var (l2_Loc) ] )  )) )  ) )) ) 
 ] ) 
 ; 
