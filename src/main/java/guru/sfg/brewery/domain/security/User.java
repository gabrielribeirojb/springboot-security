package guru.sfg.brewery.domain.security;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String username;
	private String password;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "user_authority", 
	joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")}, 
	inverseJoinColumns = {@JoinColumn(name="AUTHORITY_ID", referencedColumnName = "ID")})
	private Set<Authority> autorities;
	private Boolean accoutNonExpired = true;
	private Boolean accountNonLocked = true;
	private Boolean credentialsNonExpired = true;
}
