package iterators

import dto.Node
import kotlin.collections.ArrayDeque

class TreeDFSIterator<K : Comparable<K>, V, T : Node<K, V, T>>(root: T?) : Iterator<T> {
    private val stack = ArrayDeque<T>()

    init {
        pushLeft(root)
    }

    private fun pushLeft(node: T?) {
        var current = node
        while (current != null) {
            stack.addLast(current)
            current = current.left
        }
    }

    override fun hasNext(): Boolean = stack.isNotEmpty()

    override fun next(): T {
        if (!hasNext()) throw NoSuchElementException()

        val node = stack.removeLast()
        pushLeft(node.right)
        return node
    }
}