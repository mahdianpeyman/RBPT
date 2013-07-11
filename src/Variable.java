
public class Variable {
	private String name ;
	private Sort sort ;
	
	
	public Variable (String str, Sort s){
		name = str ; 
		setSort(s) ;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Sort getSort() {
		return sort;
	}
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	
	
}
