def ranges = new Ranges()

assert ranges.x.contains(5)
assert !ranges.x.contains(15)
assert ranges.x.size() == 10
assert ranges.x.from == 1
assert ranges.x.to == 10
assert  ranges.x.reverse() == 10..1