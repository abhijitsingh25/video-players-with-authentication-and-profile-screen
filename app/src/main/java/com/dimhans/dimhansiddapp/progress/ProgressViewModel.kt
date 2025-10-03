import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import com.dimhans.dimhansiddapp.progress.ProgressDataStore

class ProgressViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = ProgressDataStore(application)

    // total number of tasks
    private val totalTasks = 5

    // State for UI
    var completedTasks = mutableStateOf(0)
        private set

    init {
        // Load persisted value
        viewModelScope.launch {
            dataStore.completedTasksFlow.collect { value ->
                completedTasks.value = value
            }
        }
    }

    // Update task completion
    fun updateTaskCompletion(isChecked: Boolean) {
        viewModelScope.launch {
            completedTasks.value = if (isChecked) {
                (completedTasks.value + 1).coerceAtMost(totalTasks)
            } else {
                (completedTasks.value - 1).coerceAtLeast(0)
            }
            dataStore.saveCompletedTasks(completedTasks.value)
        }
    }

    fun getProgress(): Float {
        return completedTasks.value.toFloat() / totalTasks
    }
}
