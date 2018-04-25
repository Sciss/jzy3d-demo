package org.jzy3d.demos.surface

import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.controllers.keyboard.camera.AWTCameraKeyController
import org.jzy3d.chart.factories.AWTChartComponentFactory
import org.jzy3d.colors.colormaps.ColorMapRainbow
import org.jzy3d.colors.{Color, ColorMapper}
import org.jzy3d.demos.{AbstractDemo, Launcher}
import org.jzy3d.maths.Range
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid
import org.jzy3d.plot3d.builder.{Builder, Mapper}
import org.jzy3d.plot3d.primitives.Shape
import org.jzy3d.plot3d.rendering.canvas.Quality

object ColorWaveDemo {
  def main(args: Array[String]): Unit = {
    Launcher.openDemo(new ColorWaveDemo)
  }
}

class ColorWaveDemo extends AbstractDemo {
  def init(): Unit = {
    val mapper: Mapper = (x: Double, y: Double) => x * math.sin(x * y)
    val range = new Range(-3, 3)
    val steps = 80
    val surface: Shape = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper)
    import surface._
    setColorMapper(new ColorMapper(new ColorMapRainbow, getBounds.getZmin, getBounds.getZmax, new Color(1, 1, 1, .5f)))
    setFaceDisplayed(true)
    setWireframeDisplayed(false)
    val f = new AWTChartComponentFactory
    val _chart = new AWTChart(f, Quality.Advanced)
    chart = Some(_chart)
    _chart.getScene.getGraph.add(surface)
    _chart.addController(new AWTCameraKeyController)
  }
}