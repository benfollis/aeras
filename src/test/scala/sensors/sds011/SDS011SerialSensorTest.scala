package sensors.sds011
import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite

class SDS011SerialSensorTest extends AnyFunSuite with MockFactory {

  test("Read asks for 10 bytes of data from the port") {

    val portMock = mock[AerasSerialPort]
    (portMock.readBytes _).expects(10)
    val sensor = new SDS011SerialSensor(portMock)
    sensor.read()
  }

  test("PM25 correctly reports 25.0") {
    val portMock = mock[AerasSerialPort]
    (portMock.readBytes _).expects(10).returns(Array(0,0,250.toByte,0))
    val sensor = new SDS011SerialSensor(portMock)
    sensor.read()
    assert(sensor.pm25() === 25.0)
  }

  test("PM10 correctly reports 40.0") {
    val portMock = mock[AerasSerialPort]
    //40 is 400 which is 1 bit in the high buts field = 256, plus 144 in the lower bits
    (portMock.readBytes _).expects(10).returns(Array(0, 0, 0, 0, 144.toByte, 1))
    val sensor = new SDS011SerialSensor(portMock)
    sensor.read()
    assert(sensor.pm100() === 40.0)
  }
}
