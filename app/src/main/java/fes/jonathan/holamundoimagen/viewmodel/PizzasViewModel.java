package fes.jonathan.holamundoimagen.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import fes.jonathan.holamundoimagen.models.MasaDto;
import fes.jonathan.holamundoimagen.models.MenuDto;
import fes.jonathan.holamundoimagen.models.ProductoDto;
import fes.jonathan.holamundoimagen.models.TamanioDto;
import fes.jonathan.holamundoimagen.repository.PizzaRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PizzasViewModel extends AndroidViewModel {

    private final PizzaRepository repository;

    private final MutableLiveData<List<MenuDto>>     menusLiveData    = new MutableLiveData<>();
    private final MutableLiveData<List<ProductoDto>> pizzasLiveData   = new MutableLiveData<>();
    private final MutableLiveData<List<ProductoDto>> pollosLiveData   = new MutableLiveData<>();
    private final MutableLiveData<List<ProductoDto>> adicionalesLD    = new MutableLiveData<>();
    private final MutableLiveData<List<ProductoDto>> bebidasLiveData  = new MutableLiveData<>();
    private final MutableLiveData<List<TamanioDto>>  tamaniosLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<MasaDto>>     masasLiveData    = new MutableLiveData<>();

    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>(false);
    private final MutableLiveData<String>  errorLiveData   = new MutableLiveData<>();

    public PizzasViewModel(@NonNull Application application) {
        super(application);
        repository = new PizzaRepository();
    }

    public LiveData<List<MenuDto>>     getMenus()       { return menusLiveData; }
    public LiveData<List<ProductoDto>> getPizzas()      { return pizzasLiveData; }
    public LiveData<List<ProductoDto>> getPollos()      { return pollosLiveData; }
    public LiveData<List<ProductoDto>> getAdicionales() { return adicionalesLD; }
    public LiveData<List<ProductoDto>> getBebidas()     { return bebidasLiveData; }
    public LiveData<List<TamanioDto>>  getTamanios()    { return tamaniosLiveData; }
    public LiveData<List<MasaDto>>     getMasas()       { return masasLiveData; }
    public LiveData<Boolean>           isLoading()      { return loadingLiveData; }
    public LiveData<String>            getError()       { return errorLiveData; }

    public void cargarMenus() {
        enqueue(repository.obtenerMenus(), menusLiveData);
    }

    public void cargarPizzas() {
        enqueue(repository.obtenerPizzas(), pizzasLiveData);
    }

    public void cargarPollos() {
        enqueue(repository.obtenerPollos(), pollosLiveData);
    }

    public void cargarAdicionales() {
        enqueue(repository.obtenerAdicionales(), adicionalesLD);
    }

    public void cargarBebidas() {
        enqueue(repository.obtenerBebidas(), bebidasLiveData);
    }

    public void cargarOpcionesDePizza() {
        enqueue(repository.obtenerTamanios(), tamaniosLiveData);
        enqueue(repository.obtenerMasas(),    masasLiveData);
    }

    private <T> void enqueue(Call<List<T>> call, MutableLiveData<List<T>> liveData) {
        loadingLiveData.postValue(true);
        call.enqueue(new Callback<List<T>>() {
            @Override
            public void onResponse(@NonNull Call<List<T>> call,
                                   @NonNull Response<List<T>> response) {
                loadingLiveData.postValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body());
                } else {
                    errorLiveData.postValue("Error " + response.code()
                            + " al cargar datos. Intenta de nuevo.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<T>> call, @NonNull Throwable t) {
                loadingLiveData.postValue(false);
                errorLiveData.postValue("Sin conexión. Verifica tu internet.");
            }
        });
    }
}
