package sk.upjs.nosql_mongodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort.Direction;

import ch.qos.logback.classic.Level;
import sk.upjs.nosql_mongodb.entity.MongoStudent;
import sk.upjs.nosql_mongodb.entity.MongoStudijnyProgram;

public class App {
	public static void main(String[] args) {
		ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
				.getLogger(Logger.ROOT_LOGGER_NAME);
		logger.setLevel(Level.WARN);

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class);
		StudentRepositoryService repositoryService = context.getBean(StudentRepositoryService.class);
		
		repositoryService.insertAllStudents();
		
		// otestovanie funkcionality
//		repositoryService.findAll().forEach(System.out::println);
//		System.out.println();
//		
//		System.out.println(repositoryService.findById(1006262l) + "\n");
//		repositoryService.deleteById(1006262l);
//		System.out.println(repositoryService.findById(1006262l) + "\n");
		
		uloha1(repositoryService, "RNDr.");
		
		MongoStudijnyProgram studijnyProgram = new MongoStudijnyProgram(1l, "B", "Biológia");
		int year = 2001;
		
		uloha2(repositoryService, studijnyProgram, year);
		
		// bez indexu cca: 	32ms
		// 	s indexom cca: 	10ms
		uloha3(repositoryService, studijnyProgram, year);
		
		uloha4(repositoryService);
		
		repositoryService.deleteAllStudents();
		context.close();
	}
	
	private static void uloha1(StudentRepositoryService repositoryService, String titul) {
		System.out.println("ULOHA 1");
		repositoryService.findByTitul(titul).stream().forEach(System.out::println);
		System.out.println();
	}
	
	private static void uloha2(StudentRepositoryService repositoryService, MongoStudijnyProgram studijnyProgram, int year) {
		System.out.println("ULOHA 2");
		System.out.println("vsetci studenti studijneho programu " + studijnyProgram + "\n");
		repositoryService.findByStudijnyProgram(studijnyProgram).stream()
				.forEach(System.out::println);
		System.out.println();
		
		System.out.println("studenti studijneho programu " + studijnyProgram + " v roku " + year);
		repositoryService.findByStudijnyProgramInYear(studijnyProgram, year).stream().forEach(System.out::println);
		System.out.println();
	}
	
	private static void uloha3(StudentRepositoryService repositoryService, MongoStudijnyProgram studijnyProgram, int year) {
		System.out.println("ULOHA 3");
		System.out.println("bez indexu");
		repositoryService.findByStudijnyProgramInYear(studijnyProgram, year);
		
		System.out.println("\ns indexom");
		repositoryService.createIndex(MongoStudent.class, "studium.studijnyProgram", Direction.ASC);
		repositoryService.findByStudijnyProgramInYear(studijnyProgram, year);
		repositoryService.deleteIndex(MongoStudent.class, "studium.studijnyProgram");
		System.out.println();
	}
	
	private static void uloha4(StudentRepositoryService repositoryService) {
		System.out.println("ULOHA 4");
		repositoryService.printPoctyStudentovPodlaRokuAStudProgramu();
	}
}
