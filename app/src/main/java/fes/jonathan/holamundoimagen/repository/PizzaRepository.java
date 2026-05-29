package fes.jonathan.holamundoimagen.repository;

import fes.jonathan.holamundoimagen.ApiClient;
import fes.jonathan.holamundoimagen.models.IdDto;
import fes.jonathan.holamundoimagen.models.MasaDto;
import fes.jonathan.holamundoimagen.models.MenuDto;
import fes.jonathan.holamundoimagen.models.OrdenDto;
import fes.jonathan.holamundoimagen.models.OrdenDtoIn;
import fes.jonathan.holamundoimagen.models.ProductoDto;
import fes.jonathan.holamundoimagen.models.TamanioDto;
import fes.jonathan.holamundoimagen.services.PizzasService;

import java.util.List;

import retrofit2.Call;

public class PizzaRepository {

    private final PizzasService service;

    public PizzaRepository() {
        this.service = ApiClient.getService();
    }

    public Call<List<MenuDto>> obtenerMenus() {
        return service.obtenerMenus();
    }

    public Call<List<ProductoDto>> obtenerPizzas() {
        return service.obtenerPizzas();
    }

    public Call<List<TamanioDto>> obtenerTamanios() {
        return service.obtenerTamanios();
    }

    public Call<List<MasaDto>> obtenerMasas() {
        return service.obtenerMasas();
    }

    public Call<List<ProductoDto>> obtenerPollos() {
        return service.obtenerPollos();
    }

    public Call<List<ProductoDto>> obtenerAdicionales() {
        return service.obtenerAdicionales();
    }

    public Call<List<ProductoDto>> obtenerBebidas() {
        return service.obtenerBebidas();
    }

    public Call<IdDto> crearOrden(OrdenDtoIn orden) {
        return service.crearOrden(orden);
    }

    public Call<OrdenDto> obtenerOrden() {
        return service.obtenerOrden();
    }
}
