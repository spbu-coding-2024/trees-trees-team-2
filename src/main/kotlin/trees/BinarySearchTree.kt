package trees

import common.Tree
import dto.BNode
import iterators.TreeBFSIterator
import iterators.TreeDFSIterator

/**
 * Класс [BinarySearchTree] представляет собой реализацию бинарного дерева поиска.
 *
 * Бинарное дерево поиска — это структура данных, в которой для каждого узла выполняется условие:
 * все ключи в левом поддереве узла меньше, а в правом — больше, чем ключ узла.
 *
 * @param K Тип ключа, который должен быть сравнимым (реализует интерфейс [Comparable]).
 * @param V Тип значения, которое хранится в узле дерева.
 * @constructor Создает пустое бинарное дерево поиска.
 */
class BinarySearchTree<K : Comparable<K>, V> : Tree<K, V, BNode<K, V>>() {

    // Корень дерева, изначально равен null
    private var root: BNode<K, V>? = null

    /**
     * Вставляет новый элемент с заданным ключом и значением в дерево.
     * Если элемент с таким ключом уже существует, обновляется его значение.
     *
     * @param key Ключ элемента, который необходимо вставить.
     * @param value Значение элемента, которое необходимо вставить.
     */
    override fun insert(key: K, value: V) {
        if (root == null) {
            // Если дерево пусто, создаем корень
            root = BNode(key, value)
        } else {
            var node = root
            // Ищем место для нового узла
            while (node != null) {
                if (key < node.key) {
                    // Если ключ меньше текущего, идем в левое поддерево
                    if (node.left == null) {
                        node.left = BNode(key, value)  // Вставляем новый узел
                        break
                    }
                    node = node.left
                } else if (key > node.key) {
                    // Если ключ больше текущего, идем в правое поддерево
                    if (node.right == null) {
                        node.right = BNode(key, value)  // Вставляем новый узел
                        break
                    }
                    node = node.right
                } else {
                    // Если ключ уже существует, обновляем значение
                    node.value = value
                    break
                }
            }
        }
    }

    /**
     * Удаляет элемент с заданным ключом из дерева.
     * Если элемент с таким ключом не найден, возвращает false.
     *
     * @param key Ключ элемента, который нужно удалить.
     * @return true, если элемент был успешно удален, иначе false.
     */
    override fun delete(key: K): Boolean {
        var node = root
        var parent: BNode<K, V>? = null

        // Поиск узла и его родителя
        while (node != null && node.key != key) {
            parent = node
            node = if (key < node.key) node.left else node.right
        }

        if (node == null) return false // Узел не найден

        // Если у узла нет правого поддерева
        if (node.right == null) {
            // Удаляем узел: его левое поддерево поднимается на место удаляемого узла
            if (parent == null) {
                root = node.left
            } else if (node == parent.left) {
                parent.left = node.left
            } else {
                parent.right = node.left
            }
        } else {
            // Находим наименьший узел в правом поддереве
            var successor = node.right
            var successorParent = node

            while (successor?.left != null) {
                successorParent = successor
                successor = successor.left
            }

            // Заменяем удаляемый узел найденным узлом (successor)
            if (successor != node.right) {
                successorParent?.left = successor?.right
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

        return true
    }

    /**
     * Ищет значение по ключу в дереве.
     *
     * @param key Ключ элемента, значение которого нужно найти.
     * @return Значение, соответствующее ключу, или null, если элемент не найден.
     */
    override fun search(key: K): V? {
        return searchValue(root, key)
    }

    /**
     * Создает итератор для обхода дерева в ширину (BFS).
     *
     * @return Итератор для обхода дерева в ширину.
     */
    override fun treeBFSIterator(): Iterator<BNode<K, V>> {
        return TreeBFSIterator(root)
    }

    /**
     * Создает итератор для обхода дерева в глубину (DFS).
     *
     * @return Итератор для обхода дерева в глубину.
     */
    override fun treeDFSIterator(): Iterator<BNode<K, V>> {
        return TreeDFSIterator(root)
    }

}