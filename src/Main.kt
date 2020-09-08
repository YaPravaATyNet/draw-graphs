fun main() {
   // printAll()
    val graph = VerticalDrawableGraph(true)
    val a = graph.addNode("a")
    val b = graph.addNode("b")
    val c = graph.addNode("c")
    val d = graph.addNode("d")
    var e = graph.addNode("e")
    graph.addEdge(a, b)
    graph.addEdge(a, c)
    graph.addEdge(a, d)
    graph.addEdge(b, d)
    graph.addEdge(c, e)
    //graph.addEdge(a, a)
    println(graph)
    println(graph.draw())
}
 
fun printAll() {
    println("\u2500\u2502\u250c\u2510\u2514\u2518\u251c\u2524\u252c\u2534\u253c\u2190\u2191\u2192\u2193")
    println()

    println("   [aagadj]←\u2524")
    println("    │     └─│─┐")
    println("    \u2193       │ │")
    println("   [bddjdj]←┴─┘")
    println("    │")
    println("└──┐\u2193")
    println("┌─→[c]←┬┴─┘")
    println("│   │└─┴┐")
    println("└──┐↓   │")
    println("   [d]  │")
    println("    │ ")
    println("│   ↓   │")
    println("└──[a]←─┘")
    println("   ↑││")
    println("  ─┘↓└─   ")

    println("\u27e6a\u27e7\u2962\u2557")
    println("[a]\u25c0\u2501\u2513")
    println("[a]\u2770\u2501\u2513")
}







