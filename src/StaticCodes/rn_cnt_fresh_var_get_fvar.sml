
(*rn_cnt + fresh_var + get_fvar*) 
val rn_cnt = ref 0;
fun fresh_var() = (rn_cnt := !rn_cnt+1; rn_RecName(!rn_cnt));
fun get_fvar() = rn_RecName(!rn_cnt);
