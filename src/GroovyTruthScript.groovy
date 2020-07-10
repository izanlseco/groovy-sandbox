// example Boolean test evaluations

assert true
assert !false

assert ('a' =~ /./)
assert !('a' =~ /b/)

assert [1]
assert ![]

Iterator iter = [1].iterator()
assert iter
iter.next()
assert !iter

assert ['a':1]
assert ![:]

assert 1
assert 1.1
assert 1.2f
assert 1.3g
assert 2L
assert 3G
assert !0

assert ! null
assert new Object()

class AlwaysFalse {
  boolean asBoolean() { false }
}
assert ! new AlwaysFalse()

// what happens when == is mistyped as =

def x = 1
if (x == 2) {
  assert false
}
/*
if (x = 2) {
  println x
}
*/
if ((x = 3)) {
  println x
}
assert x == 3

def store = []
while (x = x - 1) {
  store << x
}
assert store  == [2, 1]

while (x = 2) {
  println x
  break
}

// the if statement in action

if (true) assert true
else      assert false

if (1) {
  assert true
} else {
  assert false
}
if ('nonempty') assert true
else if (['x']) assert false
else            assert false
if (0)          assert false
else if ([])    assert false
else            assert true

// the conditional operator and the Elvis operator

def result = (1==1) ? 'ok' : 'failed'
assert result == 'ok'
result = 'some string' ? 10 : ['x']
assert result == 10

def argument = "given"
def standard = "default"
def resultTwo = argument ? argument : standard
assert resultTwo == "given"

def resultTwoSimilar = argument ?: standard
assert resultTwoSimilar == "given"

// general switch appearance is like Java or C

def a = 1
def log = ''
switch (a) {
  case 0 : log += '0' // Fall through
  case 1 : log += '1' // Fall through
  case 2 : log += '2' ; break
  default : log += 'default'
}
assert log == '12'

// advanced switch and mixed classifiers

switch (10) {
  case 0                : assert false ; break
  case 0..9             : assert false ; break
  case [8,9,11]         : assert false ; break
  case Float            : assert false ; break
  case { it % 3 == 0 }  : assert false ; break
  case ~/../            : assert true ; break
  default               : assert false ; break
}