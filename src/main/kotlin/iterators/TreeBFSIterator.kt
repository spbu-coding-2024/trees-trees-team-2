package iterators

import dto.Node

class TreeBFSIterator<K : Comparable<K>, V>(root: Node<K, V>?) : Iterator<Node<K, V>> {
    private val queue = ArrayDeque<Node<K, V>>()

    init {
        if (root != null) {
            queue.addLast(root)
        }
    }

    override fun hasNext(): Boolean = queue.isNotEmpty()

    override fun next(): Node<K, V> {
        if (!hasNext()) throw NoSuchElementException()

        val node = queue.removeFirst()

        node.left?.let { queue.addLast(it) }
        node.right?.let { queue.addLast(it) }

        return node
    }
}