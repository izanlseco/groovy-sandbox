class Ranges {
    def x = 0..10
    def y = 0..<10
    def z = 0.0..1.0
    def w = ('a'..'c')
    def today = new Date()
    def yesterday = today -1
}

def ranges = new Ranges()

assert ranges.x.contains(0)
assert ranges.x.contains(5)
assert ranges.x.contains(10)
assert !ranges.x.contains(-1)
assert !ranges.x.contains(11)
assert ranges.x instanceof Range
assert !ranges.x.contains(15)
assert ranges.x.size() == 11
assert ranges.x.from == 0
assert ranges.x.to == 10
assert ranges.x.reverse() == 10..0

assert ranges.y.contains(9)
assert !ranges.y.contains(10)

assert ranges.z.contains(1.0)
assert ranges.z.containsWithinBounds(0.5)

assert (ranges.yesterday..ranges.today).size() == 2

assert ranges.w.contains('b')

def log = ''
for (element in 5..9) {
    log += element
}
assert log == '56789'

log = ''
for (element in 9..5) {
    log += element
}
assert log == '98765'

log = ''
(9..<5).each { element ->
    log += element
}
assert log == '9876'

/* Ranges are objects */

def result = ''
(5..9).each {element ->
    result += element
}
assert result == '56789'

/*these two are the same */

assert 5 in 0..10
assert (0..10).isCase(5)

def ages = [20, 36, 42, 56]
def midage = 21..50
assert ages.grep(midage) == [36, 42]