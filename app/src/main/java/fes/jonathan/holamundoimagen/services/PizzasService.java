package fes.jonathan.holamundoimagen.services;

import fes.jonathan.holamundoimagen.models.ClienteModel;
import fes.jonathan.holamundoimagen.models.IdDto;
import fes.jonathan.holamundoimagen.models.MasaDto;
import fes.jonathan.holamundoimagen.models.MenuDto;
import fes.jonathan.holamundoimagen.models.OrdenDto;
import fes.jonathan.holamundoimagen.models.OrdenDtoIn;
import fes.jonathan.holamundoimagen.models.ProductoDto;
import fes.jonathan.holamundoimagen.models.SesionResponse;
import fes.jonathan.holamundoimagen.models.TamanioDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Interfaz Retrofit que define TODOS los endpoints del Swagger de Pizzas.
 *
 * Notas:
 * - Los endpoints autenticados (Ordenes) no necesitan @Header("Authorization"):
 *   el AuthInterceptor lo inyecta automáticamente desde SesionManager.
 * - Los endpoints GET de catálogos son públicos — no requieren token.
 */
public interface PizzasService {

    // ──────────────────────────────────────────
    // Catálogos (públicos — sin token)
    // ──────────────────────────────────────────

    /** GET /api/Pizzas/Menus — categorías del menú con imagen y ruta. */
    @GET("api/Pizzas/Menus")
    Call<List<MenuDto>> obtenerMenus();

    /** GET /api/Pizzas/Pizzas — lista de pizzas disponibles. */
    @GET("api/Pizzas/Pizzas")
    Call<List<ProductoDto>> obtenerPizzas();

    /** GET /api/Pizzas/Pizzas/Tamanios — tamaños disponibles con precio. */
    @GET("api/Pizzas/Pizzas/Tamanios")
    Call<List<TamanioDto>> obtenerTamanios();

    /** GET /api/Pizzas/Pizzas/Masas — tipos de masa disponibles. */
    @GET("api/Pizzas/Pizzas/Masas")
    Call<List<MasaDto>> obtenerMasas();

    /** GET /api/Pizzas/Pollos — lista de platillos de pollo. */
    @GET("api/Pizzas/Pollos")
    Call<List<ProductoDto>> obtenerPollos();

    /** GET /api/Pizzas/Adicionales — lista de adicionales (entradas/complementos). */
    @GET("api/Pizzas/Adicionales")
    Call<List<ProductoDto>> obtenerAdicionales();

    /** GET /api/Pizzas/Bebidas — lista de bebidas disponibles. */
    @GET("api/Pizzas/Bebidas")
    Call<List<ProductoDto>> obtenerBebidas();

    // ──────────────────────────────────────────
    // Clientes — autenticación
    // ──────────────────────────────────────────

    /**
     * POST /api/Pizzas/Clientes — registro de nuevo cliente.
     * Responde 201 (creado) o 200 (correo ya registrado), ambos con IdDto.
     */
    @POST("api/Pizzas/Clientes")
    Call<IdDto> registrarCliente(@Body ClienteModel cliente);

    /**
     * POST /api/Pizzas/Clientes/InicioDeSesiones — login con Basic Auth.
     * El header "Authorization: Basic {base64}" se construye en LoginActivity.
     * Responde 200 con un JWT (SesionResponse).
     */
    @POST("api/Pizzas/Clientes/InicioDeSesiones")
    Call<SesionResponse> iniciarSesion(@Header("Authorization") String basicAuth);

    // ──────────────────────────────────────────
    // Órdenes (protegidas — requieren JWT)
    // El AuthInterceptor inyecta "Authorization: Bearer …" automáticamente.
    // ──────────────────────────────────────────

    /**
     * POST /api/Pizzas/Clientes/Ordenes — registrar nueva orden.
     * Responde 201 con IdDto (id de la orden creada).
     */
    @POST("api/Pizzas/Clientes/Ordenes")
    Call<IdDto> crearOrden(@Body OrdenDtoIn orden);

    /**
     * GET /api/Pizzas/Clientes/Ordenes — obtener la orden activa del cliente.
     * Responde 201 con OrdenDto (el Swagger usa 201 para el GET, comportamiento del servidor).
     */
    @GET("api/Pizzas/Clientes/Ordenes")
    Call<OrdenDto> obtenerOrden();
}
