import org.junit.jupiter.api.Test

class VerticalDrawableGraphTest: BaseTest() {
    @Test
    fun testEdgeToNextNode() = doTest("EdgeToNextNode")
    @Test
    fun testDownEdgeSimple() = doTest("DownEdgeSimple")

    private fun doTest(name: String) {
        val graph = VerticalDrawableGraph(true)
        super.doTest(graph, name, "vertical", "directed")
    }
}