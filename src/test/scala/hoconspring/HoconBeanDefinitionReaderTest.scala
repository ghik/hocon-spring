package hoconspring

import java.{lang => jl, util => ju}

import com.typesafe.config.{ConfigFactory, Config}
import org.scalatest.FunSuite
import org.springframework.context.support.GenericApplicationContext

import scala.beans.BeanProperty

class TestBean {
  @BeanProperty var int: Int = _
  @BeanProperty var string: String = _
  @BeanProperty var strIntMap: ju.Map[String, Int] = _
  @BeanProperty var strList: ju.List[String] = _
  @BeanProperty var strSet: ju.Set[String] = _
  @BeanProperty var nestedBean: TestBean = _
  @BeanProperty var config: Config = _
}

class HoconBeanDefinitionReaderTest extends FunSuite {
  test("hocon bean definition reader should work") {
    val ctx = new GenericApplicationContext
    val rdr = new HoconBeanDefinitionReader(ctx)
    rdr.loadBeanDefinitions("testBean.conf")
    ctx.refresh()

    import scala.collection.JavaConverters._

    val testBean = ctx.getBean(classOf[TestBean])
    assert(5 === testBean.int)
    assert("lol" === testBean.string)
    assert(Map("fuu" -> 42).asJava === testBean.strIntMap)
    assert(List("a", "b").asJava === testBean.strList)
    assert(Set("A", "B").asJava === testBean.strSet)
    assert(6 === testBean.nestedBean.int)
    assert(ConfigFactory.parseString("srsly = dafuq") === testBean.config)
  }
}
