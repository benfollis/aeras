import sensors.sds011.{JSerialCommPort, SDS011SerialSensor}

object Aeras extends App {
  println("Hello Aeras from SBT")
  val port = new JSerialCommPort("/dev/ttyUSB0")
  val sensor = new SDS011SerialSensor(port)
  sensor.read()
  val pm25 = sensor.pm25()
  val pm100 = sensor.pm100()
  println(pm25.toString)
  println(pm100.toString)
}
