package sk.upjs.nosql_mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Indexed;

import sk.upjs.nosql_data_source.entity.StudijnyProgram;

@Indexed
public class MongoStudijnyProgram {
	@Id
	private Long id;
	private String skratka;
	private String popis;
	
	public MongoStudijnyProgram() {}
	
	public MongoStudijnyProgram(StudijnyProgram sp) {
		this.id = sp.getId();
		this.skratka = sp.getSkratka();
		this.popis = sp.getPopis();
	}
	
	public MongoStudijnyProgram(Long id, String skratka, String popis) {
		this.id = id;
		this.skratka = skratka;
		this.popis = popis;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSkratka() {
		return skratka;
	}
	public void setSkratka(String skratka) {
		this.skratka = skratka;
	}
	public String getPopis() {
		return popis;
	}
	public void setPopis(String popis) {
		this.popis = popis;
	}
	@Override
	public String toString() {
		return "MongoStudijnyProgram [id=" + id + ", skratka=" + skratka + ", popis=" + popis + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((popis == null) ? 0 : popis.hashCode());
		result = prime * result + ((skratka == null) ? 0 : skratka.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MongoStudijnyProgram other = (MongoStudijnyProgram) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (popis == null) {
			if (other.popis != null)
				return false;
		} else if (!popis.equals(other.popis))
			return false;
		if (skratka == null) {
			if (other.skratka != null)
				return false;
		} else if (!skratka.equals(other.skratka))
			return false;
		return true;
	}
}
