# Библиотека (Kotlin)

Этот проект реализует различные **деревья поиска** на Kotlin, включая:
- **Обычное двоичное дерево поиска (BST)**
- **Красно-черное дерево (Red-Black Tree)**
- **AVL-дерево**

## 📂 Структура проекта
```
com/example/datastructures/
│── common/               # Общие интерфейсы
│   ├── Tree.kt           # Интерфейс для деревьев
│── dto/                  # Классы узла
│   ├── Node.kt           # Самый базовый узел
│   ├── BNode.kt           # Узел для BST
│   ├── AVLNode.kt           # Узел для АВЛ-дерева
│   ├── RBNode.kt           # Узел для красно-черного дерева
│── iterators/            # Общие итераторы
│   ├── TreeBFSIterator.kt  # Итератор обхода в ширину
│   ├── TreeDFSIterator.kt  # Итератор обхода в глубину
│── trees/                # Реализации различных деревьев
│   ├── BinarySearchTree.kt    # Реализация BST
│   ├── RedBlackTree.kt        # Реализация красно-черного дерева
│   ├── AVLTree.kt             # Реализация AVL-дерева
│
└── test/                 # Юнит-тесты для деревьев
    ├── BSTTest.kt
    ├── RBTest.kt
    ├── AVLTreeTest.kt
```

## 🚀 Начало работы
### 1️⃣ Клонирование репозитория
```sh
git clone https://github.com/spbu-coding-2024/trees-trees-team-2.git
cd trees-trees-team-2
```

### 2️⃣ Сборка проекта
Используйте **Gradle** для сборки проекта:
```sh
./gradlew build
```

### 3️⃣ Запуск тестов
```sh
./gradlew test
```

## 🛠 Использование
Вы можете создать и использовать любое дерево подобным образом:
```kotlin
import com.example.trees.BinarySearchTree

fun main() {
    val bst = BinarySearchTree<Int, Int>()
    bst.insert(5, 10)
    bst.insert(3, 22)
    bst.insert(7, 7)
    
    println("Поиск 10: ${bst.search(5)}") // Вернет значение 10
    println("Поиск 20: ${bst.search(3)}") // Вернет значение 22
}
```

## 📌 Возможности
✅ Вставка, удаление, поиск элементов, перебор элементов

## 📜 Лицензия
Этот проект распространяется под лицензией MIT.
