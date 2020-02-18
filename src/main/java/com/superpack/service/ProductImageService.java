package com.superpack.service;

import com.superpack.domain.ProductImage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ProductImage}.
 */
public interface ProductImageService {

    /**
     * Save a productImage.
     *
     * @param productImage the entity to save.
     * @return the persisted entity.
     */
    ProductImage save(ProductImage productImage);

    /**
     * Get all the productImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductImage> findAll(Pageable pageable);


    /**
     * Get the "id" productImage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductImage> findOne(Long id);

    /**
     * Delete the "id" productImage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
