package org.example.bigdataproject1;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface DataRepository extends MongoRepository<DataPoint,String> {
}
