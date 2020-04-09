package org.wcci.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.wcci.demo.model.Campus;


public interface CampusRepository extends CrudRepository<Campus, Long> {

}
