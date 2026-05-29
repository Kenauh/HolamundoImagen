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

public interface PizzasService {

    @GET("api/Pizzas/Menus")
    Call<List<MenuDto>> obtenerMenus();

    @GET("api/Pizzas/Pizzas")
    Call<List<ProductoDto>> obtenerPizzas();

    @GET("api/Pizzas/Pizzas/Tamanios")
    Call<List<TamanioDto>> obtenerTamanios();

    @GET("api/Pizzas/Pizzas/Masas")
    Call<List<MasaDto>> obtenerMasas();

    @GET("api/Pizzas/Pollos")
    Call<List<ProductoDto>> obtenerPollos();

    @GET("api/Pizzas/Adicionales")
    Call<List<ProductoDto>> obtenerAdicionales();

    @GET("api/Pizzas/Bebidas")
    Call<List<ProductoDto>> obtenerBebidas();

    @POST("api/Pizzas/Clientes")
    Call<IdDto> registrarCliente(@Body ClienteModel cliente);

    @POST("api/Pizzas/Clientes/InicioDeSesiones")
    Call<SesionResponse> iniciarSesion(@Header("Authorization") String basicAuth);

    @POST("api/Pizzas/Clientes/Ordenes")
    Call<IdDto> crearOrden(@Body OrdenDtoIn orden);

    @GET("api/Pizzas/Clientes/Ordenes")
    Call<OrdenDto> obtenerOrden();
}
