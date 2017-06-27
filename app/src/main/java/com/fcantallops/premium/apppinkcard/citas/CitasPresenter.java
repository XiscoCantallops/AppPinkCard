package com.fcantallops.premium.apppinkcard.citas;

import com.fcantallops.premium.apppinkcard.citas.domain.criteria.CitaCriteria;
import com.fcantallops.premium.apppinkcard.citas.domain.criteria.PagingCitaCriteria;
import com.fcantallops.premium.apppinkcard.citas.domain.model.Cita;
import com.fcantallops.premium.apppinkcard.data.citas.CitasRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Presentador que escucha los eventos de la UI y luego presenta los resultados a la vista
 */
public class CitasPresenter implements CitasMvp.Presenter {

    private final com.fcantallops.premium.apppinkcard.data.citas.CitasRepository mCitasRepository;
    private final CitasMvp.View mCitasView;

    public static final int Citas_LIMIT = 20;

    private boolean isFirstLoad = true;
    private int mCurrentPage = 1;

    public CitasPresenter(CitasRepository CitasRepository,
                             CitasMvp.View CitasView) {
        mCitasRepository = checkNotNull(CitasRepository);
        mCitasView = checkNotNull(CitasView);
    }

    @Override
    public void loadCitas(final boolean reload) {
        final boolean reallyReload = reload || isFirstLoad;

        if (reallyReload) {
            mCitasView.showLoadingState(true);
            mCitasRepository.refreshCitas();
            mCurrentPage = 1;
        } else {
            mCitasView.showLoadMoreIndicator(true);
            mCurrentPage++;
        }


        // Ahora, preparamos el criterio de paginación
        CitaCriteria criteria = new PagingCitaCriteria(mCurrentPage, Citas_LIMIT);

        mCitasRepository.getCitas(
                new CitasRepository.GetCitasCallback() {
                    @Override
                    public void onCitasLoaded(List<Cita> Citas) {
                        mCitasView.showLoadingState(false);
                        processCitas(Citas, reallyReload);

                        // Ahora si, ya no es el primer refresco
                        isFirstLoad = false;
                    }

                    @Override
                    public void onDataNotAvailable(String error) {
                        mCitasView.showLoadingState(false);
                        mCitasView.showLoadMoreIndicator(false);
                        mCitasView.showCitasError(error);
                    }
                },
                criteria);

    }

    private void processCitas(List<Cita> Citas, boolean reload) {
        if (Citas.isEmpty()) {
            if (reload) {
                mCitasView.showEmptyState();
            } else {
                mCitasView.showLoadMoreIndicator(false);
            }
            mCitasView.allowMoreData(false);
        } else {

            if (reload) {
                mCitasView.showCitas(Citas);
            } else {
                mCitasView.showLoadMoreIndicator(false);
                mCitasView.showCitasPage(Citas);
            }

            mCitasView.allowMoreData(true);
        }
    }
}
