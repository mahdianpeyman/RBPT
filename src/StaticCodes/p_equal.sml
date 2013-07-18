
(* p_equal fun *) 
fun p_equal(conn(x1,x2),conn(x3,x4)) = Loc_equal(x1,x3) andalso Loc_equal(x2,x4) 
 | p_equal(disconn(x1,x2),disconn(x3,x4)) = Loc_equal(x1,x3) andalso Loc_equal(x2,x4)
 | p_equal(x,y) = false
 ;