package sk.upjs.nosql_mongodb;

import java.time.Instant;
import java.util.Collection;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sk.upjs.nosql_mongodb.entity.MongoStudent;
import sk.upjs.nosql_mongodb.entity.MongoStudijnyProgram;
import sk.upjs.nosql_mongodb.entity.MongoStudium;

@Repository
public interface StudentRepository extends CrudRepository<MongoStudent, Long>{
	Collection<NamesOnly> findBySkratkaakadtitul(String titul);
	Collection<MongoStudent> findByStudium_StudijnyProgram(MongoStudijnyProgram studijnyProgram);
	@Query(value = "{ 'studium': {$elemMatch: {zaciatokStudia: { '$lte': ?0 }, koniecStudia: { $not: { '$lt': ?0 } }, studijnyProgram: ?1} } }")
	Collection<MongoStudent> findByStudiumInYear(Instant date, MongoStudijnyProgram studijnyProgram);
}
