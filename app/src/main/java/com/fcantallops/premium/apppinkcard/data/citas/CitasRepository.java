package com.fcantallops.premium.apppinkcard.data.citas;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fcantallops.premium.apppinkcard.data.citas.datasource.cloud.ICloudCitasDataSource;
import com.fcantallops.premium.apppinkcard.data.citas.datasource.memory.IMemoryCitasDataSource;
import com.fcantallops.premium.apppinkcard.citas.domain.criteria.CitaCriteria;
import com.fcantallops.premium.apppinkcard.citas.domain.model.Cita;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Repositorio de Citaos
 */
public class CitasRepository implements ICitasRepository {

    private final IMemoryCitasDataSource mMemoryCitasDataSource;
    private final ICloudCitasDataSource mCloudCitasDataSource;
    private final Context mContext;

    private boolean mReload;


    public CitasRepository(IMemoryCitasDataSource memoryDataSource,
                              ICloudCitasDataSource cloudDataSource,
                              Context context) {
        mMemoryCitasDataSource = checkNotNull(memoryDataSource);
        mCloudCitasDataSource = checkNotNull(cloudDataSource);
        mContext = checkNotNull(context);
    }

    @Override
    public void getCitas(final GetCitasCallback callback, final CitaCriteria criteria) {

        // Este código lo desactive para que vieras el efecto "Endless scroll" cada vez
        // que llegas al límite de la lista.
        // Quita los comentarios para activar de nuevo la fuente en memoria.
        // Esto aumenta la velocidad de consulta.
        /*if (!mMemoryCitasDataSource.mapIsNull() && !mReload) {
            getCitasFromMemory(callback, criteria);
            return;
        }*/

        // Pon de nuevo a {mReload} en el if para activar
        // la fuente de datos en memoria.
        if (true) {
            getCitasFromServer(callback, criteria);
        } else {
            List<Cita> Citas = mMemoryCitasDataSource.find(criteria);
            if (Citas.size() > 0) {
                callback.onCitasLoaded(Citas);
            } else {
                getCitasFromServer(callback, criteria);
            }
        }

    }

    private void getCitasFromMemory(GetCitasCallback callback,
                                       CitaCriteria criteria) {

        callback.onCitasLoaded(mMemoryCitasDataSource.find(criteria));
    }

    private void getCitasFromServer(final GetCitasCallback callback,
                                       final CitaCriteria criteria) {

        if (!isOnline()) {
            callback.onDataNotAvailable("No hay conexión de red.");
            return;
        }

        mCloudCitasDataSource.getCitas(
                new ICloudCitasDataSource.CitaserviceCallback() {
                    @Override
                    public void onLoaded(List<Cita> Citas) {
                        refreshMemoryDataSource(Citas);
                        getCitasFromMemory(callback, criteria);
                    }

                    @Override
                    public void onError(String error) {
                        callback.onDataNotAvailable(error);
                    }
                },
                null);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    private void refreshMemoryDataSource(List<Cita> Citas) {
        mMemoryCitasDataSource.deleteAll();
        for (Cita Cita : Citas) {
            mMemoryCitasDataSource.save(Cita);
        }
        mReload = false;
    }

    @Override
    public void refreshCitas() {
        mReload = true;
    }

}
