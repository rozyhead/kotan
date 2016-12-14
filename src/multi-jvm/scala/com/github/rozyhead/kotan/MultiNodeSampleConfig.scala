package com.github.rozyhead.kotan

import akka.remote.testkit.MultiNodeConfig
import com.typesafe.config.ConfigFactory

object MultiNodeSampleConfig extends MultiNodeConfig {
  val node1 = role("node1")
  val node2 = role("node2")

  commonConfig(ConfigFactory.parseString(
    """akka.actor.provider = "cluster"
      |
    """.stripMargin
  ))
}

