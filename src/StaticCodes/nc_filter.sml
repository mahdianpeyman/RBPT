
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