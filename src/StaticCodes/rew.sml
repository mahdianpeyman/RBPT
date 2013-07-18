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
