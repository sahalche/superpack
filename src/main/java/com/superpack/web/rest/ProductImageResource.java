package com.superpack.web.rest;

import com.superpack.domain.ProductImage;
import com.superpack.service.ProductImageService;
import com.superpack.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.superpack.domain.ProductImage}.
 */
@RestController
@RequestMapping("/api")
public class ProductImageResource {

    private final Logger log = LoggerFactory.getLogger(ProductImageResource.class);

    private static final String ENTITY_NAME = "productImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductImageService productImageService;

    public ProductImageResource(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    /**
     * {@code POST  /product-images} : Create a new productImage.
     *
     * @param productImage the productImage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productImage, or with status {@code 400 (Bad Request)} if the productImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-images")
    public ResponseEntity<ProductImage> createProductImage(@RequestBody ProductImage productImage) throws URISyntaxException {
        log.debug("REST request to save ProductImage : {}", productImage);
        if (productImage.getId() != null) {
            throw new BadRequestAlertException("A new productImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductImage result = productImageService.save(productImage);
        return ResponseEntity.created(new URI("/api/product-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-images} : Updates an existing productImage.
     *
     * @param productImage the productImage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productImage,
     * or with status {@code 400 (Bad Request)} if the productImage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productImage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-images")
    public ResponseEntity<ProductImage> updateProductImage(@RequestBody ProductImage productImage) throws URISyntaxException {
        log.debug("REST request to update ProductImage : {}", productImage);
        if (productImage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductImage result = productImageService.save(productImage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productImage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-images} : get all the productImages.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productImages in body.
     */
    @GetMapping("/product-images")
    public ResponseEntity<List<ProductImage>> getAllProductImages(Pageable pageable) {
        log.debug("REST request to get a page of ProductImages");
        Page<ProductImage> page = productImageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-images/:id} : get the "id" productImage.
     *
     * @param id the id of the productImage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productImage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-images/{id}")
    public ResponseEntity<ProductImage> getProductImage(@PathVariable Long id) {
        log.debug("REST request to get ProductImage : {}", id);
        Optional<ProductImage> productImage = productImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productImage);
    }

    /**
     * {@code DELETE  /product-images/:id} : delete the "id" productImage.
     *
     * @param id the id of the productImage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-images/{id}")
    public ResponseEntity<Void> deleteProductImage(@PathVariable Long id) {
        log.debug("REST request to delete ProductImage : {}", id);
        productImageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
