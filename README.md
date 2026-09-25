# OOP Practice Quiz — Cafe Cashier (POS)

🇮🇩 Versi Bahasa Indonesia: [README.id.md](README.id.md)

## Case Study

You are building the core of a simple **cafe cashier (point-of-sale) application**:

- The cafe has a **menu** containing food and drink items.
- A **customer** places an **order** containing one or more menu items with a quantity.
- Orders of **Rp 100,000 or more** get a **10% discount**.
- The **cashier** receives cash and calculates the change.

This quiz covers four OOP concepts: **class**, **object**, **encapsulation**, and **class relations** (association, aggregation, composition, dependency).

## Class Diagram

The class diagram is your **primary specification**: every class, field, constructor, and method signature you need is defined there. Read it carefully.

![Class Diagram](docs/class-diagram.png)

Classes marked **«create this class»** do not exist yet — you must create them yourself.

### The 4 Class Relations in This Project

| Relation | Where | Meaning here |
|---|---|---|
| **Association** | `OrderItem → MenuItem`, `Order → Customer` | The object holds a reference to another object that lives on its own. |
| **Aggregation** | `Menu ◇→ MenuItem` | `Menu` collects `MenuItem` objects that are **created outside** and passed in; they can exist without the menu. |
| **Composition** | `Order ◆→ OrderItem` | `Order` **creates its own** `OrderItem` objects inside `addItem(...)`; they cannot exist without the order. |
| **Dependency** | `Cashier ⇢ Order` | `Cashier` only **uses** an `Order` as a method parameter; it never stores it in a field. |

## Getting Started

This repository is a **template**:

1. Click the green **Use this template** button (top right) → **Create a new repository**, set the visibility to **Private**, and name it e.g. `oop-quiz-<your-name>`.
2. Clone **your** new repository:
   ```bash
   git clone git@github.com:<your-username>/oop-quiz-<your-name>.git
   ```
3. Open the project in your editor (see below), implement the classes, then commit and push.
4. Every push runs the autograder. Open the **Actions** tab in your repository to see your score. If GitHub asks you to enable workflows the first time, click **I understand my workflows, enable them**.

## Opening the Project

This is a standard Maven project — no extra configuration needed.

**NetBeans**
1. **File → Open Project…**
2. Select the cloned `oop-quiz-...` folder (NetBeans recognizes it as a Maven project automatically) → **Open Project**.
3. Right-click the project → **Test** to run all tests, or run a single test file from the `Test Packages` node.

**Visual Studio Code**
1. Install the **Extension Pack for Java** (Microsoft) from the Extensions view.
2. **File → Open Folder…** and select the cloned folder.
3. Run tests from the **Testing** (flask icon) side bar, or via the terminal:
   ```bash
   mvn test
   ```

## Part 1 — Complete the Skeleton Classes

`MenuItem`, `Customer`, and `Menu` already exist in `src/main/java/id/ac/polinema/oop/`. Replace every `throw new UnsupportedOperationException(...)` with a working implementation. The structure (fields, constructors, signatures) comes from the class diagram; the behavior rules are:

- **`MenuItem`** — `setPrice` **ignores** a **negative** price and keeps the old price.
- **`Customer`** — `setName` **ignores** a **null or blank** name and keeps the old name.
- **`Menu`** *(aggregation)* — the array has capacity **10**. `addMenuItem` stores the item at index `itemCount` and increments the counter; when the menu is full it does nothing. `findItem` matches the exact name and returns `null` when not found.

**Use plain arrays — do NOT use `List`/`ArrayList`** (Collections are next meeting's topic).

## Part 2 — Create the New Classes

`OrderItem`, `Order`, and `Cashier` do **not** exist. Create them in `src/main/java/id/ac/polinema/oop/` exactly as drawn in the class diagram. Behavior rules:

- **`OrderItem`** *(association)* — `getSubtotal()` returns the menu item's price × quantity.
- **`Order`** *(composition, association)* — the array has capacity **10**, like `Menu`. `addItem(MenuItem, int)` **creates the `OrderItem` inside the method** (this is the composition!); when the order is full it does nothing. `getTotal()` sums every line's subtotal. `getFinalTotal()` applies a **10% discount** when the total is **≥ 100000**, otherwise returns the total unchanged.
- **`Cashier`** *(dependency)* — no fields; `Order` is only a parameter. `calculateChange(Order, double)` returns the cash minus the final total.

Tip: work in the grading-table order below — the points are small and incremental, so every step you finish is immediately reflected in your score.

## Try the App Manually

`Main.java` is a free playground (not graded). After finishing all classes, write your demo there, for example:

```java
public static void main(String[] args) {
    Menu menu = new Menu();
    menu.addMenuItem(new MenuItem("Es Kopi Susu", 18000));
    menu.addMenuItem(new MenuItem("Roti Bakar", 12000));

    Customer budi = new Customer("C001", "Budi Santoso");
    Order order = new Order(budi);
    order.addItem(menu.findItem("Es Kopi Susu"), 2);
    order.addItem(menu.findItem("Roti Bakar"), 1);

    Cashier cashier = new Cashier();
    double cash = 50000;

    System.out.println("Customer : " + order.getCustomer().getName());
    System.out.println("Total    : " + order.getTotal());
    System.out.println("Payable  : " + order.getFinalTotal());
    System.out.println("Cash     : " + cash);
    System.out.println("Change   : " + cashier.calculateChange(order, cash));
}
```

Run it from NetBeans (**Run Project**), VS Code (**Run** above `main`), or the terminal:

```bash
mvn -q compile exec:java
```

Expected output:

```
Customer : Budi Santoso
Total    : 48000.0
Payable  : 48000.0
Cash     : 50000.0
Change   : 2000.0
```

## Grading

Total **100 points**, split across 12 small test groups (run via GitHub Actions on every push). Run them locally first:

```bash
mvn test                              # everything
mvn test -Dtest=MenuItemConstructorTest   # a single group
```

| # | Test Group | Concept | Points |
|---|---|---|---|
| 1 | MenuItem Constructor Test | class & object | 5 |
| 2 | MenuItem Getter Test | class & object | 5 |
| 3 | MenuItem Encapsulation Test | encapsulation | 10 |
| 4 | Customer Test | encapsulation | 10 |
| 5 | Menu Aggregation Test | aggregation | 10 |
| 6 | OrderItem Class Structure Test | new class, association | 10 |
| 7 | OrderItem Subtotal Test | object behavior | 5 |
| 8 | Order Class Structure Test | new class, association | 10 |
| 9 | Order Add Item Test | composition | 10 |
| 10 | Order Total Test | relation traversal | 10 |
| 11 | Order Discount Test | business logic | 5 |
| 12 | Cashier Test | dependency | 10 |
| | **Total** | | **100** |

## Rules

- **Do not modify** any files under `src/test/**` or `.github/**`.
- Follow the class diagram exactly — class names, field names, method names, and signatures are used by the autograder as drawn.
- All fields must be `private` (this is checked by the tests).
- Use plain arrays only — no `List`, `ArrayList`, or any other Collection.
