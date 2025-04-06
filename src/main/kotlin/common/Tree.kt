package common

import dto.Node

/**
 * Абстрактный класс для представления дерева.
 *
 * @param K Тип ключа, который должен быть сравнимым (должен реализовывать интерфейс Comparable).
 * @param V Тип значения, которое хранится в узлах дерева.
 * @param T Тип узла дерева, который должен быть наследником [Node].
 */
abstract class Tree<K : Comparable<K>, V, T : Node<K, V, T>> {

    /**
     * Вставляет новый элемент с указанным ключом и значением в дерево.
     *
     * @param key Ключ для вставляемого элемента.
     * @param value Значение для вставляемого элемента.
     */
    abstract fun insert(key: K, value: V)

    /**
     * Ищет значение в дереве по ключу.
     *
     * @param key Ключ, по которому нужно найти значение.
     * @return Значение, соответствующее ключу, или null, если элемент не найден.
     */
    abstract fun search(key: K): V?

    /**
     * Защищённая функция для поиска значения в поддереве с корнем [root].
     * Функция возвращает значение, если ключ найден, или null в противном случае.
     *
     * @param root Корень поддерева, в котором нужно выполнить поиск.
     * @param key Ключ, по которому нужно найти значение.
     * @return Значение, соответствующее ключу, или null, если элемент не найден.
     */
    protected fun searchValue(root: T?, key: K): V? {
        var node = root
        while (node != null) {
            node = when {
                key < node.key -> node.left
                key > node.key -> node.right
                else -> return node.value
            }
        }
        return null
    }

    /**
     * Удаляет элемент по ключу из дерева.
     *
     * @param key Ключ элемента, который необходимо удалить.
     * @return true, если элемент был удален, и false, если элемент не найден.
     */
    abstract fun delete(key: K): Boolean

    /**
     * Создает итератор для обхода дерева в ширину (BFS).
     *
     * @return Итератор для обхода дерева в ширину.
     */
    abstract fun treeBFSIterator(): Iterator<T>

    /**
     * Создает итератор для обхода дерева в глубину (DFS).
     *
     * @return Итератор для обхода дерева в глубину.
     */
    abstract fun treeDFSIterator(): Iterator<T>
}