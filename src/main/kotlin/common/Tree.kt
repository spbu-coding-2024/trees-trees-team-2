package common

import dto.Node

abstract class Tree<K : Comparable<K>, V, T : Node<K, V, T>> {
    abstract fun insert(key: K, value: V)

    abstract fun search(key: K): V?

    protected fun searchValue(root: T?, key: K): V? {
        var node = root;
        while (node != null) {
            node = when {
                key < node.key -> node.left
                key > node.key -> node.right
                else -> return node.value
            }
        }
        return null
    }

    abstract fun delete(key: K): Boolean

    abstract fun treeBFSIterator(): Iterator<T>

    abstract fun treeDFSIterator(): Iterator<T>

}