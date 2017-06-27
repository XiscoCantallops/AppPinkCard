package com.fcantallops.premium.apppinkcard.citas.domain.criteria;

import com.fcantallops.premium.apppinkcard.citas.domain.model.Cita;

import java.util.ArrayList;
import java.util.List;

/**
 * Criterio para determinar las Citas que pertenecen a una página específica
 */
public class PagingCitaCriteria implements CitaCriteria {

    private final int mPage;
    private final int mLimit;

    public PagingCitaCriteria(int page, int limit) {
        mPage = page;
        mLimit = limit;
    }

    @Override
    public List<Cita> match(List<Cita> Citas) {
        List<Cita> criteriaCitas = new ArrayList<>();
        int a, b, size, numPages;

        // Sanidad
        if (mLimit <= 0 || mPage <= 0) {
            return criteriaCitas;
        }

        size = Citas.size();

        // ¿La página es más grande que el contenido?
        if (size < mLimit && mPage == 1) {
            return Citas;
        }

        numPages = size / mLimit;


        if (mPage > numPages) {
            return criteriaCitas;
        }


        a = (mPage - 1) * mLimit;

        // ¿Llegamos al final?
        if (a == size) {
            return criteriaCitas;
        }

        b = a + mLimit;

        criteriaCitas = Citas.subList(a, b);

        return criteriaCitas;

    }
}
