
(* m_filter func *)
fun m_filter(nrcv_NA(m),l) = nrcv_NA(m) |
    m_filter(nsnd_NA(m,x1),x2) =
        if Loc_equal(x1,x2) then nsnd_NA(m,unknown_Loc)
        else nsnd_NA(m,x1) |
    m_filter(tau_NA,l) = tau_NA;
    