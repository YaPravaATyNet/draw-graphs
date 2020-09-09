import java.nio.file.Paths
import java.util.*
import kotlin.test.assertEquals

abstract class BaseTest {
    private fun testDataPathRoot() = Paths.get("testData").toAbsolutePath().toString()

    fun doTest(graph: DrawableGraph, name: String, vararg dir: String) {
        val file = Paths.get(testDataPathRoot(), *dir, "$name.txt").toFile()
        val scanner = Scanner(file)
        val size = scanner.nextInt()
        for (i in 0 until size) {
            graph.addNode("$i")
        }
        while (scanner.hasNext()) {
            val start = scanner.nextInt()
            val end = scanner.nextInt()
            graph.addEdge(start, end)
        }
        val expected = Paths.get(testDataPathRoot(), "vertical", "directed", "${name}Graph.txt").toFile().readText()
        assertEquals(expected, graph.draw())
    }
}