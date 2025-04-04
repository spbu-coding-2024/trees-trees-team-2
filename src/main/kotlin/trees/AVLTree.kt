package trees

import common.Tree
import dto.AVLNode
import iterators.AVLTreeBFSIterator
import iterators.AVLTreeDFSIterator
import kotlin.math.max
import kotlin.collections.ArrayDeque

class AVLTree<K : Comparable<K>, V> : Tree<K, V>() {

    private var root: AVLNode<K, V>? = null

    //Реализация левого поворота
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
    //Реализация правого поворота
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
    //Реализация балансировки
    //На вход функции подаётся стэк с вершинами, у которых мог измениться фактор баланса
    //В порядке от самой дальней вершины, до дальней -1 и т.д до самого корня
    //В стэке вершины хранятся парами: сверху стэка хранится узел, сразу же под ним идёт его родитель.
    //Соответственно из стэка вершины убираются парами
    private fun balance(stack: ArrayDeque<AVLNode<K, V>?>) {
        while (stack.isNotEmpty()) {
            //Достаём вершину и его родителя
            val unbalanceNode = stack.removeLast()
            val parent = stack.removeLast()
            //Чиним высоту вершины
            if (unbalanceNode != null) {
                fixHeight(unbalanceNode)
            }
            //Расчитываем баланс фактор, баланс фактор считается по формуле: высота правого - высота левого потомка
            val balanceF = getBalanceValue(unbalanceNode)
            //Выполняем балансировку
            if (balanceF > 1) {
                if (unbalanceNode != null) {
                    if (getBalanceValue(unbalanceNode.right) < 0) {
                        unbalanceNode.right = rightRotate(unbalanceNode.right)
                    }
                    if (parent != null) {
                        if (parent.key < unbalanceNode.key) {
                            parent.right = leftRotate(unbalanceNode)
                        } else {
                            parent.left = leftRotate(unbalanceNode)
                        }
                    }else {
                        root = leftRotate(unbalanceNode)
                    }
                }
            }
            if (balanceF < -1) {
                if (unbalanceNode != null) {
                    if (getBalanceValue(unbalanceNode.left) < 0) {
                        unbalanceNode.right = leftRotate(unbalanceNode.left)
                    }
                    if (parent != null) {
                        if (parent.key < unbalanceNode.key) {
                            parent.right = rightRotate(unbalanceNode)
                        } else {
                            parent.left = rightRotate(unbalanceNode)
                        }
                    }else {
                        root = rightRotate(unbalanceNode)
                    }
                }
            }
        }
    }

    override fun insert(key: K, value: V) {
        if (root == null) {
            root = AVLNode(key, value)
        } else {
            var node = root
            val stack = ArrayDeque<AVLNode<K, V>?>()
            stack.addLast(null)
            stack.addLast(node)
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
                    node.value = value
                    break
                }
            }

            balance(stack)
        }
    }


    override fun delete(key: K): Boolean {
        var node = root
        var parent: AVLNode<K, V>? = null
        val stack = ArrayDeque<AVLNode<K, V>?>()
        stack.addLast(parent)
        stack.addLast(node)

        // Поиск узла и его родителя
        while (node != null && node.key != key) {
            parent = node
            node = if (key < node.key) {
                node.left
            } else {
                node.right
            }
            stack.addLast(parent)
            stack.addLast(node)
        }

        if (node == null) return false// Узел не найден

        // Если у узла нет правого поддерева
        if (node.right == null) {
            if (parent == null) {
                root = node.left // Удаляемый узел - корень
                stack.addLast(null)
                stack.addLast(root)
            } else {
                if (node == parent.left) {
                    parent.left = node.left
                } else {
                    parent.right = node.left
                }
                stack.addLast(parent)
                stack.addLast(node)
            }
        } else {
            // Находим наименьший узел в правом поддереве (замену)
            var successor: AVLNode<K, V>? = node.right
            var successorParent: AVLNode<K, V>? = node
            stack.addLast(successorParent)
            stack.addLast(successor)

            while (successor?.left != null) { // Безопасный вызов ?.left
                successorParent = successor
                successor = successor.left
                stack.addLast(successorParent)
                stack.addLast(successor)
            }

            // Заменяем удаляемый узел найденным узлом
            if (successorParent != node) {
                successorParent?.left = successor?.right // Безопасный вызов ?.
                successor?.right = node.right
                stack.addLast(successorParent?.left)
                stack.addLast(successor?.right )
            }

            successor?.left = node.left


            if (parent == null) {
                root = successor // Если удаляем корень, меняем его на successor
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

        }

        balance(stack)
        return true
    }

    override fun search(key: K): V? {
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

    private fun confirmHeight(node: AVLNode<K, V>?): Int {
        if (node == null) {
            return 0
        }
        return node.height
    }

    private fun getBalanceValue(node: AVLNode<K, V>?): Int {
        if (node == null) {
            return 0
        }
        return confirmHeight(node.right) - confirmHeight(node.left)
    }

    private fun fixHeight(node: AVLNode<K, V>?){
        if (node != null) {
            node.height = 1 + max(confirmHeight(node.left), confirmHeight(node.right))
        }
    }


    fun treeBFSIterator(): Iterator<AVLNode<K, V>> {
        return AVLTreeBFSIterator(root)
    }

    fun treeDFSIterator(): Iterator<AVLNode<K, V>> {
        return AVLTreeDFSIterator(root)
    }

}
