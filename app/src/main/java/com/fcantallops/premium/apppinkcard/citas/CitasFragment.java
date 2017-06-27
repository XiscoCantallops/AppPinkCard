package com.fcantallops.premium.apppinkcard.citas;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fcantallops.premium.apppinkcard.R;
import com.fcantallops.premium.apppinkcard.di.DependencyProvider;
import com.fcantallops.premium.apppinkcard.citas.domain.model.Cita;
import com.fcantallops.premium.apppinkcard.citas.CitasAdapter.CitaItemListener;


import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento para mostrar la lista de Citas
 */
public class CitasFragment extends Fragment
        implements CitasMvp.View {

    private RecyclerView mCitasList;
    private CitasAdapter mCitasAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mEmptyView;
    private CitaItemListener mItemListener = new CitaItemListener() {
        @Override
        public void onCitaClick(Cita clickedCita) {
            // Aquí lanzarías la pantalla de detalle del Citao
        }
    };

    private CitasPresenter mCitasPresenter;


    public CitasFragment() {
        // Required empty public constructor
    }

    public static CitasFragment newInstance() {
        return new CitasFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCitasAdapter = new CitasAdapter(new ArrayList<Cita>(0), mItemListener);

        mCitasPresenter = new CitasPresenter(
                DependencyProvider.provideCitasRepository(getActivity()),
                this);

        //mCitasPresenter = new CitasPresenter(DependencyProvider.provideCitasRepository(getActivity()),this);

        /*mProductsPresenter = new ProductsPresenter(
                DependencyProvider.provideProductsRepository(getActivity()),
                this);*/

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_citas, container, false);

        // Referencias UI
        mCitasList = (RecyclerView) root.findViewById(R.id.Citas_list);
        mEmptyView = root.findViewById(R.id.noCitas);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);

        // Setup
        setUpCitasList();
        setUptRefreshLayout();

        if (savedInstanceState != null) {
            hideList(false);
        }

        return root;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            mCitasPresenter.loadCitas(false);
        }
    }

    private void setUpCitasList() {
        mCitasList.setAdapter(mCitasAdapter);
        mCitasList.setHasFixedSize(true);

        final LinearLayoutManager layoutManager =
                (LinearLayoutManager) mCitasList.getLayoutManager();

        // Se agrega escucha de scroll infinito.
        mCitasList.addOnScrollListener(
                new InfinityScrollListener(mCitasAdapter, layoutManager) {
                    @Override
                    public void onLoadMore() {
                        mCitasPresenter.loadCitas(false);
                    }
                });
    }

    private void setUptRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCitasPresenter.loadCitas(true);
            }
        });
    }


    @Override
    public void showCitas(List<Cita> Citas) {

        mCitasAdapter.replaceData(Citas);

        hideList(false);
    }

    @Override
    public void showLoadingState(final boolean show) {
        if (getView() == null) {
            return;
        }

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(show);
            }
        });
    }

    @Override
    public void showEmptyState() {
        hideList(true);
    }

    private void hideList(boolean hide) {
        mCitasList.setVisibility(hide ? View.GONE : View.VISIBLE);
        mEmptyView.setVisibility(hide ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showCitasError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showCitasPage(List<Cita> Citas) {
        mCitasAdapter.addData(Citas);
        hideList(false);
    }

    @Override
    public void showLoadMoreIndicator(boolean show) {
        if (!show) {
            mCitasAdapter.dataFinishedLoading();
        } else {
            mCitasAdapter.dataStartedLoading();
        }
    }

    @Override
    public void allowMoreData(boolean allow) {
        mCitasAdapter.setMoreData(allow);
    }


}
