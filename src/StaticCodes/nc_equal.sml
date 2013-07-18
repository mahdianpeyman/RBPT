
(*nc_equal function*)
fun nc_equal(empNC,empNC) = true 
 | nc_equal(empNC,inn(p,nc)) = false
 | nc_equal(inn(p,nc),empNC) = false
 | nc_equal(inn(p,nc1),nc2) = nc_include(p,nc2) andalso nc_equal(nc1,nc2)
 ;