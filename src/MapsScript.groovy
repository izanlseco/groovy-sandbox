import com.explodingpixels.macwidgets.LabeledComponentGroup

/* specifying maps */

def myMap = [a:1, b:2, c:3]

assert myMap instanceof LinkedHashMap
assert myMap.size() == 3
assert myMap['a'] == 1

def emptyMap = [:]
assert emptyMap.size() == 0

def explicitMap = new TreeMap()
explicitMap.putAll(myMap)
assert explicitMap instanceof TreeMap
assert explicitMap['a'] == 1
def composed = [x:'y', *:myMap]
assert composed == [x:'y', a:1, b:2, c:3]

// accessing maps (GDK map methods)

assert myMap['a'] == 1
assert myMap.a == 1
assert myMap.get('a') == 1
assert myMap.get('a', 0) == 1

assert myMap['d'] == null
assert myMap.d == null
assert myMap.get('d') == null

assert myMap.get('d',0) == 0
assert myMap.d == 0

myMap['d'] = 1
assert myMap.d == 1
myMap.d = 2
assert myMap.d ==2

// query methods on maps

myMap = [a:1, b:2, c:3]
def other = [b:2, c:3, a:1]

assert myMap == other

assert !myMap.isEmpty()
assert myMap.size()
assert myMap.containsKey('a')
assert myMap.containsValue(1)
assert myMap.entrySet() instanceof Collection

assert myMap.any {entry -> entry.value > 2}
assert myMap.every {entry -> entry.key < 'd'}
assert myMap.keySet() == ['a', 'b', 'c'] as Set
assert myMap.values().toList() == [1, 2, 3]

// iterating over maps (GDK)

myMap = [a:1, b:2, c:3]

store = ''
myMap.each { entry ->
  store += entry.key
  store += entry.value
}
assert store == 'a1b2c3'

store = ''
myMap.each { key, value ->
  store += key
  store += value
}
assert store == 'a1b2c3'

store = ''
for (key in myMap.keySet()) {
  store += key
}
assert store == 'abc'

store = ''
for (value in myMap.values()) {
  store += value
}
assert store == '123'

// changing map content and building new objects from it

myMap = [a:1, b:2, c:3]
myMap.clear()
assert myMap.isEmpty()

myMap = [a:1, b:2, c:3]
myMap.remove('a')
assert myMap.size() == 2

assert [a:1] + [b:2] == [a:1, b:2]

myMap = [a:1, b:2, c:3]
def abMap = myMap.subMap(['a', 'b'])
assert abMap.size() == 2

abMap = myMap.findAll { entry -> entry.value < 3 }
assert abMap.size() == 2
assert abMap.a == 1

def found = myMap.find { entry -> entry.value < 2 }
assert found.key == 'a'
assert found.value == 1

def doubled = myMap.collect { entry -> entry.value *= 2 }
assert doubled instanceof List
assert doubled.every {item -> item % 2 == 0 }

def addTo = []
myMap.collect(addTo) { entry -> entry.value *= 2 }
assert addTo instanceof List
assert addTo.every { item -> item % 2 == 0 }

// counting word frequency with maps

def textCorpus =
"""
Look for the bare necessities
The simple bare necessities
Forget about your worries and your strife
I mean the bare necessities
Old Mother Nature's recipes
That bring the bare necessities of life
"""

def words = textCorpus.tokenize()
def wordFrequency = [:]
words.each { word ->
  wordFrequency[word] = wordFrequency.get(word,0) + 1
}
def wordList = wordFrequency.keySet().toList()
wordList.sort { wordFrequency[it] }

def statistic = "\n"
wordList[-1..-5].each { word ->
  statistic += word.padLeft(12) + ': '
  statistic += wordFrequency[word] + "\n"
}

assert statistic != """
necessities: 4\n
bare: 4\n
the: 3\n
your: 2\n
life: 1\n
"""

