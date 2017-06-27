package com.fcantallops.premium.apppinkcard.data.citas.datasource.memory;

import com.google.common.collect.Lists;
import com.fcantallops.premium.apppinkcard.citas.domain.model.Cita;
import com.fcantallops.premium.apppinkcard.citas.domain.criteria.CitaCriteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Implementación concreta de la fuente de datos en memoria
 */
public class MemoryCitasDataSource implements IMemoryCitasDataSource {
    private static HashMap<String, Cita> mCachedCitas;

    @Override
    public List<Cita> find(CitaCriteria criteria) {

        ArrayList<Cita> Citas =
                Lists.newArrayList(mCachedCitas.values());
        return criteria.match(Citas);
    }

    @Override
    public void save(Cita Cita) {
        if (mCachedCitas == null) {
            mCachedCitas = new LinkedHashMap<>();
        }
        //mCachedCitas.put(Cita.getCode(), Cita);
        mCachedCitas.put(Cita.getmCode(),Cita);
    }


    @Override
    public void deleteAll() {
        if (mCachedCitas == null) {
            mCachedCitas = new LinkedHashMap<>();
        }
        mCachedCitas.clear();
    }


    @Override
    public boolean mapIsNull() {
        return mCachedCitas == null;
    }
}
