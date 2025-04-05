package trees

import common.Tree
import dto.BNode
import iterators.TreeBFSIterator
import iterators.TreeDFSIterator

class BinarySearchTree<K : Comparable<K>, V> : Tree<K, V, BNode<K, V>>() {

    private var root: BNode<K, V>? = null

    override fun insert(key: K, value: V) {
        if (root == null) {
            root = BNode(key, value)
        } else {
            var node = root
            while (node != null) {
                if (key < node.key) {
                    if (node.left == null) {
                        node.left = BNode(key, value)
                        break
                    }
                    node = node.left
                } else if (key > node.key) {
                    if (node.right == null) {
                        node.right = BNode(key, value)
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


    override fun delete(key: K): Boolean {
        var node = root
        var parent: BNode<K, V>? = null

        // Поиск узла и его родителя
        while (node != null && node.key != key) {
            parent = node
            node = if (key < node.key) node.left else node.right
        }

        if (node == null) return false// Узел не найден

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
            var successor: BNode<K, V>? = node.right
            var successorParent: BNode<K, V>? = node

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
        return false
    }


    override fun search(key: K): V? {
        return searchValue(root, key)
    }

    override fun treeBFSIterator(): Iterator<BNode<K, V>> {
        return TreeBFSIterator(root)
    }

    override fun treeDFSIterator(): Iterator<BNode<K, V>> {
        return TreeDFSIterator(root)
    }

}