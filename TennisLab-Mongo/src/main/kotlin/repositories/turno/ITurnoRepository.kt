package repositories.turno

import kotlinx.coroutines.flow.Flow
import models.turno.Turno
import java.util.*

interface ITurnoRepository<ID> {
    suspend fun findAll(): Flow<Turno>
    suspend fun findById(id: ID): Turno?
    suspend fun save(entity: Turno): Turno
    suspend fun delete(id: ID): Turno?
    suspend fun setFinalizado(id: ID): Turno?
    suspend fun findByUUID(id: UUID): Turno?
}