package com.fcantallops.premium.apppinkcard.data.citas.datasource.cloud;
import android.os.Handler;

import com.fcantallops.premium.apppinkcard.citas.domain.criteria.CitaCriteria;
import com.fcantallops.premium.apppinkcard.citas.domain.model.Cita;
import com.fcantallops.premium.apppinkcard.data.api.ErrorResponse;
import com.fcantallops.premium.apppinkcard.data.api.RestService;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.fcantallops.premium.apppinkcard.citas.data.api.ErrorResponse;
//import com.fcantallops.premium.apppinkcard.citas.citas.domain.criteria.CitaCriteria;
//import com.fcantallops.premium.apppinkcard.citas.citas.domain.model.Cita;

/**
 * Fuente de datos relacionada al servidor remoto
 */
public class CloudCitasDataSource implements ICloudCitasDataSource {

    private static final long LATENCY = 2000;

    private static HashMap<String, Cita> API_DATA;
    static { API_DATA = new LinkedHashMap<>();
        for (int i = 0; i < 100; i++) {
            int time = (int) (System.currentTimeMillis());
            Timestamp tsTemp = new Timestamp(time);
            String ts =  tsTemp.toString();

            //String[][] names = {

            //{"Juana ","Jorge ","Sergio "},

            //{"Ramos","Mir","Cabrer"}};

            //

            //

            //String nombre = names[i%3][i%3];

            addCita("Paciente" + (i + 1), "Especialista: " + (i + 1), tsTemp,"Consulta Traumatologia"); } }

    private static void addCita(String Paciente, String Medico,Timestamp FechaHora, String Servicio ) {
        Cita newCita = new Cita(Paciente, Medico,FechaHora, Servicio );
        API_DATA.put(newCita.getmCode(), newCita); }

    // Si vas a usar un dominio real o la ip de tu pc, cambia los valores de las
    // constantes o tendrás errores de ejecución.
    public static final String BASE_URL_REAL_DOMAIN
            = "http://pinkcard.esy.es/obtener_citas.php";

    public static final String BASE_URL_REAL_DEVICE =
            "http://pinkcard.esy.es/api.apppinkcard.com/v1/";
    public static final String BASE_URL_AVD = "http://10.0.2.2/api.apppinkcard.com/v1/";
    public static final String BASE_URL_GENYMOTION = "http://10.0.3.2/api.apppinkcard.com/v1/";

    private final Retrofit mRetrofit;
    private final RestService mRestService;

    public CloudCitasDataSource() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_REAL_DEVICE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRestService = mRetrofit.create(RestService.class);
    }


    //Datos conexión Servidor
    /*@Override
    public void getCitas(final CitaserviceCallback callback,
                            CitaCriteria criteria) {

        Call<List<Cita>> call = mRestService.getCitas();

        call.enqueue(new Callback<List<Cita>>() {
            @Override
            public void onResponse(Call<List<Cita>> call,
                                   Response<List<Cita>> response) {
                // Procesamos los posibles casos
                processGetCitasResponse(response, callback);

            }

            @Override
            public void onFailure(Call<List<Cita>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }*/

    @Override
    public void getCitas(final CitaserviceCallback callback,CitaCriteria criteria) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                                @Override
                   public void run() {
                                    callback.onLoaded(Lists.newArrayList(API_DATA.values())); } },
                LATENCY); }


    private void processGetCitasResponse(Response<List<Cita>> response,
                                            CitaserviceCallback callback) {
        String error = "Ha ocurrido un error";
        ResponseBody errorBody = response.errorBody();

        // ¿Hubo un error?
        if (errorBody != null) {
            // ¿Fué de la API?
            if (errorBody.contentType().subtype().equals("json")) {
                try {
                    // Parseamos el objeto
                    ErrorResponse er = new Gson()
                            .fromJson(errorBody.string(), ErrorResponse.class);
                    error = er.getMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            callback.onError(error);
        }

        // ¿LLegaron los Citaos sanos y salvos?
        if (response.isSuccessful()) {
            callback.onLoaded(response.body());
        }


    }
}
