package com.gabriel.stock.port;

import com.gabriel.stock.entity.Product;

import java.util.List;

public interface ProductFinder {
    List<Product> findAllByOrderByPriceDesc();
}
