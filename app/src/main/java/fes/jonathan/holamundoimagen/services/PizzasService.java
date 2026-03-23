package fes.jonathan.holamundoimagen.services;

import fes.jonathan.holamundoimagen.models.ClienteModel;
import fes.jonathan.holamundoimagen.models.PizzaModel;
import fes.jonathan.holamundoimagen.models.SesionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PizzasService {

    // Registro de cliente
    @POST("api/Pizzas/Clientes")
    Call<SesionResponse> registrarCliente(@Body ClienteModel cliente);

    // Login con Basic Auth
    @POST("api/Pizzas/Clientes/InicioDeSesiones")
    Call<SesionResponse> iniciarSesion(@Header("Authorization") String basicAuth);

    // Obtener lista de pizzas
    @GET("api/Pizzas")
    Call<List<PizzaModel>> obtenerPizzas();
}