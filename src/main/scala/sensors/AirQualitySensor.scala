package sensors

trait AirQualitySensor {
  /**
   * Returns the 2.5 micrometers particulate count
   * of the last reading taken
   * @return
   */
  def pm25(): Double

  /**
   * Return the 10 micrometers particulate count
   * of the last reading taken
   * @return
   */
  def pm100(): Double

  /**
   * Instructs the sensor to take a reading. This is an <i>explicit</i>_
   * call because sensors are consumable, and we don't want to casually ask
   * for pm counts and force reads implicitly.
   */
  def read(): Unit
}
