package sk.upjs.nosql_mongodb.entity;

public class MongoRokStudijnyProgramPocet {
	
	private RokStudijnyProgramKey id;
	private float value;
	
	public RokStudijnyProgramKey getId() {
		return id;
	}

	public void setId(RokStudijnyProgramKey id) {
		this.id = id;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "MongoRokStudijnyProgramPocet [id=" + id + ", value=" + value + "]";
	}

	public static class RokStudijnyProgramKey {
		private int rok;
		private MongoStudijnyProgram studijnyProgram;
		public int getRok() {
			return rok;
		}
		public void setRok(int rok) {
			this.rok = rok;
		}
		public MongoStudijnyProgram getStudijnyProgram() {
			return studijnyProgram;
		}
		public void setStudijnyProgram(MongoStudijnyProgram studijnyProgram) {
			this.studijnyProgram = studijnyProgram;
		}
		@Override
		public String toString() {
			return "RokStudijnyProgramKey [rok=" + rok + ", studijnyProgram=" + studijnyProgram + "]";
		}
	}
}
