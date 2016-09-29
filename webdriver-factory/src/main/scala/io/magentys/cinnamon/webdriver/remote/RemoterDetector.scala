package io.magentys.cinnamon.webdriver.remote

import org.reflections.Reflections

import scala.collection.JavaConverters._

object RemoterDetector {

  def getRemoterMatchesURL(url: String): List[CinnamonRemote] = {
    val reflection = new Reflections("io.magentys")
    val classes = reflection.getSubTypesOf(classOf[CinnamonRemote]).asScala.toList

    classes.flatMap(f => {
      val remoter = f.newInstance()

      if (remoter.matchesHubUrl(url))
        Some(remoter)
      else
        None
    })
  }
}