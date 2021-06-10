package com.daniel.appweb.electuit_project.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "tbl_components")
public class Component implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="autoincrement" , strategy="increment")
	@GeneratedValue(generator="autoincrement")
	@Column(name = "component_id")
	private Integer Id;
	@Column(name = "component_type")
	private String componentType;
	@Column(name = "component_status")
	private String status;
	@Column(name = "id_in_circuit")
	private Integer IdInCircuit;
	@Column(name = "x")
	private Float x;
	@Column(name = "y")
	private Float y;
	@Column(name = "burned")
	private Integer burned;
	@ManyToOne
	@JoinColumn(name = "circuit_id")
	private Circuit circuit;

	
	
	public Component() {
		
	}



	public Component(Integer id, String componentType, String status, Integer idInCircuit, Float x, Float y,
			Integer burned, Circuit circuit) {
		super();
		Id = id;
		this.componentType = componentType;
		this.status = status;
		IdInCircuit = idInCircuit;
		this.x = x;
		this.y = y;
		this.burned = burned;
		this.circuit = circuit;
	}



	public Integer getId() {
		return Id;
	}



	public void setId(Integer id) {
		Id = id;
	}



	public String getComponentType() {
		return componentType;
	}



	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public Integer getIdInCircuit() {
		return IdInCircuit;
	}



	public void setIdInCircuit(Integer idInCircuit) {
		IdInCircuit = idInCircuit;
	}



	public Float getX() {
		return x;
	}



	public void setX(Float x) {
		this.x = x;
	}



	public Float getY() {
		return y;
	}



	public void setY(Float y) {
		this.y = y;
	}



	public Integer getBurned() {
		return burned;
	}



	public void setBurned(Integer burned) {
		this.burned = burned;
	}



	public Circuit getCircuit() {
		return circuit;
	}



	public void setCircuit(Circuit circuit) {
		this.circuit = circuit;
	}




}