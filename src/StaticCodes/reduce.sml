
(* reduce function *) 
fun reduce([]) = [] |
    reduce(def(x,y)::g) = def(x,rew(y))::g ;
