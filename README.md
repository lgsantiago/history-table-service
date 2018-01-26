# History Table

### Description
HistoryTable which is a key-value store that can also tell you the values at past moments in time.


put(key, value) // Normal "put" - stores the key/value pair\
get(key, timestamp) // Return the value of the key as it was at the given timestamp.

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

### Complexity analysis

### put(key, value):

case #1: History Table is empty. Perform normal insert. Speed: O(1).

case #2: History Table is not empty, no potential duplicates. Perform normal insert. Speed: O(1).

case #3: History Table is not empty and there are potential duplicates: Perform binary search to find possible duplicates. Best case: O(1), Worst case: O(log n), Average case: O(log n).

### get(key, timestamp)

case #1: The timestamp requested is less than the timestamp of first element in time value pair list. Speed: O(1).

case #2: The timestamp requested is greater than or equal than last item in time value pair list. Speed O(1).

case #3: Search for value inside time value pair list. Iterate linearly: Best case: exact timestamp is found and break loop: Speed O(n). Worst case: iterate entire list: Speed O(n). Where n is the number elements inside time value pair list.
 

