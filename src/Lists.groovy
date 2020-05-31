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

// list operators involved in adding and removing items

myList = []

myList += 'a'
assert myList == ['a']

myList += ['b','c']
assert  myList == ['a','b','c']

myList = []
myList << 'a' << 'b'
assert myList == ['a','b']

assert myList - ['b'] == ['a']

assert myList * 2 == ['a','b','a','b']

// lists taking part in control structures

myList = ['a','b','c']

assert myList.isCase('a')
assert 'b' in  myList

def candidate = 'c'
switch(candidate) {
    case myList : assert true; break
    default     : assert false
}

assert ['x','a','z'].grep(myList) == ['a']

myList = []
if (myList) assert false

// lists can be iterated with a for loop
def expr = ''

for (i in [1,'*',5]){
    expr += i
}
assert expr == '1*5'

// methods to manipulate list content

assert [1, [2,3]].flatten() == [1,2,3]
assert [1,2,3].intersect([4,3,1]) == [3,1]
assert [1,2,3].disjoint([4,5,6])

list = [1,2,3]
popped = list.pop()
assert popped == 1
assert list == [2,3]

assert [1,2].reverse() == [2,1]
assert [3,1,2].sort() == [1,2,3]

def list = [[1,0],[0,1,2]]
list = list.sort {a,b -> a[0] <=> b[0]}
assert list == [[0,1,2],[1,0]]

list = list.sort {item -> item.size()}
assert list == [[1,0],[0,1,2]]

list = ['a','b','c']
list.remove(2)
assert list == ['a','b']
list.remove('b')
assert list == ['a']

list = ['a','b','b','c']
list.removeAll(['b','c'])
assert list == ['a']

def doubled = [1,2,3].collect { item ->
    item * 2
}
assert doubled == [2,4,6]

def odd = [1,2,3].findAll { item->
    item % 2 == 1
}
assert odd == [1,3]

def x = [1,1,1]
assert [1] == new HashSet(x).toList()
assert [1] == x.unique()

def y =[1,null,1]
assert [1,1] == y.findAll {it != null}
assert [1,1] == y.grep {it}

// list query, iteration, and accumulation

def listTwo = [1,2,3]

assert listTwo.first() == 1
assert listTwo.head() == 1
assert listTwo.tail() == [2,3]
assert listTwo.last() == 3
assert listTwo.count(2) == 1
assert listTwo.max() == 3
assert listTwo.min() == 1

def even = listTwo.find { item ->
    item % 2 == 0
}
assert even == 2

assert listTwo.every {item -> item < 5 }
assert listTwo.any {item -> item < 2 }

def store = ''
listTwo.each {item ->
    store += item
}
assert store == '123'

store = ''
listTwo.reverseEach { item ->
    store += item
}
assert store == '321'

store = ''
listTwo.eachWithIndex{ item, index ->
    store += "$index:$item "
}
assert store == '0:1 1:2 2:3 '
assert listTwo.join('-') == '1-2-3'

result = listTwo.inject(0) { clinks, guests ->
    clinks + guests
}
assert result == 0 + 1 + 2 + 3
assert listTwo.sum() == 6

factorial = listTwo.inject(1) { fac, item ->
    fac * item
}
assert factorial == 1 * 1 * 2 * 3

// quicksort with lists

def quicksort(list) {
    if (list.size() < 2) return list
    def pivot  = list[list.size().intdiv(2)]
    def left   = list.findAll { item -> item <  pivot }
    def middle = list.findAll { item -> item == pivot }
    def right  = list.findAll { item -> item >  pivot }
    return quicksort(left) + middle + quicksort(right)
}

assert quicksort([])                 == []
assert quicksort([1])                == [1]
assert quicksort([1,2])              == [1,2]
assert quicksort([2,1])              == [1,2]
assert quicksort([3,1,2])            == [1,2,3]
assert quicksort([3,1,2,2])          == [1,2,2,3]
assert quicksort([1.0f,'a',10,null]) == [null,1.0f,10,'a']
assert quicksort('bca')          == 'abc'.toList()

// processing lists of URLs

def urls = [
       new URL('http', 'myshop.com', 80, 'index.html'),
       new URL('https', 'myshop.com', 443, 'buynow.html'),
       new URL('ftp', 'myshop.com', 21, 'downloads')
]

assert urls
        .findAll { it.port < 99 }
        .collect { it.file.toUpperCase() }
        .sort()
        .join(', ') == 'DOWNLOADS, INDEX.HTML'