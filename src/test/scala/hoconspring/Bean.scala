package hoconspring

import java.{lang => jl, util => ju}
import scala.beans.BeanProperty

class Bean {
  @BeanProperty var stuff: Int = _
  @BeanProperty var dafuq: String = _
  @BeanProperty var mapzorz: ju.Map[String, Int] = _
  @BeanProperty var listzorz: ju.List[String] = _
  @BeanProperty var setzorz: ju.Set[String] = _
  @BeanProperty var nested: Bean = _

  override def toString = s"Bean($stuff, $dafuq, $mapzorz, $listzorz, $setzorz, $nested)"

  def shiet = 5
}
