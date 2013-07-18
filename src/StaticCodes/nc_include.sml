
(* nc_include *)
fun nc_include(p,empNC) = false 
 | nc_include(p1,inn(p2,nc)) = p_equal(p1,p2) orelse nc_include(p1,nc);
