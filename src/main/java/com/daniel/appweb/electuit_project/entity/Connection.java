package com.daniel.appweb.electuit_project.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_connections")
public class Connection implements Serializable{


	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="autoincrement" , strategy="increment")
	@GeneratedValue(generator="autoincrement")
	@Column(name = "connection_id")
	private Integer Id;
	@Column(name = "id_in_circuit")
	private Integer IdInCircuit;
	@Column(name = "component_connected_id_in_circuit")
	private Integer componentConnectedIdInCircuit;
	@Column(name = "connection_orientation")
	private String connectionOrientation;
	@Column(name = "id_circuit")
	private Integer IdCircuit;
	

	public Connection() {
		
	}


	public Connection(Integer id, Integer idInCircuit, Integer componentConnectedIdInCircuit,
			String connectionOrientation,Integer IdCircuit) {

		Id = id;
		IdInCircuit = idInCircuit;
		this.componentConnectedIdInCircuit = componentConnectedIdInCircuit;
		this.connectionOrientation = connectionOrientation;
		this.IdCircuit = IdCircuit;
	}


	public Integer getId() {
		return Id;
	}


	public void setId(Integer id) {
		Id = id;
	}


	public Integer getIdInCircuit() {
		return IdInCircuit;
	}


	public void setIdInCircuit(Integer idInCircuit) {
		IdInCircuit = idInCircuit;
	}


	public Integer getComponentConnectedIdInCircuit() {
		return componentConnectedIdInCircuit;
	}


	public void setComponentConnectedIdInCircuit(Integer componentConnectedIdInCircuit) {
		this.componentConnectedIdInCircuit = componentConnectedIdInCircuit;
	}


	public String getConnectionOrientation() {
		return connectionOrientation;
	}


	public void setConnectionOrientation(String connectionOrientation) {
		this.connectionOrientation = connectionOrientation;
	}


	public Integer getIdCircuit() {
		return IdCircuit;
	}


	public void setIdCircuit(Integer idCircuit) {
		IdCircuit = idCircuit;
	}
	
	
	
	
}