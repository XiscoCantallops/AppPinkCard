package com.fcantallops.premium.apppinkcard.data.citas.datasource.memory;

import com.fcantallops.premium.apppinkcard.citas.domain.model.Cita;
import com.fcantallops.premium.apppinkcard.citas.domain.criteria.CitaCriteria;

import java.util.List;

/**
 * Interfaz para fuente de datos en memoria
 */
public interface IMemoryCitasDataSource {
    List<Cita> find(CitaCriteria criteria);

    void save(Cita Cita);

    void deleteAll();

    boolean mapIsNull();
}
