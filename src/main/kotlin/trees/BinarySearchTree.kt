package trees

import common.Tree

open class BinarySearchTree<K : Comparable<K>, V> : Tree<K, V> {

    protected class Node<K : Comparable<K>, V>(
        var key: K,
        var value: V,
        var left: Node<K, V>? = null,
        var right: Node<K, V>? = null
    )

    protected var root: Node<K, V>? = null

    override fun insert(key: K, value: V) {
        if (root == null) {
            root = Node(key, value)
        } else {
            var node = root
            while (node != null) {
                if (key < node.key) {
                    if (node.left == null) {
                        node.left = Node(key, value)
                        break
                    }
                    node = node.left
                } else if (key > node.key) {
                    if (node.right == null) {
                        node.right = Node(key, value)
                        break
                    }
                    node = node.right
                } else if (key == node.key) {
                    node.value = value
                    break
                }
            }
        }
    }


    override fun delete(key: K) {
        var node = root
        var parent: Node<K, V>? = null

        // Поиск узла и его родителя
        while (node != null && node.key != key) {
            parent = node
            node = if (key < node.key) node.left else node.right
        }

        if (node == null) return // Узел не найден

        // Если у узла нет правого поддерева
        if (node.right == null) {
            if (parent == null) {
                root = node.left // Удаляемый узел - корень
            } else {
                if (node == parent.left) {
                    parent.left = node.left
                } else {
                    parent.right = node.left
                }
            }
        } else {
            // Находим наименьший узел в правом поддереве (замену)
            var successor: Node<K, V>? = node.right
            var successorParent: Node<K, V>? = node

            while (successor?.left != null) { // Безопасный вызов ?.left
                successorParent = successor
                successor = successor.left
            }

            // Заменяем удаляемый узел найденным узлом
            if (successorParent != node) {
                successorParent?.left = successor?.right // Безопасный вызов ?.
                successor?.right = node.right
            }

            successor?.left = node.left

            if (parent == null) {
                root = successor // Если удаляем корень, меняем его на successor
            } else if (node == parent.left) {
                parent.left = successor
            } else {
                parent.right = successor
            }
        }
    }


    override fun search(key: K): V? {
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

    fun printBreadthFirst() {
        if (root == null) return
        val queue: MutableList<Node<K, V>> = mutableListOf(root!!)
        while (queue.isNotEmpty()) {
            val node = queue.removeAt(0)
            println("Ключ: ${node.key}, Значение: ${node.value}")
            node.left?.let { queue.add(it) }
            node.right?.let { queue.add(it) }
        }
    }

    fun printDepthFirst() {
        if (root == null) return
        val stack = ArrayDeque<Node<K, V>>()
        stack.add(root!!)
        while (stack.isNotEmpty()) {
            val node = stack.removeLast()
            println("Ключ: ${node.key}, Значение: ${node.value}")
            node.right?.let { stack.add(it) }
            node.left?.let { stack.add(it) }
        }
    }

}