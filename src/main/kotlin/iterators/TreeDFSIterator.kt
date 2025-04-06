package iterators

import dto.Node
import kotlin.collections.ArrayDeque

/**
 * Итератор для обхода дерева в глубину (DFS).
 *
 * Этот итератор выполняет обход дерева с использованием стека. Он проходит все узлы дерева, начиная с корня,
 * исследуя как можно дальше вдоль ветвей перед возвратом.
 *
 * @param K Тип ключа, который должен быть сравнимым (реализует интерфейс [Comparable]).
 * @param V Тип значения, которое хранится в узле дерева.
 * @param T Тип узла дерева, который должен быть наследником [Node].
 * @param root Корень дерева, с которого начинается обход.
 *
 * @constructor Создает итератор для обхода дерева в глубину с указанным корнем.
 */
class TreeDFSIterator<K : Comparable<K>, V, T : Node<K, V, T>>(root: T?) : Iterator<T> {
    // Стек для хранения узлов дерева, которые нужно обработать
    private val stack = ArrayDeque<T>()

    // Инициализируем стек, добавляя в него все левые дочерние узлы от корня
    init {
        pushLeft(root)
    }

    /**
     * Добавляет в стек все левые дочерние узлы начиная с указанного узла.
     *
     * @param node Узел, с которого начинается добавление в стек.
     */
    private fun pushLeft(node: T?) {
        var current = node
        // Пока текущий узел не равен null, добавляем его в стек и переходим к левому дочернему узлу
        while (current != null) {
            stack.addLast(current)
            current = current.left
        }
    }

    /**
     * Проверяет, есть ли следующие элементы для обхода.
     *
     * @return true, если стек не пуст, и false в противном случае.
     */
    override fun hasNext(): Boolean = stack.isNotEmpty()

    /**
     * Возвращает следующий узел дерева в обходе в глубину.
     * Если элементы для обхода закончились, выбрасывает исключение [NoSuchElementException].
     *
     * @return Следующий узел дерева.
     * @throws NoSuchElementException Если обход завершен и нет доступных элементов.
     */
    override fun next(): T {
        if (!hasNext()) throw NoSuchElementException()

        // Удаляем и возвращаем последний узел из стека
        val node = stack.removeLast()

        // Добавляем левые дочерние узлы правого поддерева в стек
        pushLeft(node.right)

        return node
    }
}