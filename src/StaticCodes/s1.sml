
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