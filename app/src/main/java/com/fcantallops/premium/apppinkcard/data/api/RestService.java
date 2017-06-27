package com.fcantallops.premium.apppinkcard.data.api;

import com.fcantallops.premium.apppinkcard.citas.domain.model.Cita;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Segmentos de URL donde actuaremos
 */
public interface RestService {
    @GET("Citas")
    Call<List<Cita>> getCitas();
}
