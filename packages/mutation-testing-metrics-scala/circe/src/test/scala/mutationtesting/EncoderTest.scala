package mutationtesting

import verify._
import io.circe.syntax._

object EncoderTest extends BasicTestSuite {
  test("encoded JSON should be valid") {
    import mutationtesting.MutationReportEncoder._
    val sut = MutationTestReport(
      thresholds = Thresholds(high = 80, low = 10),
      files = Map(
        "src/stryker4s/Stryker4s.scala" -> MutationTestResult(
          source = "case class Stryker4s(foo: String)",
          mutants = Seq(
            MutantResult(
              "1",
              "BinaryOperator",
              "-",
              Location(
                Position(1, 2),
                Position(2, 3)
              ),
              status = MutantStatus.Killed
            )
          )
        )
      )
    )

    val result = sut.asJson.noSpaces

    val expectedJson =
      """{"$schema":"https://raw.githubusercontent.com/stryker-mutator/mutation-testing-elements/master/packages/mutation-testing-report-schema/src/mutation-testing-report-schema.json","schemaVersion":"1","thresholds":{"high":80,"low":10},"files":{"src/stryker4s/Stryker4s.scala":{"source":"case class Stryker4s(foo: String)","mutants":[{"id":"1","mutatorName":"BinaryOperator","replacement":"-","location":{"start":{"line":1,"column":2},"end":{"line":2,"column":3}},"status":"Killed"}],"language":"scala"}}}"""
    assert(result == expectedJson)
  }
}
