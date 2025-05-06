package logic.use_cases.tasks

import logic.use_cases.tasks.GetAllTasksByProjectIdUseCase.Companion.NO_TASK_FOUND
import logic.exceptions.TaskNotFoundException
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.utils.GenerateUUID

class AssignTaskToUserUseCase(
    private val taskRepository: TaskRepository,
) {
    fun assignTaskToUser(userId : String = GenerateUUID().generate(), taskId : String = GenerateUUID().generate()): Boolean {
        taskRepository.getTaskById(taskId)
            ?: throw TaskNotFoundException(NO_TASK_FOUND)

        return taskRepository.assignTaskToUser(taskId, userId)
    }
}