package dto

/**
 * Класс [Node] представляет узел дерева с параметризованными типами: [K] (ключ), [V] (значение) и [T] (тип дочерних узлов).
 * Этот класс используется для построения различных структур данных, таких как бинарные деревья.
 *
 * @param K Тип ключа, который должен быть сравнимым (реализует интерфейс [Comparable]).
 * @param V Тип значения, которое хранится в узле.
 * @param T Тип дочерних узлов (должен быть того же типа, что и текущий узел).
 * @param key Ключ для этого узла.
 * @param value Значение для этого узла.
 * @param left Левый дочерний узел.
 * @param right Правый дочерний узел.
 *
 * @constructor Создает узел с заданным ключом, значением и дочерними узлами (если они есть).
 */
open class Node<K : Comparable<K>, V, T>(
    var key: K,                        // Ключ узла, тип K должен быть Comparable для поддержки сравнения
    var value: V,                       // Значение узла
    var left: T? = null,                // Левый дочерний узел (может быть null)
    var right: T? = null                // Правый дочерний узел (может быть null)
)