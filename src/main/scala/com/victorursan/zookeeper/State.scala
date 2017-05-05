package com.victorursan.zookeeper

import com.victorursan.state.Bean
import org.apache.mesos.v1.Protos.TaskID

/**
  * Created by victor on 4/24/17.
  */
trait State {
  def getNextId: String

  def getOverview: Map[String, String]

  def addToOverview(taskId: String, state: String): Map[String, String]

  def removeFromOverview(taskId: String): Map[String, String]

  def awaitingBeans: Set[Bean]

  def tasksToKill: Set[TaskID]

  def addToAccept(bean: Bean): Set[Bean]

  def addToAccept(beans: Set[Bean]): Set[Bean]

  def removeFromAccept(bean: Bean): Set[Bean]

  def removeFromAccept(beans: Set[Bean]): Set[Bean]

  def addToRunningUnpacked(bean: Bean): Set[Bean]

  def addToOldBeans(bean: Bean): Set[Bean]

  def addToOldBeans(beans: Set[Bean]): Set[Bean]

  def oldBeans: Set[Bean]

  def removeOldBean(bean: Bean): Set[Bean]

  def removeOldBean(beans: Set[Bean]): Set[Bean]

  def addToRunningUnpacked(beans: Set[Bean]): Set[Bean]

  def runningUnpacked: Set[Bean]

  def removeRunningUnpacked(bean: Bean): Set[Bean]

  def removeRunningUnpacked(beans: Set[Bean]): Set[Bean]

  def addToKill(taskID: TaskID): Set[TaskID]

  def removeFromKill(taskID: TaskID): Set[TaskID]

  def removeFromKill(taskIDs: Set[TaskID]): Set[TaskID]
}
