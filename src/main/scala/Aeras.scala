import sensors.sds011.SDS011SerialSensor

object Aeras extends App {
  println("Hello Aeras from SBT")
  val sensor = new SDS011SerialSensor("/dev/ttyUSB0")
  sensor.read()
  val pm25 = sensor.pm25()
  val pm100 = sensor.pm100()
  println(pm25.toString)
  println(pm100.toString)
}
