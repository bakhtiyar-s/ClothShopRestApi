package com.epam.clothshopapp.controller;

import com.epam.clothshopapp.config.SwaggerConfig;
import com.epam.clothshopapp.mapper.DtoMapper;
import com.epam.clothshopapp.mapper.dto.ProductDto;
import com.epam.clothshopapp.mapper.dto.VendorDto;
import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.model.Vendor;
import com.epam.clothshopapp.service.VendorService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/vendors")
@Api(tags = {SwaggerConfig.VENDOR})
@PreAuthorize("hasAnyAuthority('READ', 'WRITE')")
@RequiredArgsConstructor
public class VendorController {
    private final VendorService vendorService;
    private final DtoMapper<Vendor, VendorDto> vendorMapper;
    private final DtoMapper<Product, ProductDto> productMapper;

    @GetMapping
    public ResponseEntity findAllVendors() {
        try {
            List<Vendor> vendors = vendorService.findAll();
            List<VendorDto> vendorDtos = vendors.stream().map(vendor -> vendorMapper.toDto(vendor)).collect(Collectors.toList());
            return new ResponseEntity<>(vendorDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE')")
    public ResponseEntity saveVendor(@RequestBody VendorDto vendorDto) {
        try {
            vendorService.save(vendorMapper.fromDto(vendorDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findVendorById(@PathVariable("id") int id) {
        try {
            Vendor vendor = vendorService.findById(id);
            return new ResponseEntity<>(vendorMapper.toDto(vendor), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('WRITE')")
    public ResponseEntity updateVendorById(@PathVariable("id") int id, @RequestBody VendorDto vendorDto) {
        try {
            vendorService.updateVendorById(id, vendorMapper.fromDto(vendorDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('WRITE')")
    public ResponseEntity deleteVendorById(@PathVariable("id") int id) {
        try {
            vendorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}/products")
    public ResponseEntity findProductsByVendorId(@PathVariable("id") int id) {
        try {
            List<Product> products = vendorService.findProductsByVendorId(id);
            List<ProductDto> productDtos = products.stream().map(product -> productMapper.toDto(product)).collect(Collectors.toList());
            return new ResponseEntity<>(productDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{id}/products")
    public ResponseEntity saveProductToVendor(@PathVariable("id") int id, @RequestBody ProductDto productDto) {
        try {
            vendorService.saveProductToVendor(id, productMapper.fromDto(productDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
