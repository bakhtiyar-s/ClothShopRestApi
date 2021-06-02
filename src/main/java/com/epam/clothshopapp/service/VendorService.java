package com.epam.clothshopapp.service;

import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.model.Vendor;
import com.epam.clothshopapp.repository.ProductRepository;
import com.epam.clothshopapp.repository.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VendorService extends GenericService<Vendor, Integer, VendorRepository> {

    ProductRepository productRepository;

    public void updateVendorById(int id, Vendor vendor) {
        Vendor oldVendor = super.findById(id);
        oldVendor.setName(vendor.getName());
        oldVendor.setProducts(vendor.getProducts());
        super.save(oldVendor);
    }


    public List<Product> findProductsByVendorId(int id) {
        Vendor vendor = super.findById(id);
        return vendor.getProducts();
    }

    public void saveProductToVendor(int id, Product product) {
        Vendor vendor = super.findById(id);
        List<Product> products = vendor.getProducts();
        productRepository.save(product);
        products.add(product);
        vendor.setProducts(products);
        super.save(vendor);
    }
}
