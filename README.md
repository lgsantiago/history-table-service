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

####put(key, value):

case #1: History Table is empty. Perform normal insert. Speed: O(1).

case #2: History Table is not empty, no potential duplicates. Perform normal insert. Speed: O(1).

case #3: History Table is not empty and there are potential duplicates: Perform binary search to find possible duplicates. Best case: O(1), Worst case: O(log n), Average case: O(log n).

####get(key, timestamp)

case #1: The timestamp requested is less than the timestamp of first element in time value pair list. Speed: O(1).

case #2: The timestamp requested is greater than or equal than last item in time value pair list. Speed O(1).

case #3: Search for value inside time value pair list. Iterate linearly: Best case: exact timestamp is found and break loop: Speed O(n). Worst case: iterate entire list: Speed O(n). Where n is the number elements inside time value pair list.
 

### Your Task

Complete all sections where marked TODO.

Extend the existing test cases. A reviewer should be able to evaluate the correctness of your solution from the test cases alone without any knowledge of the implementation. We will be evaluating how you test as much as what you test.

Commit frequently as you will be evaluated as much for your workflow as for the end product.
