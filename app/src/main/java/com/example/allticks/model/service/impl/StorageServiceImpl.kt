package com.example.allticks.model.service.impl

import android.util.Log
import com.example.allticks.model.Task
import com.example.allticks.model.service.AccountService
import com.example.allticks.model.service.StorageService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class StorageServiceImpl
@Inject
constructor(private val firestore: FirebaseFirestore, private val auth: AccountService) :
    StorageService {

  @OptIn(ExperimentalCoroutinesApi::class)
  override val tasks: Flow<List<Task>>
    get() =
        auth.currentUser.flatMapLatest { user ->
          firestore
              .collection(TASK_COLLECTION)
              .whereEqualTo(USER_ID_FIELD, user.id)
              //                    .dataObjects()
              .snapshots()
              .map { snapshot -> snapshot.toObjects(Task::class.java) }
              .map { tasks ->
                tasks.sortedWith(compareBy<Task> { it.completed }.thenByDescending { it.createdAt })
              }
        }

  override suspend fun getTask(taskId: String): Task? =
      firestore.collection(TASK_COLLECTION).document(taskId).get().await().toObject<Task?>().also {
        Log.i("TAG", it.toString())
      }

  override suspend fun save(task: Task): String {
    val taskWithUserId =
        task.copy(userId = auth.currentUserId, createdAt = System.currentTimeMillis())
    return firestore.collection(TASK_COLLECTION).add(taskWithUserId).await().id
  }

  override suspend fun update(task: Task) {
    firestore.collection(TASK_COLLECTION).document(task.id).set(task).await()
  }

  override suspend fun delete(taskId: String) {
    firestore.collection(TASK_COLLECTION).document(taskId).delete().await()
  }

  override suspend fun getUserCategories(userId: String): List<String>? {
    return try {
      val userDoc = firestore.collection("users").document(userId).get().await()
      val categories = userDoc.get("categories") as? List<String>
      Log.i("TAG", "Fetching categories for user: $userId \n $categories")
      categories
    } catch (e: Exception) {
      Log.e("TAG", "Error fetching categories for user: $userId", e)
      null
    }
  }
  override suspend fun updateUserCategories(userId: String, categories: List<String>) {
    firestore
        .collection("users")
        .document(userId)
        .set(mapOf("categories" to categories), SetOptions.merge())
        .await()
  }
  companion object {
    private const val USER_ID_FIELD = "userId"
    private const val TASK_COLLECTION = "tasks"
  }
}
