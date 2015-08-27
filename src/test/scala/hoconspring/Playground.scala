package hoconspring

import com.typesafe.config.ConfigFactory
import org.springframework.context.support.GenericApplicationContext


object Playground {
  def main(args: Array[String]): Unit = {
    val bdr = new HoconBeanDefinitionReader(new GenericApplicationContext())
    bdr.loadBeanDefinitions(ConfigFactory.parseResources("beans.conf"))
  }

}
