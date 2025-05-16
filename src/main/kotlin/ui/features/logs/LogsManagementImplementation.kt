package org.qudus.squad.ui.features.logs

import logic.use_cases.log.GetAllLogsUseCase
import logic.use_cases.log.SortOrder
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.ui.controlPanel.AdminControlPanel
import org.qudus.squad.ui.tablesDisplay.AllLogsTableDisplay

class LogsManagementImplementation (
    private val allLogsTableDisplay : AllLogsTableDisplay,

    ) : LogsManagementRepository {
    val getAllLogs :GetAllLogsUseCase=getKoin().get()
     override suspend fun recentLogsScreen() {
         val story : AdminControlPanel = getKoin().get()
        println("SELECT SORT ORDER:")
        println("1 - ASCENDING (Oldest First)")
        println("2 - DESCENDING (Newest First)")
        val sortingSelected = readlnOrNull()?.trim()
        val sortOrder = when (sortingSelected) {
            "1" -> SortOrder.ASCENDING
            else -> SortOrder.DESCENDING
        }
        if (getAllLogs.getAllLogs(sortOrder).isNotEmpty()) {
            allLogsTableDisplay.invoke(getAllLogs.getAllLogs(sortOrder))
        } else println()
        when (readlnOrNull()?.trim()) {
            else -> story.adminStory()
        }
    }
}