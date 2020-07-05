package sensors.sds011

import sensors.AirQualitySensor
import com.fazecast.jSerialComm.SerialPort

class SDS011SerialSensor(val serialPort: String) extends AirQualitySensor{

  var lastRead: Array[Byte] = new Array[Byte](0)
  val commandGenerator = new SDS011CommandGenerator
  // the sds011 sends data in 10 byte increments
  val sampleLengthBytes = 10


  def getReading(lowerByteIndex: Int, higherByteIndex: Int): Double = {
    val firstBits: Int = lastRead(lowerByteIndex)
    val secondBits: Int = lastRead(higherByteIndex) << 8 //shifted one byte over
    val reading = firstBits + secondBits;
    reading / 10.0
  }

  /**
   * Returns the 2.5 micrometers particulate count
   *
   * @return
   */
  override def pm25(): Double = {
    /* the pm25 reading is in bytes 2 and 3 of the reading 0 indexed
      in little endian order
     */
    getReading(2, 3)
  }

  /**
   * Return the 10 micrometers particulate count
   *
   * @return
   */
  override def pm100(): Double = {
    /** The pm 10 micrometers count is in bytes 4 and 5 of the reading
     * 0 indexed, in little endian order*/
    getReading(4, 5)
  }

  override def read(): Unit = {
    val port = SerialPort.getCommPort(serialPort)
    port.openPort()
    port.setBaudRate(9600)
    port.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0)
    val out = port.getOutputStream
    lastRead = new Array[Byte](sampleLengthBytes)
    port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0)
    val readBytes = port.readBytes(lastRead, sampleLengthBytes);
    // put us back to sleep

  }
}
