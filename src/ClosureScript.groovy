// simple abbreviated closure declaration

log = ''
(1..10).each { counter -> log += counter}
assert log == '12345678910'

log = ''
(1..10).each { log += it }
assert log == '12345678910'

// simple method closures in action

class SizeFilter {
  Integer limit

  boolean sizeUpTo(String value) {
    return value.size() <= limit
  }
}

SizeFilter filter6 = new SizeFilter(limit:6)
SizeFilter filter5 = new SizeFilter(limit:5)

Closure sizeUpTo6 = filter6.&sizeUpTo

def words = ['long string', 'medium', 'short', 'tiny']

assert 'medium' == words.find (sizeUpTo6)
assert 'short' == words.find (filter5.&sizeUpTo)

// Multimethod, also known as runtime overload resolution, closures

class MultiMethodSample {
  int mysteryMethod (String value) {
    return value.length()
  }
  int mysteryMethod (List list) {
    return list.size()
  }
  int mysteryMethod (int x, int y) {
    return x+y
  }
}

MultiMethodSample instance = new MultiMethodSample()
Closure multi = instance.&mysteryMethod

assert 10 == multi ('string arg')
assert 3 == multi (['list', 'of', 'values'])
assert 14 == multi (6, 8)

// Full closure declaration examples

Map map = ['a':1, 'b':2]
map.each {key, value -> map[key] = value * 2 }
assert map == ['a':2, 'b':4]

Closure doubler = { key, value -> map[key] = value * 2 }
map.each(doubler)
assert map == ['a':4, 'b':8]

def doubleMethod (entry) {
  entry.value = entry.value * 2
}
doubler = this.&doubleMethod
map.each(doubler)
assert map == ['a':8, 'b':16]