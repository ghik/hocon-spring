package hoconspring

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry

object XmlPlayground {
  def main(args: Array[String]) {
    val bdr = new SimpleBeanDefinitionRegistry
    val reader = new XmlBeanDefinitionReader(bdr)
    reader.loadBeanDefinitions("/beans.xml")

    println(bdr.getBeanDefinition("bin").getPropertyValues.get("propz"))
  }
}
