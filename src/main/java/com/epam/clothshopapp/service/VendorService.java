package com.epam.clothshopapp.service;

import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.model.Vendor;

import java.util.List;
import java.util.Optional;

public interface VendorService {
    List<Vendor> findAllVendors();

    void saveVendor(Vendor vendor);

    Optional<Vendor> findVendorById(int id);

    void updateVendorById(int id, Vendor vendor);

    void deleteVendorById(int id);

    List<Product> findProductsByVendorId(int id);

    void saveProductToVendor(int id, Product product);
}
