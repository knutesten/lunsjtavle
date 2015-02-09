package no.mesan.lunsjtavle.model

import java.sql.Timestamp

import spray.json.{DeserializationException, JsNumber, JsValue, JsonFormat}

/**
 * @author Knut Esten Melandsø Nekså
 */
trait TimestampFormat {

  implicit object TimestampFormat extends JsonFormat[Timestamp] {
    def write(obj: Timestamp) = JsNumber(obj.getTime)

    def read(json: JsValue) = json match {
      case JsNumber(time) => new Timestamp(time.toLong)

      case _ => throw new DeserializationException("Date expected")
    }
  }

}
