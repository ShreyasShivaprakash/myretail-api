package com.myretail.products.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Shreyas.
 */
@Repository
public interface MyRetailProductPriceRepository extends MongoRepository<MyRetailProductPrice, String> {

    MyRetailProductPrice findByProductId(int productId);

}
