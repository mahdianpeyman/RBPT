
(* mergeRSE.sml *) 
fun mergeRSE([],g) = g |
    mergeRSE(x::g1,g2) = x::mergeRSE(g1,g2)
;
