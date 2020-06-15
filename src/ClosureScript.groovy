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

// calling closures

def adder = { x, y -> return x+y }

assert adder(4, 3) == 7
assert adder.call(2, 6) == 8

def benchmark(int repeat, Closure worker) {
  def start = System.nanoTime()

  repeat.times { worker(it) }

  def stop = System.nanoTime()
  return stop - start
}
def slow = benchmark(10000) { (int) it / 2 }
def fast = benchmark(10000) { it.intdiv(2) }
assert fast * 2 < slow

// reacting on the parameter count or type

def numParams (Closure closure) {
  closure.getMaximumNumberOfParameters()
}

assert numParams { one -> } == 1
assert numParams { one, two -> } == 2

def paramTypes (Closure closure) {
  closure.getParameterTypes()
}

assert paramTypes { String s -> } == [String]
assert paramTypes { Number n, Date d -> } == [Number, Date]

// a simple currying example

def mult = { x, y -> return x * y }
def twoTimes = mult.curry(2)
assert twoTimes(5) == 10

// more elaborate currying

def configurator = { format, filter, line -> filter(line) ? format(line) : null }
def appender = { config, append, line ->
  def out = config(line)
  if (out) append (out)
}

def dateFormatter = { line -> "${new Date()}: $line" }
def debugFilter = { line -> line.contains('debug') }
def consoleAppender = { line -> println line }

def myConf = configurator.curry(dateFormatter, debugFilter)
def myLog = appender.curry(myConf, consoleAppender)

myLog('here is some debug message')
myLog('this will not be printed')

// closure composition

def fourTimes = twoTimes >> twoTimes
def eightTimes = twoTimes << fourTimes

assert eightTimes(1) == twoTimes(fourTimes(1))

// memoization

def fib
fib = { it < 2 ? 1 : fib(it-1) + fib(it-2) }
fib = fib.memoize()
assert fib(40) == 165_580_141

// trampoline

def last
last = { it.size() == 1 ? it.head() : last.trampoline(it.tail()) }
last = last.trampoline()

assert last(1..10_000) == 10_000

// classification via the isCase method

def odd = { it % 2 == 1 }

assert [1,2,3].grep(odd) == [1, 3]

switch (10) {
  case odd : assert false
}

if (2 in odd) assert false

// investigating closure scope

class Mother {
  def prop = 'prop'
  def method()  {
    'method'
  }

  Closure birth (param) {
    def local = 'local'
    def closure = {
      [ this, prop, method(), local, param ]
    }
    return closure
  }
}

Mother julia = new Mother()
def closure = julia.birth('param')

def context = closure.call()

assert context[0] == julia
assert context[1, 2] == ['prop', 'method']
assert context[3, 4] == ['local', 'param']

assert closure.thisObject == julia
assert closure.owner == julia

assert closure.delegate == julia
assert closure.resolveStrategy == Closure.OWNER_FIRST

def mapThree = [:]

mapThree.with {
  a = 1
  b = 2
}
assert mapThree == [a:1 ,b:2]

// accumulator problem in groovy

def foo(n) {
  return { n += it }
}

def accumulator = foo(1)

assert accumulator(2) == 3
assert accumulator(1) == 4