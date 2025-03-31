package trees

import common.Tree
import dto.AVLNode
import iterators.AVLTreeBFSIterator
import iterators.AVLTreeDFSIterator
import kotlin.math.max
import kotlin.collections.ArrayDeque

class AVLTree<K : Comparable<K>, V> : Tree<K, V> {

    private var root: AVLNode<K, V>? = null

    override fun insert(key: K, value: V) {
        if (root == null) {
            root = AVLNode(key, value)
        } else {
            var node = root
            val stack = ArrayDeque<AVLNode<K, V>?>()
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
                } else if (key > node.key) {
                    if (node.right == null) {
                        node.right = AVLNode(key, value)
                        stack.addLast(node)
                        stack.addLast(node.right)
                        break
                    }
                    stack.addLast(node)
                    node = node.right
                } else if (key == node.key) {
                    node.value = value
                    break
                }
            }
            while (stack.isNotEmpty()) {
                val unbalanceNode = stack.removeLast()
                if (unbalanceNode != null) {
                    unbalanceNode.height = 1 + max(confirmHeight(unbalanceNode.left), confirmHeight(unbalanceNode.right))
                }
                val balance = getBalanceValue(unbalanceNode)
                if (balance > 1) {
                    if (unbalanceNode != null) {
                        val right = unbalanceNode.right
                        if (getBalanceValue(right) < 0) {
                            if (right != null) {
                                val tmpNode = right.left
                                if (tmpNode != null) {
                                    right.left = tmpNode.right
                                    tmpNode.right = right
                                }
                                right.height = 1 + max(confirmHeight(right.left), confirmHeight(right.right))
                                if (tmpNode != null) {
                                    tmpNode.height = 1 + max(confirmHeight(tmpNode.left), confirmHeight(tmpNode.right))
                                }
                                unbalanceNode.right = tmpNode
                                unbalanceNode.height = 1 + max(confirmHeight(unbalanceNode.left), confirmHeight(unbalanceNode.right))
                            }
                        }
                    }
                    if (unbalanceNode != null) {
                        val tmpNode = unbalanceNode.right
                        if (tmpNode != null) {
                            unbalanceNode.right = tmpNode.left
                            tmpNode.left = unbalanceNode
                        }
                        unbalanceNode.height = 1 + max(confirmHeight(unbalanceNode.left), confirmHeight(unbalanceNode.right))
                        if (tmpNode != null) {
                            tmpNode.height = 1 + max(confirmHeight(tmpNode.left), confirmHeight(tmpNode.right))
                        }
                        if (stack.isNotEmpty()) {
                            val parent1 = stack.last()
                            if (parent1 != null) {
                                parent1.right = tmpNode
                                parent1.height = 1 + max(confirmHeight(parent1.left), confirmHeight(parent1.right))
                            }
                        }else {
                            root = tmpNode
                        }
                    }
                }
                if (balance < -1) {
                    if (unbalanceNode != null) {
                        val left = unbalanceNode.left
                        if (getBalanceValue(left) > 0) {
                            if (left != null) {
                                val tmpNode = left.right
                                if (tmpNode != null) {
                                    left.right = tmpNode.left
                                    tmpNode.left = left
                                }
                                left.height = 1 + max(confirmHeight(left.left), confirmHeight(left.right))
                                if (tmpNode != null) {
                                    tmpNode.height = 1 + max(confirmHeight(tmpNode.left), confirmHeight(tmpNode.right))
                                }
                                unbalanceNode.left = tmpNode
                                unbalanceNode.height = 1 + max(confirmHeight(unbalanceNode.left), confirmHeight(unbalanceNode.right))
                            }
                        }
                    }
                    if (unbalanceNode != null) {
                        val tmpNode = unbalanceNode.left
                        if (tmpNode != null) {
                            unbalanceNode.left = tmpNode.right
                            tmpNode.right = unbalanceNode
                        }
                        unbalanceNode.height = 1 + max(confirmHeight(unbalanceNode.left), confirmHeight(unbalanceNode.right))
                        if (tmpNode != null) {
                            tmpNode.height = 1 + max(confirmHeight(tmpNode.left), confirmHeight(tmpNode.right))
                        }
                        if (stack.isNotEmpty()) {
                            val parent1 = stack.last()
                            if (parent1 != null) {
                                parent1.left = tmpNode
                                parent1.height = 1 + max(confirmHeight(parent1.left), confirmHeight(parent1.right))
                            }
                        }else {
                            root = tmpNode
                        }
                    }
                }
            }
        }
    }


    override fun delete(key: K) {
        var node = root
        var parent: AVLNode<K, V>? = null
        val stack = ArrayDeque<AVLNode<K, V>?>()

        // Поиск узла и его родителя
        while (node != null && node.key != key) {
            stack.addLast(node)
            parent = node
            node = if (key < node.key) node.left else node.right
        }
        if (stack.isNotEmpty()) {
            stack.removeLast()
        }

        if (node == null) return // Узел не найден

        // Если у узла нет правого поддерева
        if (node.right == null) {
            if (parent == null) {
                root = node.left // Удаляемый узел - корень
                val tmp = node.left
                if (tmp != null) {
                    tmp.height = 1 + max(confirmHeight(tmp.left), confirmHeight(tmp.right))
                }
                stack.addLast(node.left)
            } else {
                if (node == parent.left) {
                    parent.left = node.left
                } else {
                    parent.right = node.left
                }
                parent.height = 1 + max(confirmHeight(parent.left), confirmHeight(parent.right))
            }
        } else {
            // Находим наименьший узел в правом поддереве (замену)
            var successor: AVLNode<K, V>? = node.right
            var successorParent: AVLNode<K, V>? = node

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

            if (successor != null) {
                successor.height = 1 + max(confirmHeight(successor.left), confirmHeight(successor.right))
            }

            if (parent == null) {
                root = successor // Если удаляем корень, меняем его на successor
            } else if (node == parent.left) {
                parent.left = successor
                parent.height = 1 + max(confirmHeight(parent.left), confirmHeight(parent.right))
            } else {
                parent.right = successor
                parent.height = 1 + max(confirmHeight(parent.left), confirmHeight(parent.right))
            }

        }

        while (stack.isNotEmpty()) {
            val unbalanceNode = stack.removeLast()
            if (unbalanceNode != null) {
                unbalanceNode.height = 1 + max(confirmHeight(unbalanceNode.left), confirmHeight(unbalanceNode.right))
            }
            var balance = getBalanceValue(unbalanceNode)
            if (balance > 1) {
                if (unbalanceNode != null) {
                    val right = unbalanceNode.right
                    if (getBalanceValue(right) < 0) {
                        if (right != null) {
                            val tmpNode = right.left
                            if (tmpNode != null) {
                                right.left = tmpNode.right
                                tmpNode.right = right
                            }
                            right.height = 1 + max(confirmHeight(right.left), confirmHeight(right.right))
                            if (tmpNode != null) {
                                tmpNode.height = 1 + max(confirmHeight(tmpNode.left), confirmHeight(tmpNode.right))
                            }
                            unbalanceNode.right = tmpNode
                            unbalanceNode.height = 1 + max(confirmHeight(unbalanceNode.left), confirmHeight(unbalanceNode.right))
                        }
                    }
                }
                if (unbalanceNode != null) {
                    val tmpNode = unbalanceNode.right
                    if (tmpNode != null) {
                        unbalanceNode.right = tmpNode.left
                        tmpNode.left = unbalanceNode
                    }
                    unbalanceNode.height = 1 + max(confirmHeight(unbalanceNode.left), confirmHeight(unbalanceNode.right))
                    if (tmpNode != null) {
                        tmpNode.height = 1 + max(confirmHeight(tmpNode.left), confirmHeight(tmpNode.right))
                    }
                    if (stack.isNotEmpty()) {
                        val parent1 = stack.last()
                        if (parent1 != null) {
                            parent1.right = tmpNode
                            parent1.height = 1 + max(confirmHeight(parent1.left), confirmHeight(parent1.right))
                        }
                    }else {
                        root = tmpNode
                    }
                }
            }
            if (balance < -1) {
                if (unbalanceNode != null) {
                    val left = unbalanceNode.left
                    if (getBalanceValue(left) > 0) {
                        if (left != null) {
                            val tmpNode = left.right
                            if (tmpNode != null) {
                                left.right = tmpNode.left
                                tmpNode.left = left
                            }
                            left.height = 1 + max(confirmHeight(left.left), confirmHeight(left.right))
                            if (tmpNode != null) {
                                tmpNode.height = 1 + max(confirmHeight(tmpNode.left), confirmHeight(tmpNode.right))
                            }
                            unbalanceNode.left = tmpNode
                            unbalanceNode.height = 1 + max(confirmHeight(unbalanceNode.left), confirmHeight(unbalanceNode.right))
                        }
                    }
                }
                if (unbalanceNode != null) {
                    val tmpNode = unbalanceNode.left
                    if (tmpNode != null) {
                        unbalanceNode.left = tmpNode.right
                        tmpNode.right = unbalanceNode
                    }
                    unbalanceNode.height = 1 + max(confirmHeight(unbalanceNode.left), confirmHeight(unbalanceNode.right))
                    if (tmpNode != null) {
                        tmpNode.height = 1 + max(confirmHeight(tmpNode.left), confirmHeight(tmpNode.right))
                    }
                    if (stack.isNotEmpty()) {
                        val parent1 = stack.last()
                        if (parent1 != null) {
                            parent1.left = tmpNode
                            parent1.height = 1 + max(confirmHeight(parent1.left), confirmHeight(parent1.right))
                        }
                    }else {
                        root = tmpNode
                    }
                }
            }
        }

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

    fun treeBFSIterator(): Iterator<AVLNode<K, V>> {
        return AVLTreeBFSIterator(root)
    }

    fun treeDFSIterator(): Iterator<AVLNode<K, V>> {
        return AVLTreeDFSIterator(root)
    }

}
