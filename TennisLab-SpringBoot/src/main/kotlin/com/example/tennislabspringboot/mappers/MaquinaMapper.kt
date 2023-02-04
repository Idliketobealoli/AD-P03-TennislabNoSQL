package com.example.tennislabspringboot.mappers

import com.example.tennislabspringboot.dto.maquina.EncordadoraDTOvisualize
import com.example.tennislabspringboot.dto.maquina.MaquinaDTOcreate
import com.example.tennislabspringboot.dto.maquina.MaquinaDTOvisualize
import com.example.tennislabspringboot.dto.maquina.PersonalizadoraDTOvisualize
import com.example.tennislabspringboot.models.maquina.Maquina
import com.example.tennislabspringboot.models.maquina.TipoMaquina

/**
 * @author Daniel Rodriguez Muñoz
 * Mapeador para pasar de Maquina a el dto correspondiente
 */
fun Maquina.toDTO() : MaquinaDTOvisualize {
    return when (tipo) {
        TipoMaquina.ENCORDADORA -> {
            EncordadoraDTOvisualize (
                modelo = modelo,
                marca = marca,
                fechaAdquisicion = fechaAdquisicion,
                numeroSerie = numeroSerie,
                activa = activa,
                isManual = isManual!!,
                maxTension = maxTension!!,
                minTension = minTension!!
            )
        }
        TipoMaquina.PERSONALIZADORA -> {
            PersonalizadoraDTOvisualize (
                modelo = modelo,
                marca = marca,
                fechaAdquisicion = fechaAdquisicion,
                numeroSerie = numeroSerie,
                activa = activa,
                measuresManeuverability = measuresManeuverability!!,
                measuresRigidity = measuresRigidity!!,
                measuresBalance = measuresBalance!!
            )
        }
    }
}

fun toDTO(list: List<Maquina>) : List<MaquinaDTOvisualize> {
    val res = mutableListOf<MaquinaDTOvisualize>()
    list.forEach { res.add(it.toDTO()) }
    return res
}

fun fromDTO(list: List<MaquinaDTOcreate>) : List<Maquina> {
    val res = mutableListOf<Maquina>()
    list.forEach { res.add(it.fromDTO()) }
    return res
}