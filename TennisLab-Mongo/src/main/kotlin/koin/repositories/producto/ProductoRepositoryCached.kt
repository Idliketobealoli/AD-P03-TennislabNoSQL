package koin.repositories.producto

import koin.mappers.toDTO
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import koin.models.producto.Producto
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import org.litote.kmongo.Id
import koin.services.cache.producto.IProductoCache
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import java.util.*

@Single
@Named("ProductoRepositoryCached")
class ProductoRepositoryCached(
    @Named("ProductoRepository")
    private val repo: IProductoRepository<Id<Producto>>,
    private val cache: IProductoCache
): IProductoRepository<Id<Producto>> {
    private var refreshJob: Job? = null
    private var listSearches = mutableListOf<Producto>()

    init { refreshCache() }

    private fun refreshCache() {
        if (refreshJob != null) refreshJob?.cancel()

        refreshJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                if(listSearches.isNotEmpty()) {
                    listSearches.forEach {
                        cache.cache.put(it.uuid, it)
                    }
                }

                delay(cache.refreshTime)
            }
        }
    }

    suspend fun findAllAsFlow() = flow {
        do {
            emit(toDTO(repo.findAll().toList()))
            delay(1000)
        } while (true)
    }

    override suspend fun findAll(): Flow<Producto> = withContext(Dispatchers.IO) {
        repo.findAll()
    }

    override suspend fun findByUUID(id: UUID): Producto? = withContext(Dispatchers.IO) {
        var result: Producto? = null

        cache.cache.asMap().forEach { if (it.key == id) result = it.value }
        if (result != null) {
            listSearches.add(result!!)
            return@withContext result
        }

        result = repo.findByUUID(id)
        if (result != null) listSearches.add(result!!)

        result
    }

    override suspend fun save(entity: Producto): Producto = withContext(Dispatchers.IO) {
        listSearches.add(entity)
        repo.save(entity)
        entity
    }

    override suspend fun decreaseStock(id: Id<Producto>): Producto? = withContext(Dispatchers.IO) {
        val result = repo.decreaseStock(id)
        if (result != null) listSearches.add(result)
        result
    }

    override suspend fun delete(id: Id<Producto>): Producto? = withContext(Dispatchers.IO) {
        val entity = repo.delete(id)
        if (entity != null){
            listSearches.removeIf { it.uuid == entity.uuid }
            cache.cache.invalidate(entity.uuid)
        }
        entity
    }

    override suspend fun findById(id: Id<Producto>): Producto? = withContext(Dispatchers.IO) {
        val result = repo.findById(id)
        if (result != null) listSearches.add(result)
        result
    }
}