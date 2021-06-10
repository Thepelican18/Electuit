package com.daniel.appweb.electuit_project.entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "tbl_circuits")
public class Circuit implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="autoincrement" , strategy="increment")
	@GeneratedValue(generator="autoincrement")
	@Column(name = "circuit_id")
	private Integer id;
	@Column(name = "title")
	private String title;
	@Lob
	@Type(type="org.hibernate.type.ImageType")
	@Column(name = "circuit_preview")
	private byte[] circuitPreview;
	
	
	@Column(name = "creation_date")
	private LocalDate creationDate;
	@Expose(serialize = false)
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@Expose(serialize = false)
	@OneToMany(cascade=CascadeType.ALL,mappedBy = "circuit")
    private Set<Component> componentsSet;
	
	public Circuit() {
		
	}

	public Circuit(Integer id, String title, byte[] circuitPreview, LocalDate creationDate, User user,
			Set<Component> componentsSet) {
		super();
		this.id = id;
		this.title = title;
		this.circuitPreview = circuitPreview;
		this.creationDate = creationDate;
		this.user = user;
		this.componentsSet = componentsSet;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public byte[] getCircuitPreview() {
		return circuitPreview;
	}

	public void setCircuitPreview(byte[] circuitPreview) {
		this.circuitPreview = circuitPreview;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Component> getComponentsSet() {
		return componentsSet;
	}

	public void setComponentsSet(Set<Component> componentsSet) {
		this.componentsSet = componentsSet;
	}

	@Override
	public String toString() {
		return "Circuit [id=" + id + ", title=" + title +   ", circuitPreview="
				+ Arrays.toString(circuitPreview) + ", creationDate=" + creationDate + ", user=" + user
				+ ", componentsSet=" + componentsSet + "]";
	}

	
}
