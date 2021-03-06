package org.jzy3d.demos

import org.jzy3d.chart.Chart
import org.jzy3d.chart.factories.AWTChartComponentFactory
import org.jzy3d.colors.colormaps.ColorMapRainbow
import org.jzy3d.colors.{Color, ColorMapper}
import org.jzy3d.maths.Coord3d
import org.jzy3d.plot3d.primitives.{Point, Polygon, Shape}
import org.jzy3d.plot3d.rendering.canvas.Quality

import scala.collection.JavaConverters._

object BuildSurfaceDemo extends Demo {
  def mkChart(): Chart = {
    val distDataProp = Array(Array(.25, .45, .20), Array(.56, .89, .45), Array(.6, .3, .7))
    val polygons = for {
      i <- distDataProp   .indices.init
      j <- distDataProp(i).indices.init
    } yield {
      val polygon: Polygon = new Polygon
      val coordinates = Seq(
        new Coord3d(i    , j    , distDataProp(i    )(j    )),
        new Coord3d(i    , j + 1, distDataProp(i    )(j + 1)),
        new Coord3d(i + 1, j + 1, distDataProp(i + 1)(j + 1)),
        new Coord3d(i + 1, j    , distDataProp(i + 1)(j    ))
      )
      for (c <- coordinates) polygon.add(new Point(c))
      polygon
    }
    val surface = new Shape(polygons.toBuffer.asJava)
    import surface._
    setColorMapper(new ColorMapper(new ColorMapRainbow, getBounds.getZmin, getBounds.getZmax, new Color(1, 1, 1, 1f)))
    setWireframeDisplayed(true)
    setWireframeColor(org.jzy3d.colors.Color.BLACK)
    val f = new AWTChartComponentFactory
    val chart = new Chart(f, Quality.Advanced)
    chart.getScene.getGraph.add(surface)
    chart
  }
}