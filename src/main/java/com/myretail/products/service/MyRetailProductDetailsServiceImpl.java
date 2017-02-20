package com.myretail.products.service;


import com.myretail.products.dao.MyRetailProductPrice;
import com.myretail.products.dao.MyRetailProductPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Shreyas.
 */

@Component
public class MyRetailProductDetailsServiceImpl implements MyRetailProductDetailsService {

    @Autowired
    MyRetailProductPriceRepository myRetailProductPriceRepository;

    Logger log = LoggerFactory.getLogger(MyRetailProductDetailsServiceImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public MyRetailProductPrice getProductPrice(int id) throws Exception {
        MyRetailProductPrice productPrice = null;
        try {
            productPrice = myRetailProductPriceRepository.findByProductId(id);
            if (productPrice != null) {
                return productPrice;
            }
        }catch(Exception e) {
            throw new Exception("Price Details Not Found in mongodb : " +id, e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public MyRetailProductPrice updateProductPrice(MyRetailProductPrice price) throws  Exception {
        MyRetailProductPrice productPrice = null;
        try {
                productPrice = myRetailProductPriceRepository.save(price);

            if (productPrice != null) {
                return productPrice;
            }
        }catch(Exception e) {
            throw new Exception("Price Details Not updated to mongodb : ", e);
        }
        return null;
    }

    @Override
    public String getProductDetails(int id) throws Exception {

       String url = "http://redsky.target.com/v1/pdp/tcin/" +id + "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";
        log.info("Redsky API Request: " + url);
        RestTemplate restTemplate = new RestTemplate();

        String apiResponse = restTemplate.getForObject(url, String.class);

        return apiResponse;
    }
}
