package br.com.carlos.enumerate;

public enum BlockUnblockEnum {
	BLOCK_USER(1, "Bloquear usuario"),
	UNBLOCK_USER(2, "Desbloquear usuario"),
	BLOCK_USER_MAX_ATTEMPTS(3, "Bloquear usuario por excesso de tentativas falhas no login"),
	UNAUTHORIZED_ACCESS(4, "Tentativa de acesso não autorizada"),
	BLOCK_USER_SCREEN_USERS(5, "Usuario bloqueado via tela de usuarios");
	private final Integer key;
	private final String description;
	
	BlockUnblockEnum(Integer key, String description) {
		this.key = key;
		this.description = description;
	}

	public Integer getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}
	
	public static BlockUnblockEnum fromValue(Integer key) {
		for(BlockUnblockEnum option: BlockUnblockEnum.values()) {
			if(option.key.equals(key))
				return option;
		}
		return null;
	}
}
