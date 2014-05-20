package hoconspring

import com.typesafe.config.ConfigFactory


object Playground {
  def main(args: Array[String]) {
    println(ConfigFactory.parseString("stuff = \"\\\\\"").getString("stuff"))
  }

}
