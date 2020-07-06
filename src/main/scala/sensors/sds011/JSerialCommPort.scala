package sensors.sds011
import com.fazecast.jSerialComm.SerialPort
class JSerialCommPort(val portName: String) extends AerasSerialPort {

  val port = SerialPort.getCommPort(portName)
  /**
   * Blocks and reads quantity bytes from the port.
   * May block forever if data isn't available, or throw
   * and exception if it can't be done
   *
   * @param quantity the number of bytes to read
   * @return a byte array containing quantity bytes read from the port
   */
  override def readBytes(quantity: Int): Array[Byte] = {
    // open our port
    if(!port.isOpen) {
      port.openPort()
    }
    val buf = new Array[Byte](quantity)
    port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0)
    port.readBytes(buf, quantity)
    // be polite and close it
    port.closePort()
    buf
  }
}
