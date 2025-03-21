package iterators

import dto.Node
import kotlin.collections.ArrayDeque

class TreeDFSIterator<K : Comparable<K>, V>(root: Node<K, V>?) : Iterator<Node<K, V>> {
    private val stack = ArrayDeque<Node<K, V>>()

    init {
        pushLeft(root)
    }

    private fun pushLeft(node: Node<K, V>?) {
        var current = node
        while (current != null) {
            stack.addLast(current)
            current = current.left
        }
    }

    override fun hasNext(): Boolean = stack.isNotEmpty()

    override fun next(): Node<K, V> {
        if (!hasNext()) throw NoSuchElementException()

        val node = stack.removeLast()
        pushLeft(node.right)
        return node
    }
}