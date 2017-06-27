package com.fcantallops.premium.apppinkcard.citas;

import com.fcantallops.premium.apppinkcard.citas.domain.model.Cita;

import java.util.List;


public interface CitasMvp {
    interface View {
        void showCitas(List<Cita> Citas);


        void showLoadingState(boolean show);

        void showEmptyState();

        void showCitasError(String msg);

        void showCitasPage(List<Cita> Citas);

        void showLoadMoreIndicator(boolean show);

        void allowMoreData(boolean show);
    }

    interface Presenter {
        void loadCitas(boolean reload);
    }
}
