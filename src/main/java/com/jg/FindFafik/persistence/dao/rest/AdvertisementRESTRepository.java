package com.jg.FindFafik.persistence.dao.rest;

import com.jg.FindFafik.persistence.model.Advertisement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AdvertisementRESTRepository extends CrudRepository<Advertisement, Long> {

}
