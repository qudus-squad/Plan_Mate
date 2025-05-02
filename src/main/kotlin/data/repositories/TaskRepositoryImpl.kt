package org.qudus.squad.data.repositories

import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.Task

class TaskRepositoryImpl(
    private val taskRepository: TaskRepository
): TaskRepository {
    override fun createNewTask(task: Task){
        taskRepository.createNewTask(task)
    }

    override fun editExistingTask(updatedTask: Task){
        taskRepository.editExistingTask(updatedTask)
    }

    override fun getAllTasksByProjectId(id: String): List<Task> {
        return taskRepository.getAllTasksByProjectId(id)
    }

    override fun getTaskById(id: String): Task {
       return taskRepository.getTaskById(id)
    }

    override fun switchTaskState(taskId: String, newTaskState: TaskState) {
        taskRepository.switchTaskState(taskId , newTaskState)
    }

    override fun deleteTaskById(id: String) {
       taskRepository.deleteTaskById(id)
    }

    override fun assignTaskToUser(taskId: String, userId: String) {
        taskRepository.assignTaskToUser(taskId , userId)
    }

    override fun unAssignTask(taskId: String) {
        taskRepository.unAssignTask(taskId)
    }
}