package com.myretail.products.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.products.dao.MyRetailProductPrice;
import com.myretail.products.dto.RequestTransferObject;
import com.myretail.products.dto.ResponseValueObject;
import com.myretail.products.model.ProductCurrentPrice;
import com.myretail.products.model.RedskyModel;
import com.myretail.products.service.MyRetailProductDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by Shreyas.
 */

@RestController
@RequestMapping("myretail/v1/")
@ComponentScan(basePackages = "com.myretail.products")
public class MyRetailProductController {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MyRetailProductDetailsService myRetailProductDetailsService;
    Logger log = LoggerFactory.getLogger(MyRetailProductController.class);
    String PRODUCT_NOT_FOUND = "Product ID is not a valid product in Target";


    String USER_EXCEPTION = "Please verify your request.";
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> getProductDetails(@PathVariable int id) throws Exception {

        ResponseValueObject responseValueObject = new ResponseValueObject();
        RedskyModel redskyModel = new RedskyModel();

        String apiResponse=myRetailProductDetailsService.getProductDetails(id);
        log.info("Redsky API Response : " + apiResponse);

            if (!apiResponse.isEmpty()) {
                redskyModel = objectMapper.readValue(apiResponse, RedskyModel.class);
                responseValueObject.setName(redskyModel.getProduct().getItem().getProductDescription().getTitle());
                responseValueObject.setId(id);
                log.info("Product Name : " + redskyModel.getProduct().getItem().getProductDescription().getTitle());

                try {
                    MyRetailProductPrice myRetailProductPrice = myRetailProductDetailsService.getProductPrice(id);
                    if (myRetailProductPrice != null) {
                        log.info("Product Price : " + myRetailProductPrice.getCurrentPrice());
                        ProductCurrentPrice currentPrice = new ProductCurrentPrice();
                        currentPrice.setCurrencyCode(myRetailProductPrice.getCurrencyCode());
                        currentPrice.setValue(myRetailProductPrice.getCurrentPrice());
                        responseValueObject.setCurrentPrice(currentPrice);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("Product price not found in database: " + id);
                }
            }else {
                log.info(PRODUCT_NOT_FOUND +" : " + id);
                throw new Exception("Product Not Found : "  + id);
            }
        return new ResponseEntity<>(responseValueObject, HttpStatus.OK);
    }

    @RequestMapping(value = "products/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> putProductDetails(@PathVariable final int id, @RequestBody RequestTransferObject requestTransferObject) throws Exception {
        MyRetailProductPrice myRetailProductPrice = new MyRetailProductPrice();
        if (requestTransferObject.getCurrent_price()==null) {
            log.error("current_price is null or empty.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (requestTransferObject.getCurrency_code()==null) {
            log.error("currency_code is null or empty.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
            try {
                 myRetailProductPrice = myRetailProductDetailsService.getProductPrice(id);
                if (myRetailProductPrice != null) {
                    log.info("Product Price before update : " + myRetailProductPrice.getCurrentPrice());
                    myRetailProductPrice.setCurrentPrice(requestTransferObject.getCurrent_price());
                    myRetailProductPrice.setCurrencyCode(requestTransferObject.getCurrency_code());
                    Date now = new Date();
                    myRetailProductPrice.setModifyTimestamp(now);
                    myRetailProductPrice = myRetailProductDetailsService.updateProductPrice(myRetailProductPrice);
                    log.info(" _id : " + myRetailProductPrice.getId());
                    log.info("Product Price after update : " + myRetailProductPrice.getCurrentPrice());
                }else{
                    throw new Exception("Price Details Not Found in database for : " + id);
                }
                return new ResponseEntity<>(myRetailProductPrice, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Error while connecting to Database");
            }
    }

      @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> errorResponse(Throwable throwable) {
        Map<String,Object> errorMap= new HashMap<>();
        errorMap.put("message", USER_EXCEPTION);
        errorMap.put("errorCode", UUID.randomUUID().toString());
        return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}












