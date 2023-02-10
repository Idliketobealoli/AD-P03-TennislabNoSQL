// Generated by Ktorfit
package koin.services.ktorfit

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.`internal`.*
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import koin.dto.tarea.TareaDTOFromApi
import koin.dto.user.UserDTOfromAPI

class _IKtorFitImpl(
  private val client: KtorfitClient,
) : IKtorFit {
  override suspend fun getAll(): List<UserDTOfromAPI> {
    val requestData = RequestData(method="GET",
        relativeUrl="users",
        returnTypeData=TypeData("kotlin.collections.List",listOf(TypeData("koin.dto.user.UserDTOfromAPI"))))
            

    return client.suspendRequest<List<UserDTOfromAPI>, UserDTOfromAPI>(requestData)!!
  }

  override suspend fun getById(id: Int): UserDTOfromAPI? {
    val requestData = RequestData(method="GET",
        relativeUrl="users/{id}",
        returnTypeData=TypeData("UserDTOfromAPI?"),
        paths = listOf(PathData("id","$id",false))) 

    return client.suspendRequest<UserDTOfromAPI?, UserDTOfromAPI?>(requestData)
  }

  override suspend fun getAllTareas(): List<TareaDTOFromApi> {
    val requestData = RequestData(method="GET",
        relativeUrl="todos",
        returnTypeData=TypeData("kotlin.collections.List",listOf(TypeData("koin.dto.tarea.TareaDTOFromApi"))))
            

    return client.suspendRequest<List<TareaDTOFromApi>, TareaDTOFromApi>(requestData)!!
  }

  override suspend fun saveTareas(tarea: TareaDTOFromApi): TareaDTOFromApi {
    val requestData = RequestData(method="POST",
        relativeUrl="todos",
        bodyData = tarea,
        returnTypeData=TypeData("koin.dto.tarea.TareaDTOFromApi")) 

    return client.suspendRequest<TareaDTOFromApi, TareaDTOFromApi>(requestData)!!
  }
}

fun Ktorfit.createIKtorFit(): IKtorFit = _IKtorFitImpl(KtorfitClient(this))
