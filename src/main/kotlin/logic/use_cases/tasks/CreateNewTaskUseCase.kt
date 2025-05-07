package logic.use_cases.tasks

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType
import org.qudus.squad.model.entity.Task

class CreateNewTaskUseCase(
    private val taskRepository: TaskRepository,
    private val logRepository: LogRepository,
    private val taskDataValidator: TaskDataValidationUseCase
) {
    suspend fun createNewTask(userName: String, task: Task){
        if (taskDataValidator.validateTaskValues(task)){
            taskRepository.createNewTask(task)
            logRepository.addNewLog(LogEntry(userName, task.id, TargetType.TASK, "add new task"))
        }
    }
}