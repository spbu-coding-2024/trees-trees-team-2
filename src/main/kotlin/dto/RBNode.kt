import dto.Node
enum class Color{BLACK, RED}
/**
 * Узел красно-черного дерева.
 *
 * @param K Тип ключа, должен поддерживать сравнение [Comparable]
 * @param V Тип значения
 * @property parent Родительский узел (null для корня)
 * @property color Цвет узла (красный/черный)
 *
 * @see Node Базовый класс узла
 */
class RBNode<K: Comparable<K>, V>(key: K, value: V) : Node<K, V, RBNode<K,V>>(key, value) {
    /**
    * Ссылка на родительский узел в дереве.
    * Для корневого узла значение равно null.
    */
    var parent: RBNode<K, V>? = null
    /**
     * Цвет узла в красно-черном дереве.
     * По умолчанию RED (новые узлы вставляются красными).
     */
    var color:Color = Color.RED
    /**
     * Проверяет, является ли узел листом (не имеет дочерних узлов).
     * @return true если оба дочерних узла отсутствуют
     */
    fun isLeaf(): Boolean = (this.left == null) && (this.right == null)
}

