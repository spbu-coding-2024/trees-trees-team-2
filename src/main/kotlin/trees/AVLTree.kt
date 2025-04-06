package trees

import common.Tree
import dto.AVLNode
import iterators.TreeBFSIterator
import iterators.TreeDFSIterator
import kotlin.math.max
import kotlin.collections.ArrayDeque

/**
 * Класс [AVLTree] представляет собой реализацию АВЛ-дерева.
 *
 * АВЛ-дерева — это бинарное дерево поиска, в котором разницы высот левого и правого поддерева каждый вершины меньше 2.
 *
 * @param K Тип ключа, который должен быть сравнимым (реализует интерфейс [Comparable]).
 * @param V Тип значения, которое хранится в узле дерева.
 * @constructor Создает пустое бинарное дерево поиска.
 */

class AVLTree<K : Comparable<K>, V> : Tree<K, V,AVLNode<K,V>>() {

    private var root: AVLNode<K, V>? = null

    /**
     * Выполняет левый поворот вершины
     *
     * @param node Вершина относительно которой выполняем поворот.
     */
    private fun leftRotate(node: AVLNode<K, V>?): AVLNode<K, V>?  {
        if (node != null) {
            val tmpNode = node.right
            node.right = tmpNode?.left
            tmpNode?.left = node
            fixHeight(node)
            fixHeight(tmpNode)
            return tmpNode
        }
        return null
    }

    /**
     * Выполняет правый поворот вершины
     *
     * @param node Вершина относительно которой выполняем поворот.
     */
    private fun rightRotate(node: AVLNode<K, V>?): AVLNode<K, V>? {
        if (node != null) {
            val tmpNode = node.left
            node.left = tmpNode?.right
            tmpNode?.right = node
            fixHeight(node)
            fixHeight(tmpNode)
            return tmpNode
        }
        return null
    }

    /**
     * Выполняет балансировку вершины
     * @param stack На вход функции подаётся стек с вершинами, у которых мог измениться фактор баланса
     * В порядке пути от самой дальней вершины, до дальней -1 и т.д до самого корня
     * В стеке вершины хранятся парами: сверху стека хранится узел, сразу же под ним идёт его родитель.
     * Соответственно из стека вершины убираются парами
     */
    private fun balance(stack: ArrayDeque<AVLNode<K, V>?>) {
        while (stack.isNotEmpty()) {
            /* Достаём вершину и его родителя */
            val unbalanceNode = stack.removeLast()
            val parent = stack.removeLast()
            /* Чиним высоту вершины */
            if (unbalanceNode != null) {
                fixHeight(unbalanceNode)
            }
            /* Высчитываем баланс фактор, баланс фактор считается по формуле: высота правого - высота левого потомка */
            val balanceF = getBalanceValue(unbalanceNode)

            /* Выполняем балансировку */
            if (balanceF > 1) {
                if (unbalanceNode != null) {
                    if (getBalanceValue(unbalanceNode.right) < 0) {
                        unbalanceNode.right = rightRotate(unbalanceNode.right)
                    }
                    /* Проверка, является ли вершина вокруг которой мы вращаем корнем */
                    if (parent != null) {
                        if (parent.key < unbalanceNode.key) {
                            parent.right = leftRotate(unbalanceNode)
                        } else {
                            parent.left = leftRotate(unbalanceNode)
                        }
                    } else {
                        root = leftRotate(unbalanceNode)
                    }
                }
            }

            if (balanceF < -1) {
                if (unbalanceNode != null) {
                    if (getBalanceValue(unbalanceNode.left) > 0) {
                        unbalanceNode.left = leftRotate(unbalanceNode.left)
                    }
                    /* Проверка, является ли вершина вокруг которой мы вращаем корнем */
                    if (parent != null) {
                        if (parent.key < unbalanceNode.key) {
                            parent.right = rightRotate(unbalanceNode)
                        } else {
                            parent.left = rightRotate(unbalanceNode)
                        }
                    } else {
                        root = rightRotate(unbalanceNode)
                    }
                }
            }
        }
    }

