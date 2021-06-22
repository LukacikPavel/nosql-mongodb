package sk.upjs.nosql_mongodb.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.TimeZone;

import org.springframework.data.annotation.Id;

import sk.upjs.nosql_data_source.entity.Studium;

public class MongoStudium {
	@Id
	private Long id;
	private Instant zaciatokStudia;
	private Instant koniecStudia;
	private MongoStudijnyProgram studijnyProgram;
	
	public MongoStudium() {}
	
	public MongoStudium(Studium studium) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		this.id = studium.getId();
		try {
			this.zaciatokStudia = formatter.parse(studium.getZaciatokStudia()).toInstant();
		} catch (ParseException e) {
			this.zaciatokStudia = null;
//			e.printStackTrace();
		}
		try {
			this.koniecStudia = formatter.parse(studium.getKoniecStudia()).toInstant();
		} catch (ParseException e) {
			this.koniecStudia = null;
//			e.printStackTrace();
		}
		this.studijnyProgram = new MongoStudijnyProgram(studium.getStudijnyProgram());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Instant getZaciatokStudia() {
		return zaciatokStudia;
	}
	public void setZaciatokStudia(Instant zaciatokStudia) {
		this.zaciatokStudia = zaciatokStudia;
	}
	public Instant getKoniecStudia() {
		return koniecStudia;
	}
	public void setKoniecStudia(Instant koniecStudia) {
		this.koniecStudia = koniecStudia;
	}
	public MongoStudijnyProgram getStudijnyProgram() {
		return studijnyProgram;
	}
	public void setStudijnyProgram(MongoStudijnyProgram studijnyProgram) {
		this.studijnyProgram = studijnyProgram;
	}
	@Override
	public String toString() {
		return "MongoStudium [id=" + id + ", zaciatokStudia=" + zaciatokStudia + ", koniecStudia=" + koniecStudia
				+ ", studijnyProgram=" + studijnyProgram + "]";
	}
}
