package iterators

import dto.Node

/**
 * Итератор для обхода дерева в ширину (BFS).
 *
 * Этот итератор выполняет обход дерева с использованием очереди. Он проходит все узлы дерева по уровням, начиная с корня.
 *
 * @param K Тип ключа, который должен быть сравнимым (реализует интерфейс [Comparable]).
 * @param V Тип значения, которое хранится в узле дерева.
 * @param T Тип узла дерева, который должен быть наследником [Node].
 * @param root Корень дерева, с которого начинается обход.
 *
 * @constructor Создает итератор для обхода дерева в ширину с указанным корнем.
 */
class TreeBFSIterator<K : Comparable<K>, V, T : Node<K, V, T>>(root: T?) : Iterator<T> {
    // Очередь для хранения узлов дерева, которые нужно обработать
    private val queue = ArrayDeque<T>()

    // Инициализируем очередь, добавляя в нее корень дерева (если он не null)
    init {
        if (root != null) {
            queue.addLast(root)
        }
    }

    /**
     * Проверяет, есть ли следующие элементы для обхода.
     *
     * @return true, если очередь не пуста, и false в противном случае.
     */
    override fun hasNext(): Boolean = queue.isNotEmpty()

    /**
     * Возвращает следующий узел дерева в обходе в ширину.
     * Если элементы для обхода закончились, выбрасывает исключение [NoSuchElementException].
     *
     * @return Следующий узел дерева.
     * @throws NoSuchElementException Если обход завершен и нет доступных элементов.
     */
    override fun next(): T {
        if (!hasNext()) throw NoSuchElementException()

        // Удаляем и возвращаем первый узел из очереди
        val node = queue.removeFirst()

        // Добавляем левого и правого дочерних узлов в очередь (если они существуют)
        node.left?.let { queue.addLast(it) }
        node.right?.let { queue.addLast(it) }

        return node
    }
}