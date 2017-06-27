package com.fcantallops.premium.apppinkcard.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fcantallops.premium.apppinkcard.data.citas.CitasRepository;
import com.fcantallops.premium.apppinkcard.data.citas.datasource.cloud.CloudCitasDataSource;
import com.fcantallops.premium.apppinkcard.data.citas.datasource.memory.MemoryCitasDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Contenedor de dependencias
 */
public final class DependencyProvider {

    private static Context mContext;
    private static MemoryCitasDataSource memorySource = null;
    private static CloudCitasDataSource cloudSource = null;
    private static CitasRepository mCitasRepository = null;

    private DependencyProvider() {
    }

    public static CitasRepository provideCitasRepository(@NonNull Context context) {
        mContext = checkNotNull(context);
        if (mCitasRepository == null) {
            mCitasRepository = new CitasRepository(getMemorySource(),
                    getCloudSource(), context);
        }
        return mCitasRepository;
    }

    public static MemoryCitasDataSource getMemorySource() {
        if (memorySource == null) {
            memorySource = new MemoryCitasDataSource();
        }
        return memorySource;
    }

    public static CloudCitasDataSource getCloudSource() {
        if (cloudSource == null) {
            cloudSource = new CloudCitasDataSource();
        }
        return cloudSource;
    }
}
