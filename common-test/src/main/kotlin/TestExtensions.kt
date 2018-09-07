import kotlin.test.assertEquals

infix fun <T> T.shouldEqual(other: T) {
    assertEquals(this, other)
}