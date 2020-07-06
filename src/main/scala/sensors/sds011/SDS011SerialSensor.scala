package sensors.sds011

import sensors.AirQualitySensor


class SDS011SerialSensor(val serialPort: AerasSerialPort) extends AirQualitySensor{

  // the sds011 sends data in 10 byte increments
  val sampleLengthBytes = 10
  var lastRead: Array[Byte] = new Array[Byte](0)
  val commandGenerator = new SDS011CommandGenerator


  def getReading(lowerByteIndex: Int, higherByteIndex: Int): Double = {
    val firstBits: Int = lastRead(lowerByteIndex) & 0xFF // byte to int conversion
    val secondBits: Int = (lastRead(higherByteIndex) & 0xFF) << 8 //shifted one byte over and byte to int conversion
    val reading = firstBits + secondBits;
    reading / 10.0
  }


  override def pm25(): Double = {
    /* the pm25 reading is in bytes 2 and 3 of the reading 0 indexed
      in little endian order
     */
    getReading(2, 3)
  }


  override def pm100(): Double = {
    /** The pm 10 micrometers count is in bytes 4 and 5 of the reading
     * 0 indexed, in little endian order*/
    getReading(4, 5)
  }

  override def read(): Unit = {
    lastRead = serialPort.readBytes(sampleLengthBytes)
  }
}
