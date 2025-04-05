package iterators

import dto.BNode
import dto.Node

class TreeBFSIterator<K : Comparable<K>, V>(root: BNode<K, V>?) : Iterator<BNode<K, V>> {
    private val queue = ArrayDeque<BNode<K, V>>()

    init {
        if (root != null) {
            queue.addLast(root)
        }
    }

    override fun hasNext(): Boolean = queue.isNotEmpty()

    override fun next(): BNode<K, V> {
        if (!hasNext()) throw NoSuchElementException()

        val node = queue.removeFirst()

        node.left?.let { queue.addLast(it) }
        node.right?.let { queue.addLast(it) }

        return node
    }
}