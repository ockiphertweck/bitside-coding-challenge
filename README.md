# Shopping Basket
 
A small shopping basket in Java. You scan articles, ask for the total at any time, and sales deals are applied automatically. As per task description there is no GUI, further we dont have any main function as the functionality is
shown per automated tests.
 
## Requirements
 
- JDK 25
- Maven 3.9 or newer
## Run the tests
 
```
mvn test
```
 
## Usage
 
```java
Inventory inventory = new Inventory();
inventory.addArticle(new Article("A0001", 1299));
inventory.addArticle(new Article("A0002", 399));
 
// Buy one get one free on A0002
Basket basket = new Basket(inventory, List.of(new BuyOneGetOneFreeDeal("A0002")));
basket.scan("A0002");
basket.scan("A0001");
basket.scan("A0002");
 
basket.total(); // 1698 cents, which is 16.98 EUR
```
 
If there are no deals you can create a Basket just with an inventory.
```java
Inventory inventory = new Inventory();
inventory.addArticle(new Article("A0001", 1299));
inventory.addArticle(new Article("A0002", 399));
 
// Buy one get one free on A0002
Basket basket = new Basket(inventory);
basket.scan("A0002");
basket.scan("A0001");
basket.scan("A0002");
 
basket.total(); // 2097 cents, which is 20.97 EUR
```
 
## Deals

There are currently two types of Deals available, which can be extended easily by creating a new class which extends the `BaseDeal` class
 
| Deal | Example | Task example total |
|---|---|---|
| `BuyOneGetOneFreeDeal` | `new BuyOneGetOneFreeDeal("A0002")` | 16.98 EUR |
| `PercentOffDeal` | `new PercentOffDeal("A0001", 10)` | 19.67 EUR |
 
`BasketTest` has both examples from the task.
 
## Project structure
 
```
de.philipphertweck.shoppingbasket
    Article                  record: article number and price in cents
    Inventory                the warehouse's fixed price list
    Basket                   scan(articleNumber), total()
    deal/
        Deal                 interface
        BaseDeal             shared checks, template method
        BuyOneGetOneFreeDeal
        PercentOffDeal
    exceptions/
        UnknownArticleException
```
 
## Design decisions
 
### Money is stored as integer cents
 
Prices and totals are `long` values in cents. Floating point would give small errors, so every calculation stays exact. Turning cents into euros is left to whoever displays the value.
 
### Discounts are rounded down per unit
 
For the percentage discount, I simply truncated the decimal part of the unit price, so the result is always rounded down. Since we use longs throughout and no floating-point numbers, this is the simplest and cleanest solution. I deliberately skipped commercial rounding because the difference is only a fraction of a cent per unit and makes no practical difference.
 
### The inventory is passed to the basket
 
A basket cannot calculate anything without prices, so the inventory is a constructor argument. Each test builds its own inventory, which means no shared state and nothing to reset between tests. 

I decided against a singleton for the Inventory, as it would have benn a global mutable state and would hide the dependency to the Basket.
 
### Deals follow the strategy pattern
 
`Deal` has two methods, `articleNumber()` and `priceFor(Article, int)`. A new deal is a new class, and `Basket` does not change. `BaseDeal` implements `priceFor` as a final method that checks the article and then calls the protected abstract method `calculate`. The concrete deals only contain their own rule. With this pattern one can easily extend the Deal for futher use cases.
 
 
### Deals are set up in the constructor
 
Deals are part of the shop setup and known before the first scan, just like the inventory. Each article can have at most one deal. A second one is rejected with an `IllegalArgumentException`.
 

### The price at checkout counts
 
Prices are looked up when the total is calculated. This makes no difference here: `Article` is an immutable record and duplicate article numbers are rejected, so a price cannot change after an article was added.

## Exceptions
 
- `UnknownArticleException` is thrown when scanning an article number that is not in the inventory. A caller can catch it and react.
- `IllegalArgumentException` covers invalid arguments: a duplicate article number, a blank article number, a negative price, a percentage outside 0 to 100, or two deals for the same article.
- `IllegalStateException` is thrown if a scanned article is missing from the inventory during `total()`. That cannot happen after a successful `scan`, so it would point to a bug.
## Assumptions
 
- Stock levels are not modelled. Every article is available in any quantity.
- Items cannot be removed from a basket.
## Tests
 
- Every production class has a test class with the matching name.
- `BasketTest` contains the two examples from the task as end-to-end tests.
- Tests check behavior and not internal structure.
## Possible extensions
 
- Removing items from a basket
- A receipt that lists the applied deals
- Coupon codes entered by the customer
- Several deals per article
- Fractional percentages
 