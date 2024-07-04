package myy803.SocialBookStore.entity;


import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User implements UserDetails{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userid")
	private int userid;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name="role")
	private Role role;
	
	public User() {
		this.role = Role.GUEST;
	};
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;	
		this.role = Role.GUEST;
	}
	
	public int getUserid() {return userid;} // only getter for id 
	
	
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}

	
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}

	
	public Role getRole() {return role;}
	public void setRole(Role role) {this.role = role;}
	
	
	public String toString()
	{
		return "username=" + this.username + ", role=" + this.role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
		return Collections.singletonList(authority);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


}
