package net.fiap.postech.fastburger.application.usecases;

import net.fiap.postech.fastburger.adapters.configuration.exceptionHandler.BusinessException;
import net.fiap.postech.fastburger.application.domain.Product;
import net.fiap.postech.fastburger.application.domain.enums.CategoryEnum;
import net.fiap.postech.fastburger.application.ports.outputports.product.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductUseCaseTest {

    private SaveProductOutPutPort saveProductOutPutPort;
    private UpdateProductOutPutPort updateProductOutPutPort;
    private DeleteProductOutPutPort deleteProductOutPutPort;
    private FindProductByCategoryOutPutPort findProductByCategoryOutPutPort;
    private ProductUseCase productUseCase;

    @BeforeEach
    void setUp() {
        saveProductOutPutPort = mock(SaveProductOutPutPort.class);
        updateProductOutPutPort = mock(UpdateProductOutPutPort.class);
        deleteProductOutPutPort = mock(DeleteProductOutPutPort.class);
        findProductByCategoryOutPutPort = mock(FindProductByCategoryOutPutPort.class);
        productUseCase = new ProductUseCase(saveProductOutPutPort, updateProductOutPutPort, deleteProductOutPutPort, findProductByCategoryOutPutPort);
    }

    @Test
    void delete() {
        String id = "1";
        productUseCase.delete(id);
        verify(deleteProductOutPutPort, times(1)).delete(id);
    }

    @Test
    void save() {
        Product product = new Product();
        product.setPrice(10.0);
        when(saveProductOutPutPort.save(any())).thenReturn(product);
        Product savedProduct = productUseCase.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    void update() {
        String id = "1";
        Product product = new Product();
        product.setPrice(10.0);
        when(updateProductOutPutPort.update(any(), any())).thenReturn(product);
        Product updatedProduct = productUseCase.update(id, product);
        assertEquals(product, updatedProduct);
    }

    @Test
    void find() {
        CategoryEnum categoryEnum = CategoryEnum.BURGERS;
        Product product = new Product();
        when(findProductByCategoryOutPutPort.find(any())).thenReturn(Collections.singletonList(product));
        var products = productUseCase.find(categoryEnum);
        assertFalse(products.isEmpty());
        assertEquals(product, products.get(0));
    }

    @Test
    void saveReturnsNull() {
        Product product = new Product();
        product.setPrice(10.0);
        when(saveProductOutPutPort.save(any())).thenReturn(null);
        Product savedProduct = productUseCase.save(product);
        assertNull(savedProduct);
    }

    @Test
    void saveThrowsException() {
        Product product = new Product();
        product.setPrice(10.0);
        when(saveProductOutPutPort.save(any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> productUseCase.save(product));
    }

    @Test
    void saveWithNullProduct() {
        assertThrows(IllegalArgumentException.class, () -> productUseCase.save(null));
    }

    @Test
    void saveWithInvalidPrice() {
        Product product = new Product();
        product.setPrice(0.0);
        assertThrows(BusinessException.class, () -> productUseCase.save(product));
    }
}