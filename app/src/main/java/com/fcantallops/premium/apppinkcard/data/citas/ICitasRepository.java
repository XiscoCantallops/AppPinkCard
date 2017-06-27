package com.fcantallops.premium.apppinkcard.data.citas;
import com.fcantallops.premium.apppinkcard.citas.domain.model.Cita;
import com.fcantallops.premium.apppinkcard.citas.domain.criteria.CitaCriteria;

import java.util.List;

/**
 * Repositorio de productos
 */
public interface ICitasRepository {
    interface GetCitasCallback {

        void onCitasLoaded(List<Cita> Citas);

        void onDataNotAvailable(String error);
    }

    void getCitas(GetCitasCallback callback, CitaCriteria criteria);

    void refreshCitas();
}
