package com.victorursan.utils

import org.apache.mesos.Protos
import org.apache.mesos.Protos.ContainerInfo.DockerInfo
import org.apache.mesos.Protos._

import scala.util.Random

/**
 * Created by victor on 4/24/16.
 */
object TaskHandler {

  private def createNexTaskID: TaskID =
    TaskID.newBuilder()
      .setValue(Math.abs(Random.nextInt).toString)
      .build()

  private def createDockerInfo(image: String): DockerInfo =
    DockerInfo.newBuilder()
      .setImage(image)
      .setNetwork(ContainerInfo.DockerInfo.Network.HOST)
      .build()

  private def createContainerInfo(dockerInfo: DockerInfo): ContainerInfo =
    ContainerInfo.newBuilder()
      .setType(ContainerInfo.Type.DOCKER)
      .setDocker(dockerInfo)
      .build()

  private def createScalarResource(name: String, value: Double): Protos.Resource =
    Resource.newBuilder()
      .setName(name)
      .setType(Value.Type.SCALAR)
      .setScalar(Value.Scalar.newBuilder().setValue(value))
      .build()

  private def createDockerTask(taskID: TaskID, offer: Offer, containerInfo: ContainerInfo, dockerEntity: DockerEntity): TaskInfo =
    TaskInfo.newBuilder()
      .setName(dockerEntity.name)
      .setTaskId(taskID)
      .setSlaveId(offer.getSlaveId)
      .addResources(createScalarResource("cpus", dockerEntity.resource.cpu))
      .addResources(createScalarResource("mem", dockerEntity.resource.mem))
      .setContainer(containerInfo)
      .setCommand(CommandInfo.newBuilder().setShell(false))
      .build()

  def createTaskWith(offer: Offer, dockerEntity: DockerEntity): TaskInfo = {
    // generate a unique task ID
    val taskID: TaskID = createNexTaskID

    // docker info
    val dockerInfo: DockerInfo = createDockerInfo(dockerEntity.image)

    // container info
    val containerInfo: ContainerInfo = createContainerInfo(dockerInfo)

    // task info
    createDockerTask(taskID, offer, containerInfo, dockerEntity)
  }
}