    /**
     * Вставляет новый элемент с заданным ключом и значением в дерево.
     * Если элемент с таким ключом уже существует, обновляется его значение.
     *
     * @param key Ключ элемента, который необходимо вставить.
     * @param value Значение элемента, которое необходимо вставить.
     */
    override fun insert(key: K, value: V) {
        if (root == null) {
            root = AVLNode(key, value)
        } else {
            var node = root
            /* Создаём стек и добавляем в него предок корня(null) и корень */
            val stack = ArrayDeque<AVLNode<K, V>?>()
            stack.addLast(null)
            stack.addLast(node)
            /* Идём к местоположению искомой вершины, добавляя все вершины по которым прошли в стек */
            while (node != null) {
                if (key < node.key) {
                    if (node.left == null) {
                        node.left = AVLNode(key, value)
                        stack.addLast(node)
                        stack.addLast(node.left)
                        break
                    }
                    stack.addLast(node)
                    node = node.left
                    stack.addLast(node)
                } else if (key > node.key) {
                    if (node.right == null) {
                        node.right = AVLNode(key, value)
                        stack.addLast(node)
                        stack.addLast(node.right)
                        break
                    }
                    stack.addLast(node)
                    node = node.right
                    stack.addLast(node)
                } else if (key == node.key) {
                    /* Вершины с таким ключом уже есть, очищаем стек и перезаписываем value */
                    node.value = value
                    while(stack.isNotEmpty()) {
                        stack.removeLast()
                    }
                    break
                }
            }
            balance(stack)
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
        var parent: AVLNode<K, V>? = null
        /* Создаём стек */
        val stack = ArrayDeque<AVLNode<K, V>?>()
        /* Поиск узла и его родителя, и добавление всех вершин на пути в стек */
        while (node != null && node.key != key) {
            stack.addLast(parent)
            stack.addLast(node)
            parent = node
            if (key < node.key) {
                node = node.left
            } else {
                node = node.right
            }
        }

        if (node == null) {
            /* Вершины с таким ключом нет в дереве */
            while(stack.isNotEmpty()) {
                stack.removeLast()
            }
            return false
        }

        /* Если у узла нет правого поддерева */
        if (node.right == null) {
            /* В данном случае удаление простое*/
            if (parent == null) {
                root = node.left // Удаляемый узел - корень
                stack.addLast(null)
                stack.addLast(node.left)
            } else {
                stack.addLast(parent)
                if (node == parent.left) {
                    parent.left = node.left
                    stack.addLast(node.left )
                } else {
                    parent.right = node.left
                    stack.addLast(node.right)
                }
            }
        } else {
            /* Находим наименьший узел в правом поддереве (замену) */
            var successor: AVLNode<K, V>? = node.right
            var successorParent: AVLNode<K, V>? = node

            while (successor?.left != null) {
                successorParent = successor
                successor = successor.left
            }

            /* Заменяем удаляемый узел найденным узлом */
            if (successorParent != node) {
                successorParent?.left = successor?.right
                successor?.right = node.right
            }

            successor?.left = node.left

            /* Добавляем замененную вершину в стек и корректируем родителя */
            if (parent == null) {
                root = successor // Если удаляем корень, делаем его найденной заменой
                stack.addLast(null)
                stack.addLast(root)
            } else if (node == parent.left) {
                parent.left = successor
                stack.addLast(parent)
                stack.addLast(successor)
            } else {
                parent.right = successor
                stack.addLast(parent)
                stack.addLast(successor)
            }
            /* Добавляем все вершины, которые прошли в поиске замены в стек */
            parent = successor
            if (successor != null) {
                successor = successor.right
            }
            if (successor != null) {
                stack.addLast(parent)
                stack.addLast(successor)
                while(successor?.left != null){
                    parent = successor
                    successor = successor.left
                    stack.addLast(parent)
                    stack.addLast(successor)
                }
            }
        }

        balance(stack)
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
     * @return Возвращает высоту вершину
     */
    private fun confirmHeight(node: AVLNode<K, V>?): Int {
        if (node == null) {
            return 0
        }
        return node.height
    }

    /**
     * @return Возвращает фактор баланса вершины
     * по формуле: высота правого потомка - высота левого потомка
     */
    private fun getBalanceValue(node: AVLNode<K, V>?): Int {
        if (node == null) {
            return 0
        }
        return confirmHeight(node.right) - confirmHeight(node.left)
    }

    /**
     * Чинит высоту вершины
     */
    private fun fixHeight(node: AVLNode<K, V>?){
        if (node != null) {
            node.height = 1 + max(confirmHeight(node.left), confirmHeight(node.right))
        }
    }

    /**
     * Создает итератор для обхода дерева в ширину (BFS).
     *
     * @return Итератор для обхода дерева в ширину.
     */
    override fun treeBFSIterator(): Iterator<AVLNode<K, V>> {
        return TreeBFSIterator(root)
    }

    /**
     * Создает итератор для обхода дерева в глубину (DFS).
     *
     * @return Итератор для обхода дерева в глубину.
     */
    override fun treeDFSIterator(): Iterator<AVLNode<K, V>> {
        return TreeDFSIterator(root)
    }

}
