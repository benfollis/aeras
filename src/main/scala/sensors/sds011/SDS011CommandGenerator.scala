package sensors.sds011

class SDS011CommandGenerator {

  val CommandWorkState = 6
  val WorkStateSleeping = 0
  val WorkStateMeasuring = 1

  val Getting = 0
  val Setting = 1

  val EmptyData: Array[Int] = Array(0,0,0,0,0,0,0,0,0,0,0)

  def generateCommand(cmd: Int, mode: Int = 0, data: Array[Int] = EmptyData): Array[Byte] = {
    val request = new Array[Int](19)
    //header
    request(0) = 0xAA
    //marker
    request(1) = 0XB4
    //command
    request(2) = cmd
    // mode
    request(3) = mode
    // checkData will be used to make the checksum
    var checkData = cmd + mode
    //data
    for(i <- data.indices) {
      request(i+4) = data(i)
      checkData += data(i) // for checksum purposes
    }
    //device ID always 0xFF, 0xFF
    request(15) = 0xFF
    request(16) = 0XFF
    checkData += 0xFF
    checkData += 0xFF // again, checksum stuff
    val checkSum = checkData % 256
    //checksum
    request(17) = checkSum
    //tail
    request(18) = 0xAB
    request.map(field => field.toByte)
  }


  def wakeUp(): Array[Byte] = {
    generateCommand(CommandWorkState, WorkStateMeasuring)
  }

  def sleep(): Array[Byte] = {
    generateCommand(CommandWorkState, WorkStateSleeping)
  }
}
