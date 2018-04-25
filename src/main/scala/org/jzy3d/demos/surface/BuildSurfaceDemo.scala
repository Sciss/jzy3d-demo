package org.jzy3d.demos.surface

import org.jzy3d.chart.Chart
import org.jzy3d.colors.colormaps.ColorMapRainbow
import org.jzy3d.colors.{Color, ColorMapper}
import org.jzy3d.demos.{AbstractDemo, Launcher}
import org.jzy3d.maths.Coord3d
import org.jzy3d.plot3d.primitives.{Point, Polygon, Shape}

import scala.collection.JavaConverters._

object BuildSurfaceDemo {
  def main(args: Array[String]): Unit = {
    Launcher.openDemo(new BuildSurfaceDemo)
  }
}

class BuildSurfaceDemo extends AbstractDemo {
  def init(): Unit = {
    val distDataProp = Array(Array(.25, .45, .20), Array(.56, .89, .45), Array(.6, .3, .7))
    val polygons = for {
      i <- distDataProp.indices.dropRight(1)
      j <- distDataProp(i).indices.dropRight(1)
    } yield {
      val polygon: Polygon = new Polygon
      val coords = Seq(
        new Coord3d(i, j, distDataProp(i)(j)),
        new Coord3d(i, j + 1, distDataProp(i)(j + 1)),
        new Coord3d(i + 1, j + 1, distDataProp(i + 1)(j + 1)),
        new Coord3d(i + 1, j, distDataProp(i + 1)(j))
      )
      for (coord <- coords) polygon.add(new Point(coord))
      polygon
    }
    val surface = new Shape(polygons.toBuffer.asJava)
    import surface._
    setColorMapper(new ColorMapper(new ColorMapRainbow, getBounds.getZmin, getBounds.getZmax, new Color(1, 1, 1, 1f)))
    setWireframeDisplayed(true)
    setWireframeColor(org.jzy3d.colors.Color.BLACK)
    val _chart = new Chart
    chart = Some(_chart)
    _chart.getScene.getGraph.add(surface)
  }
}