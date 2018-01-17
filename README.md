# History Table

### Problem discription
We want to write the class HistoryTable which is a key-value store that can also tell you the values at past moments in time.

The functions we want to implement are:

put(key, value) // Normal "put" - stores the key/value pair\
get(key, timestamp) // Return the value of the key as it was at the given timestamp.

Assume that we already have\
getCurrentTime() // Returns the current timestamp, monotonically increasing every time it is called

Example:

At 2pm:\
put("a", 7)

At 4pm:\
put("a", 9)

Any time later:\
get("a", 1pm) -> null\
get("a", 2pm) -> 7\
get("a", 3:45pm) -> 7\
get("a", 5pm) -> 9

### Running the project

You are encouraged to use your favorite IDE. You may start the application by running [DemoApplication.main()](https://github.com/collectivehealth/history-table/blob/master/src/main/java/com/example/demo/DemoApplication.java#L9). You've also been given an outline of unit tests that you can run in [HistoryTableTest](https://github.com/collectivehealth/history-table/blob/master/src/test/java/com/example/demo/service/HistoryTableTest.java).

### Complexity analysis

// TODO: analyze the performance of your implementation.

### Your Task

Complete all sections where marked TODO.

Extend the existing test cases. A reviewer should be able to evaluate the correctness of your solution from the test cases alone without any knowledge of the implementation. We will be evaluating how you test as much as what you test.

Commit frequently as you will be evaluated as much for your workflow as the end product.
