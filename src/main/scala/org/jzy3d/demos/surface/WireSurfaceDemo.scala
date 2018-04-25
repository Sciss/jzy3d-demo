package org.jzy3d.demos.surface

import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.factories.AWTChartComponentFactory
import org.jzy3d.colors.colormaps.ColorMapRainbow
import org.jzy3d.colors.{Color, ColorMapper}
import org.jzy3d.demos.{AbstractDemo, Launcher}
import org.jzy3d.maths.{Dimension, Range}
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid
import org.jzy3d.plot3d.builder.{Builder, Mapper}
import org.jzy3d.plot3d.primitives.Shape
import org.jzy3d.plot3d.rendering.canvas.Quality
import org.jzy3d.plot3d.rendering.legends.colorbars.AWTColorbarLegend

object WireSurfaceDemo {
  def main(args: Array[String]): Unit = {
    Launcher.openDemo(new WireSurfaceDemo)
  }
}

class WireSurfaceDemo extends AbstractDemo {

  def init(): Unit = {
    val mapper: Mapper = (x: Double, y: Double) => 10 * math.sin(x / 10) * math.cos(y / 20) * x
    val range = new Range(-150, 150)
    val steps = 50
    val surface: Shape = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper)
    import surface._
    setColorMapper(new ColorMapper(new ColorMapRainbow, getBounds.getZmin, getBounds.getZmax, new Color(1, 1, 1, .5f)))
    setFaceDisplayed(true)
    setWireframeDisplayed(true)
    setWireframeColor(Color.BLACK)
    val f = new AWTChartComponentFactory
    val _chart = new AWTChart(f, Quality.Advanced)
    chart = Some(_chart)
    _chart.getScene.getGraph.add(surface)
    val cBar = new AWTColorbarLegend(surface, _chart.getView.getAxe.getLayout)
    cBar.setMinimumSize(new Dimension(100, 600))
    surface.setLegend(cBar)
  }
}