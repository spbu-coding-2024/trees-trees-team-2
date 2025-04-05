package iterators

import RBNode


class RBTreeBFSIterator<K : Comparable<K>, V>(root: RBNode<K, V>?) : Iterator<RBNode<K, V>> {
    private val queue = ArrayDeque<RBNode<K, V>>()

    init {
        if (root != null) {
            queue.addLast(root)
        }
    }

    override fun hasNext(): Boolean = queue.isNotEmpty()

    override fun next(): RBNode<K, V> {
        if (!hasNext()) throw NoSuchElementException()

        val node = queue.removeFirst()

        node.left?.let { queue.addLast(it) }
        node.right?.let { queue.addLast(it) }

        return node
    }
}