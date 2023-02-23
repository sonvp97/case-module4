//package com.codegym.formatter;
//
//import com.codegym.model.dto.ProductDto;
//
//import com.codegym.model.service.ProductService;
//import org.springframework.format.Formatter;
//import org.springframework.stereotype.Component;
//
//import java.text.ParseException;
//import java.util.Locale;
//import java.util.Optional;
//@Component
//public class ProductFormatter implements Formatter<ProductDto> {
//    private ProductService iproductService;
//    public ProductFormatter(ProductService iProductService){
//        this.iproductService = iProductService;
//    }
//    @Override
//    public ProductDto parse(String id, Locale locale) throws ParseException {
//        Optional<ProductDto> product = iproductService.findById(Long.parseLong(id));
//        return product.orElse(null);
//    }
//    @Override
//    public String print(ProductDto object, Locale locale) {
//        return null;
//    }
//}
