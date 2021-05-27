package com.epam.clothshopapp.service;

import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.model.Vendor;
import com.epam.clothshopapp.repository.ProductRepository;
import com.epam.clothshopapp.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService{
    private final VendorRepository vendorRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Vendor> findAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public void saveVendor(Vendor vendor) {
        vendorRepository.save(vendor);
    }

    @Override
    public Optional<Vendor> findVendorById(int id) {
        return vendorRepository.findById(id);
    }

    @Override
    public void updateVendorById(int id, Vendor vendor) {
        Optional<Vendor> oldVendor = vendorRepository.findById(id);
        if (oldVendor.isPresent()) {
            oldVendor.get().setName(vendor.getName());
            oldVendor.get().setProducts(vendor.getProducts());
            vendorRepository.save(oldVendor.get());
        }
    }

    @Override
    public void deleteVendorById(int id) {
        vendorRepository.deleteById(id);
    }

    @Override
    public List<Product> findProductsByVendorId(int id) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        return vendor.map(Vendor::getProducts).orElse(null);
    }

    @Override
    public void saveProductToVendor(int id, Product product) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        if (vendor.isPresent()) {
            List<Product> products = vendor.get().getProducts();
            productRepository.save(product);
            products.add(product);
            vendor.get().setProducts(products);
            vendorRepository.save(vendor.get());
        }
    }


}
