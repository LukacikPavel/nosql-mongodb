package sk.upjs.nosql_mongodb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.stereotype.Service;

import sk.upjs.nosql_data_source.entity.Student;
import sk.upjs.nosql_data_source.persist.DaoFactory;
import sk.upjs.nosql_data_source.persist.StudentDao;
import sk.upjs.nosql_mongodb.entity.MongoRokStudijnyProgramPocet;
import sk.upjs.nosql_mongodb.entity.MongoStudent;
import sk.upjs.nosql_mongodb.entity.MongoStudijnyProgram;

@Service
public class StudentRepositoryService {

	@Autowired
	private StudentRepository repository;
	@Autowired
	private MongoOperations mongoOps;
	@Autowired
	private MongoTemplate mongoTemplate;

	private StudentDao studentDao = DaoFactory.INSTANCE.getStudentDao();

	public void insertAllStudents() {
		List<Student> students = studentDao.getAll();
//		students.stream().forEach(System.out::println);
//		System.out.println();
		List<MongoStudent> mStudents = students.stream().map(s -> new MongoStudent(s)).collect(Collectors.toList());
		repository.saveAll(mStudents);
	}

	public MongoStudent insert(MongoStudent student) {
		return repository.save(student);
	}

	public Optional<MongoStudent> findById(long id) {
		return repository.findById(id);
	}

	public Collection<MongoStudent> findAll() {
		return (Collection<MongoStudent>) repository.findAll();
	}

	public void deleteById(long id) {
		repository.deleteById(id);
	}

	public void deleteAllStudents() {
		repository.deleteAll();
	}

	public Collection<NamesOnly> findByTitul(String titul) {
		return repository.findBySkratkaakadtitul(titul);
	}

	public Collection<MongoStudent> findByStudijnyProgram(MongoStudijnyProgram studijnyProgram) {
		return repository.findByStudium_StudijnyProgram(studijnyProgram);
	}

	public Collection<MongoStudent> findByStudijnyProgramInYear(MongoStudijnyProgram studijnyProgram, int year) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		Instant firstDayOfYear = null;
		try {
			firstDayOfYear = formatter.parse("1.1." + year).toInstant();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(firstDayOfYear);
		long start = System.currentTimeMillis();
		Collection<MongoStudent> result = repository.findByStudiumInYear(firstDayOfYear, studijnyProgram);
		System.out.println(System.currentTimeMillis() - start + " ms");
		return result;
	}

	public void createIndex(Class<?> entityClass, String name, Direction direction) {
		mongoOps.indexOps(entityClass).ensureIndex(new Index(name, direction));
	}

	public void deleteIndex(Class<?> entityClass, String name) {
		mongoOps.indexOps(entityClass).dropIndex(name + "_1");
	}

	public void printPoctyStudentovPodlaRokuAStudProgramu() {
		String mapFunction = 
				"function() {" + 
				"	this.studium.forEach(function(studium) {" + 
				"		var startYear = studium.zaciatokStudia.getFullYear();" + 
				"		var endYear = startYear;" + // alebo aktualny?
				"		if (studium.koniecStudia) {" + 
				"			endYear = studium.koniecStudia.getFullYear();" + 
				"		}" + 
				"		for (var year = startYear; year < endYear + 1; year++) {" + 
				"			emit({rok: year, studijnyProgram: studium.studijnyProgram}, 1);" + 
				"		}" + 
				"	});" + 
				"}";
		
		String reduceFunction = 
				"function(key, values) {"
				+ "	var sum = 0;"
				+ "	 for (var i = 0; i < values.length; i++)"
				+ "		sum += values[i];" + 
				"	return sum;" + 
				"}";

		MapReduceResults<MongoRokStudijnyProgramPocet> mapReduce = mongoTemplate.mapReduce("mongoStudent", mapFunction,
				reduceFunction, MongoRokStudijnyProgramPocet.class);
		
		for (MongoRokStudijnyProgramPocet elem : mapReduce) {
			System.out.println("ROK: " + elem.getId().getRok() + " | studijny program " + elem.getId().getStudijnyProgram().getPopis() + "\t| pocet studentov: " + (int)elem.getValue());			
		}
	}

}
