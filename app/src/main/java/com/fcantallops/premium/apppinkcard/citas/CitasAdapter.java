package com.fcantallops.premium.apppinkcard.citas;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fcantallops.premium.apppinkcard.R;
import com.fcantallops.premium.apppinkcard.citas.domain.model.Cita;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Adaptador de Citas
 */
public class CitasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements DataLoading {

    private List<Cita> mCitas;
    private CitaItemListener mItemListener;

    private final static int TYPE_Cita = 1;
    private final static int TYPE_LOADING_MORE = 2;

    private boolean mLoading = false;
    private boolean mMoreData = false;


    public CitasAdapter(List<Cita> Citas, CitaItemListener itemListener) {
        setList(Citas);
        mItemListener = itemListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getDataItemCount() && getDataItemCount() > 0) {
            return TYPE_Cita;
        }
        return TYPE_LOADING_MORE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;

        if (viewType == TYPE_LOADING_MORE) {
            //view = inflater.inflate(R.layout.item_loading_footer, parent, false);
            view = inflater.inflate(R.layout.item_loading_footer,parent,false);
            return new LoadingMoreHolder(view);
        }

        view = inflater.inflate(R.layout.item_cita, parent, false);
        return new CitasHolder(view, mItemListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_Cita:
                Cita Cita = mCitas.get(position);
                CitasHolder CitasHolder = (CitasHolder) viewHolder;

                CitasHolder.servicio.setText(Cita.getmServicio());
                CitasHolder.medico.setText(Cita.getmMedico());




                //CitasHolder.fecha.setText(Cita.getmFechaHora());

                //czas.setText(getDate(timestamp ));
                //CitasHolder.fecha.setText(getDate);

                //CitasHolder.price.setText(Cita.getFormatedPrice());
                //CitasHolder.paciente.setText(Cita.getmPaciente());
                //CitasHolder.unitsInStock.setText(Cita.getFormattedUnitsInStock());

            /*    Glide.with(viewHolder.itemView.getContext())
                        .load(Cita.get
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(CitasHolder.featuredImage);*/

                break;
            case TYPE_LOADING_MORE:
                bindLoadingViewHolder((LoadingMoreHolder) viewHolder, position);
                break;
        }

    }

    private void bindLoadingViewHolder(LoadingMoreHolder viewHolder, int position) {
        viewHolder.progress.setVisibility((position > 0 && mLoading && mMoreData)
                ? View.VISIBLE : View.INVISIBLE);
    }

    public void replaceData(List<Cita> notes) {
        setList(notes);
        notifyDataSetChanged();
    }

    private void setList(List<Cita> notes) {
        mCitas = checkNotNull(notes);
    }

    public void addData(List<Cita> Citas) {
        mCitas.addAll(Citas);
    }

    @Override
    public int getItemCount() {
        return getDataItemCount() + (mLoading ? 1 : 0);
    }

    public Cita getItem(int position) {
        return mCitas.get(position);
    }


    public void dataStartedLoading() {
        if (mLoading) return;
        mLoading = true;
        Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                notifyItemInserted(getLoadingMoreItemPosition());
            }
        });

    }

    public void dataFinishedLoading() {
        if (!mLoading) return;
        final int loadingPos = getLoadingMoreItemPosition();
        mLoading = false;
        Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                notifyItemRemoved(loadingPos);
            }
        });

    }

    public void setMoreData(boolean more) {
        mMoreData = more;
    }


    private int getLoadingMoreItemPosition() {
        return mLoading ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    public int getDataItemCount() {
        return mCitas.size();
    }


    @Override
    public boolean isLoadingData() {
        return mLoading;
    }

    @Override
    public boolean isThereMoreData() {
        return mMoreData;
    }



  //Holder
    public class CitasHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       // public TextView paciente;
        public TextView medico;
        public ImageView featuredImage;
        public TextView servicio;

        public TextView fecha;
        public TextView hora;

        private CitaItemListener mItemListener;

        public CitasHolder(View itemView, CitaItemListener listener) {
            super(itemView);
            mItemListener = listener;
            medico = (TextView) itemView.findViewById(R.id.cita_medico);
            servicio = (TextView) itemView.findViewById(R.id.cita_servicio);

            fecha = (TextView) itemView.findViewById(R.id.cita_fecha);
            hora = (TextView) itemView.findViewById(R.id.cita_hora);


            featuredImage = (ImageView) itemView.findViewById(R.id.cita_featured_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Cita Cita = getItem(position);
            mItemListener.onCitaClick(Cita);

        }
    }


    private class LoadingMoreHolder extends RecyclerView.ViewHolder {
        public ProgressBar progress;

        public LoadingMoreHolder(View view) {
            super(view);
            progress = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }

    public interface CitaItemListener {
        void onCitaClick(Cita clickedNote);

    }

}
