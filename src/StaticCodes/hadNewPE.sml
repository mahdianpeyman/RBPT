
(* hadNewPE.sml *)
fun haddNewPE([]) = [] |
    haddNewPE(def(rv(n,l),y)::g) = def(rv(n,l),S1(l,y,true))::haddNewPE(g);