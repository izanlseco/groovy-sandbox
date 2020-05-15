// specifying lists

List myList = [1, 2, 3]
assert myList.size() == 3
assert myList[0] == 1
assert myList instanceof ArrayList

List emptyList = []
assert emptyList.size() ==  0

List longList = (0..1000).toList()
assert longList[555] == 555

List explicitList = new ArrayList()
explicitList.addAll(myList)
assert explicitList.size() == 3
explicitList[0] = 10
assert explicitList[0] == 10

explicitList = new LinkedList(myList)
assert explicitList.size() == 3
explicitList[0] = 10
assert explicitList[0] == 10

// accessing parts of a list with an overloaded subscript operator

myList = ['a','b','c','d','e','f']

assert  myList[0..2] == ['a','b','c']
assert myList[0, 2, 4] == ['a','c','e']

myList[0..2] = ['x','y','z']
assert myList == ['x','y','z','d','e','f']

myList[3..5] = []
assert myList == ['x','y','z']

myList[1..1] = [0,1,2]
assert myList == ['x', 0, 1, 2, 'z']