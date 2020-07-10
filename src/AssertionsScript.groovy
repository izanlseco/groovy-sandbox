// using assertions for inline unit tests

def host = /\/\/([a-zA-Z0-9-]+(\.[a-zA-Z0-9-])*?)(:|\/)/

assertHost 'http://a.b.c:8080/bla', host, 'a.b.c'
assertHost 'http://a.b.c/bla', host, 'a.b.c'
assertHost 'http://127.0.0.1:8080/bla', host, '127.0.0.1'
assertHost 'http://t-online.de/bla', host, 't-online.de'
assertHost 'http://T-online.de/bla', host, 'T-online.de'

def assertHost (candidate, regex, expected) {
  candidate.eachMatch(regex) { assert it[1] == expected }
}

// example while loops

def list = [1,2,3]
while (list) {
  list.remove(0)
}
assert list == []

while (list.size() < 3) list << list.size() + 1
assert list [1,2,3]

// multiple for loop examples

def store = ''
for (String s in 'a'..'c') store += s
assert store == 'abc'

store = ''
for (i in [1,2,3]) {
  store += i
}
assert store  == '123'

def myString = 'Old school Java'
store = ''
for (int i = 0; i < myString.size(); i++) {
  store += myString[i]
}
assert store == myString

myString = 'Java range index'
store = ''
for (int i : 0 ..< myString.size()) {
  store += myString[i]
}
assert store == myString

myString = 'Groovy range index'
store = ''
for (i in 0 ..< myString.size()) {
  store += myString[i]
}
assert store == myString

myString = 'Java String Iterable'
store = ''
for (String s : myString) {
  store += s
}
assert store == myString

myString = 'Groovy Iterator'
store = ''
for (s in myString) {
  store += s
}
assert store == myString

// simple break and continue

def a = 1
while (true) {
  a++
  break
}
assert a == 2

for (i in 0..10) {
  if (i == 0) continue
  a++
  if (i > 0) break
}
assert a == 3

// exception handling in Groovy

def myMethod() {
  throw new IllegalArgumentException()
}

def log = []
try {
  myMethod()
} catch (Exception e) {
  log << e.toString()
} finally {
  log << 'finally'
}
assert log.size() == 2