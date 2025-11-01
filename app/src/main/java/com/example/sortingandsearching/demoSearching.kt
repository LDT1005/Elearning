package com.example.sortingandsearching

import java.util.ArrayDeque

fun parseInputGraph(input: String): Map<String, Set<String>> {
    val mutableGraph = mutableMapOf<String, Set<String>>()
    try {
        input.split('\n') // Tach theo tung dong
            .filter { it.contains(":") } // Chi lay cac dong co dinh dang
            .forEach { line ->
                val parts = line.split(':')
                if (parts.size != 2) throw IllegalArgumentException("Dong loi: $line")

                val key = parts[0].trim()
                val values = parts[1].split(',')
                    .map { it.trim() }
                    .filter { it.isNotEmpty() } // Bo qua neu co dau phay thua
                    .toSet()
                mutableGraph[key] = values
            }

        // Tu dong them cac nut khong co hang xom (neu duoc tham chieu)
        val allNodes = mutableGraph.keys.toMutableSet()
        mutableGraph.values.forEach { set -> allNodes.addAll(set) }
        allNodes.forEach { node ->
            if (!mutableGraph.containsKey(node)) {
                mutableGraph[node] = emptySet()
            }
        }
        return mutableGraph
    } catch (e: Exception) {
        throw IllegalArgumentException("Dinh dang do thi khong hop le.")
    }
}


// --- Cac ham tim kiem ---

// Project 30: Depth-First Search (DFS)
fun dfs(graph: Map<String, Set<String>>, startNode: String): String {
    if (!graph.containsKey(startNode)) return "Loi: Khong tim thay nut bat dau '$startNode' trong do thi."

    val stack = ArrayDeque<String>()
    val visited = mutableSetOf<String>()
    val resultBuilder = StringBuilder()

    stack.push(startNode)

    while (stack.isNotEmpty()) {
        val currentNode = stack.pop()
        if (currentNode !in visited) {
            resultBuilder.append("$currentNode ")
            visited.add(currentNode)

            // Dao nguoc danh sach hang xom truoc khi push
            // De duyet theo thu tu tu nhien (vd: 1 truoc 2, 2 truoc 3)
            graph[currentNode]?.sorted()?.reversed()?.forEach { neighbor ->
                if (neighbor !in visited) {
                    stack.push(neighbor)
                }
            }
        }
    }
    return resultBuilder.toString().trim()
}

// Project 31: Breadth-First Search (BFS)
fun bfs(graph: Map<String, Set<String>>, startNode: String): String {
    if (!graph.containsKey(startNode)) return "Loi: Khong tim thay nut bat dau '$startNode' trong do thi."

    val queue = ArrayDeque<String>()
    val visited = mutableSetOf<String>()
    val resultBuilder = StringBuilder()

    queue.offer(startNode)
    visited.add(startNode)

    while (queue.isNotEmpty()) {
        val currentNode = queue.poll()
        resultBuilder.append("$currentNode ")

        // Them hang xom vao Queue (theo thu tu tu nhien)
        graph[currentNode]?.sorted()?.forEach { neighbor ->
            if (neighbor !in visited) {
                visited.add(neighbor)
                queue.offer(neighbor)
            }
        }
    }
    return resultBuilder.toString().trim()
}