package com.fcantallops.premium.apppinkcard.citas.domain.criteria;

import com.fcantallops.premium.apppinkcard.citas.domain.model.Cita;

import java.util.List;

/**
 * Patrón de especificación para los Citas
 */
public interface CitaCriteria {
    List<Cita> match(List<Cita> Citas);
}
