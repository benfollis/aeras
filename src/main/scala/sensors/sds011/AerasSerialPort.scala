package sensors.sds011

/**
 * Trait to describe the basic calls we need from a serial port
 */
trait AerasSerialPort {

  /**
   * Blocks and reads quantity bytes from the port.
   * May block forever if data isn't available, or throw
   * and exception if it can't be done
   * @param quantity the number of bytes to read
   * @return a byte array containing quantity bytes read from the port
   */
  def readBytes(quantity: Int): Array[Byte]


}
