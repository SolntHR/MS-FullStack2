package cl.sonidovivo.carrito.cliente;

import cl.sonidovivo.carrito.exception.RecursoNoEncontradoException;
import cl.sonidovivo.carrito.exception.ServicioNoDisponibleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class InventarioCliente {

    private final RestClient restClient;

    public InventarioCliente(@LoadBalanced RestClient.Builder builder, @Value("${inventario.url}") String urlInventario) {
        this.restClient = builder.baseUrl(urlInventario).build();
    }

    public ProductoInventario obtenerPorCodigo(String codigo) {
        try {
            ProductoInventario producto = restClient.get()
                    .uri("/productos/codigo/{codigo}", codigo)
                    .retrieve()
                    .body(ProductoInventario.class);
            if (producto == null) {
                throw new RecursoNoEncontradoException("Producto no encontrado con código " + codigo + ".");
            }
            return producto;
        } catch (HttpClientErrorException.NotFound e) {
            throw new RecursoNoEncontradoException("Producto no encontrado con código " + codigo + ".");
        } catch (RestClientException e) {
            throw new ServicioNoDisponibleException("No se pudo consultar el inventario. Intente más tarde.");
        }
    }
}
