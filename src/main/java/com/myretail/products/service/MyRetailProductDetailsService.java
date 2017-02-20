package com.myretail.products.service;

import com.myretail.products.dao.MyRetailProductPrice;

/**
 * Created by Shreyas.
 */
public interface MyRetailProductDetailsService {

    MyRetailProductPrice getProductPrice(int id) throws  Exception;
    MyRetailProductPrice updateProductPrice(MyRetailProductPrice productPrice) throws  Exception;
    String  getProductDetails(int id) throws Exception;
}
