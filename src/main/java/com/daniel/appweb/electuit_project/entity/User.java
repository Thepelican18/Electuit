package com.daniel.appweb.electuit_project.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name="autoincrement" , strategy="increment")
	@GeneratedValue(generator="autoincrement")
	@Column(name = "user_id")
	private Integer ID;
	@Column(name = "username")
	private String username;
	@Column(name = "user_email")
	private String email;
	@Column(name = "user_password")
	private String password;
	
	 @OneToMany(cascade=CascadeType.ALL,mappedBy = "user")
     private Set<Circuit> circuitsSet;
	
	public User() {
		
	}

	public User(Integer iD, String username, String email, String password, Set<Circuit> circuitsSet) {
		super();
		ID = iD;
		this.username = username;
		this.email = email;
		this.password = password;
		this.circuitsSet = circuitsSet;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Circuit> getCircuitsSet() {
		return circuitsSet;
	}

	public void setCircuitsSet(Set<Circuit> circuitsSet) {
		this.circuitsSet = circuitsSet;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
