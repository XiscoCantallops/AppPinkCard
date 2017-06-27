package com.fcantallops.premium.apppinkcard.data.citas.datasource.cloud;

import com.fcantallops.premium.apppinkcard.citas.domain.model.Cita;
import com.fcantallops.premium.apppinkcard.citas.domain.criteria.CitaCriteria;

import java.util.List;

/**
 * Interfaz de comunicación con el repositorio para la fuente de datos remota
 */
public interface ICloudCitasDataSource {

    interface CitaserviceCallback {

        void onLoaded(List<Cita> Citas);

        void onError(String error);

    }

    void getCitas(CitaserviceCallback callback, CitaCriteria criteria);

}
