package com.epam.clothshopapp.controller;

import com.epam.clothshopapp.config.SwaggerConfig;
import com.epam.clothshopapp.mapper.DtoMapper;
import com.epam.clothshopapp.mapper.dto.ProductDto;
import com.epam.clothshopapp.mapper.dto.VendorDto;
import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.model.Vendor;
import com.epam.clothshopapp.service.VendorService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/vendors")
@Api(tags = {SwaggerConfig.VENDOR})
public class VendorController {
    private VendorService vendorService;
    private DtoMapper dtoMapper;

    @Autowired
    public VendorController(VendorService vendorService, DtoMapper dtoMapper) {
        this.vendorService = vendorService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public ResponseEntity findAllVendors() {
        try {
            List<Vendor> vendors = vendorService.findAllVendors();
            List<VendorDto> vendorDtos = vendors.stream().map(vendor -> dtoMapper.vendorToVendorDto(vendor)).collect(Collectors.toList());
            return new ResponseEntity<>(vendorDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity saveVendor(@RequestBody VendorDto vendorDto) {
        try {
            vendorService.saveVendor(dtoMapper.vendorDtoToVendor(vendorDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findVendorById(@PathVariable("id") int id) {
        try {
            Optional<Vendor> vendor = vendorService.findVendorById(id);
            if (vendor.isPresent()) {
                return new ResponseEntity<>(dtoMapper.vendorToVendorDto(vendor.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateVendorById(@PathVariable("id") int id, @RequestBody VendorDto vendorDto) {
        try {
            vendorService.updateVendorById(id, dtoMapper.vendorDtoToVendor(vendorDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteVendorById(@PathVariable("id") int id) {
        try {
            vendorService.deleteVendorById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}/products")
    public ResponseEntity findProductsByVendorId(@PathVariable("id") int id) {
        try {
            List<Product> products = vendorService.findProductsByVendorId(id);
            List<ProductDto> productDtos = products.stream().map(product -> dtoMapper.productToProductDto(product)).collect(Collectors.toList());
            return new ResponseEntity<>(productDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{id}/products")
    public ResponseEntity saveProductToVendor(@PathVariable("id") int id, @RequestBody ProductDto productDto) {
        try {
            vendorService.saveProductToVendor(id, dtoMapper.productDtoToProduct(productDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
