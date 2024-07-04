package myy803.SocialBookStore.entity;

public enum Role {
	USER("User"), GUEST("Guest");
	
	private final String value;
	
	private Role(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
