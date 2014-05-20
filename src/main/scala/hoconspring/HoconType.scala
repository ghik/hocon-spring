package hoconspring

import java.{lang => jl, util => ju}
import com.typesafe.config._
import scala.collection.JavaConverters._
import scala.Some

trait HoconType[T] {

  protected def requireNonNull(value: ConfigValue) = {
    require(value != null, s"No value found")
    value
  }

  protected def requireType(requiredType: ConfigValueType, value: ConfigValue) {
    requireNonNull(value)
    require(value.valueType == requiredType, s"Value at ${value.origin} has type, ${value.valueType}, required $requiredType")
  }

  def get(value: ConfigValue): T
}

object HoconType {

  import ConfigValueType._

  implicit object anyHoconType extends HoconType[Any] {
    def get(value: ConfigValue) =
      requireNonNull(value).unwrapped
  }

  implicit object anyRefHoconType extends HoconType[AnyRef] {
    def get(value: ConfigValue) =
      requireNonNull(value).unwrapped
  }

  implicit object nullHoconType extends HoconType[Null] {
    def get(value: ConfigValue) = {
      requireType(NULL, value)
      null
    }
  }

  implicit object stringHoconType extends HoconType[String] {
    def get(value: ConfigValue) = {
      requireType(STRING, value)
      value.unwrapped.asInstanceOf[String]
    }
  }

  implicit object booleanHoconType extends HoconType[Boolean] {
    def get(value: ConfigValue) = {
      requireType(BOOLEAN, value)
      value.unwrapped.asInstanceOf[Boolean]
    }
  }

  implicit object numberHoconType extends HoconType[jl.Number] {
    def get(value: ConfigValue) = {
      requireType(NUMBER, value)
      value.unwrapped.asInstanceOf[jl.Number]
    }
  }

  implicit object intHoconType extends HoconType[Int] {
    def get(value: ConfigValue) = {
      requireType(NUMBER, value)
      value.unwrapped.asInstanceOf[jl.Number].intValue
    }
  }

  implicit object longHoconType extends HoconType[Long] {
    def get(value: ConfigValue) = {
      requireType(NUMBER, value)
      value.unwrapped.asInstanceOf[jl.Number].longValue
    }
  }

  implicit object configHoconType extends HoconType[Config] {
    def get(value: ConfigValue) = {
      requireType(OBJECT, value)
      value.asInstanceOf[ConfigObject].toConfig
    }
  }

  implicit object configValueHoconType extends HoconType[ConfigValue] {
    def get(value: ConfigValue) = value
  }

  implicit object configObjectHoconType extends HoconType[ConfigObject] {
    def get(value: ConfigValue) = {
      requireType(OBJECT, value)
      value.asInstanceOf[ConfigObject]
    }
  }

  implicit object configListHoconType extends HoconType[ConfigList] {
    def get(value: ConfigValue) = {
      requireType(LIST, value)
      value.asInstanceOf[ConfigList]
    }
  }

  implicit def listHoconType[T: HoconType] = new HoconType[ju.List[T]] {
    def get(value: ConfigValue) = {
      requireType(LIST, value)
      val elementHoconType = implicitly[HoconType[T]]
      value.asInstanceOf[ConfigList].asScala.map(elementHoconType.get).asJava
    }
  }

  implicit def mapHoconType[T: HoconType] = new HoconType[ju.Map[String, T]] {
    def get(value: ConfigValue) = {
      requireType(OBJECT, value)
      val elementHoconType = implicitly[HoconType[T]]
      value.asInstanceOf[ConfigObject].asScala.map {
        case (k, v) => (k, elementHoconType.get(v))
      }.asJava
    }
  }

  implicit def optionHoconType[T: HoconType] = new HoconType[Option[T]] {
    def get(value: ConfigValue): Option[T] =
      if (value == null || value.valueType == NULL) None
      else Some(implicitly[HoconType[T]].get(value))
  }

}
