package com.haynespro.assessment.mmt.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "MMT_MODEL")
public class Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    // This should be ManyToOne since many models can belong to one make
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MAKE_ID")
	private Make make;
	
	private String category;
	
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Make getMake() {
		return make;
	}

	public void setMake(Make make) {
		this.make = make;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String model) {
		this.name = model;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Model other = (Model) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Model [id=" + id + ", category=" + category + ", name=" + name + "]";
	}

}
