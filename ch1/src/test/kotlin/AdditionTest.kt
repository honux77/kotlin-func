import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.random.Random

class AdditionTest {

    private fun randomNatural() = Random.nextInt(from = 1, until = 100_000_000)

    @Test
    fun `add two numbers`() {
        expectThat(5 + 6).isEqualTo(11)
        expectThat(7 + 42).isEqualTo(49)
        expectThat(9999 + 1).isEqualTo(10000)
    }

    @Test
    fun `Zero identity`() {
        val x = randomNatural()
        expectThat(x + 0).isEqualTo(x)
    }

    @Test
    fun `commutative property`() {
        repeat(100) {
            val x = randomNatural()
            val y = randomNatural()
            expectThat(x + y).isEqualTo(y + x)
        }
    }

    @Test
    fun `associative property`() {
        repeat(100) {
            val x = randomNatural()
            val y = randomNatural()
            val z = randomNatural()
            expectThat((x + y) + z).isEqualTo(x + (y + z))
        }
    }

    @Test
    fun `연1-3 1씩 더해가는 테스트`() {
        repeat(100) {
            val a = randomNatural()
            val b = randomNatural()
            val min = minOf(a, b)
            val max = maxOf(a, b)
            var count = min
            while (count < max) {
                count++
            }
            expectThat(count).isEqualTo(max)
        }
    }
}