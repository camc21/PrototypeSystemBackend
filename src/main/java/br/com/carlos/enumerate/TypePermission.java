package br.com.carlos.enumerate;

public enum TypePermission {
	
	NULL(0l),
	READ(1l),
	WRITE(2l);
	
	private Long code;

	public Long getCode() {
		return this.code;
	}
  
	private TypePermission(Long code){
		this.code = code;   	
	}


}
