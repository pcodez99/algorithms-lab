const assert = require('node:assert/strict');
const BST = require('./bst');

const withSeven = new BST();
for (const k of [9, 8, 7]) withSeven.insert(k);
assert.equal(withSeven.root.k, 8);
assert.deepEqual(withSeven.InOrder(), [7, 8, 9]);
assert.deepEqual([withSeven.getSize(8), withSeven.getHeight(8)], [3, 2]);

for (const [values, type] of [
  [[30, 20, 10], 'SS'], [[10, 20, 30], 'DD'],
  [[30, 10, 20], 'SD'], [[10, 30, 20], 'DS'],
]) {
  const paused = new BST();
  paused.insert(values[0]);
  paused.insert(values[1]);
  assert.deepEqual(paused.beginInsert(values[2]), { key: values[0], type });
  assert.equal(paused.root.k, values[0]);
  assert.equal(Math.abs(paused.getBalanceFactor(values[0])), 2);
  assert.equal(paused.continueRotation(), null);
  assert.equal(paused.root.k, 20);
}

const reported = new BST();
for (const k of [17, 10, 45, 5, 22, 76, 7]) reported.insert(k);
assert.equal(reported.root.left.k, 7);
assert.deepEqual([reported.root.left.left.k, reported.root.left.right.k], [5, 10]);
assert.deepEqual(reported.InOrder(), [5, 7, 10, 17, 22, 45, 76]);

const tree = new BST();
for (const k of [40, 20, 60, 10, 30, 50, 70, 25]) tree.insert(k);
tree.insert(25);
assert.deepEqual([tree.getSize(40), tree.getHeight(40), tree.getSize(20), tree.getHeight(20), tree.getBalanceFactor(20)], [8, 4, 4, 3, -1]);
assert.deepEqual(tree.InOrder(), [10, 20, 25, 30, 40, 50, 60, 70]);
for (const k of [10, 20, 40, 60, 25, 30, 50, 70]) {
  assert.ok(tree.get(tree.root, k));
  tree.deleteKey(k);
  const check = node => {
    if (!node) return [0, 0, []];
    const [ls, lh, left] = check(node.left);
    const [rs, rh, right] = check(node.right);
    assert.equal(node.size, 1 + ls + rs);
    assert.equal(node.height, 1 + Math.max(lh, rh));
    assert.ok(Math.abs(lh - rh) <= 1);
    assert.ok(left.every(x => x < node.k) && right.every(x => x > node.k));
    return [node.size, node.height, [...left, node.k, ...right]];
  };
  check(tree.root);
}
tree.deleteKey(999);
assert.equal(tree.root, null);
for (const [values, removed, expected, type] of [
  [[4, 2, 6, 1, 3], 6, 2, 'SS'],
  [[2, 1, 4, 3, 5], 1, 4, 'DD'],
  [[5, 2, 7, 4], 7, 4, 'SD'],
  [[2, 1, 5, 3], 1, 3, 'DS'],
]) {
  const paused = new BST();
  for (const k of values) paused.insert(k);
  assert.deepEqual(paused.beginDelete(removed), { key: values[0], type });
  assert.equal(paused.root.k, values[0]);
  assert.equal(Math.abs(paused.getBalanceFactor(values[0])), 2);
  assert.deepEqual(paused.InOrder(), values.filter(k => k !== removed).sort((a, b) => a - b));
  assert.equal(paused.continueRotation(), null);
  assert.equal(paused.root.k, expected);
  assert.ok(paused.InOrder().every(k => Math.abs(paused.getBalanceFactor(k)) <= 1));
}
const multi = new BST();
for (const k of [2, 0, 8, 3, 7, 9, 6, 4, 11, 5, 10, 1]) multi.insert(k);
assert.deepEqual(multi.beginDelete(8), { key: 9, type: 'DS' });
assert.deepEqual(multi.continueRotation(), { key: 7, type: 'SS' });
assert.equal(multi.continueRotation(), null);
assert.deepEqual(multi.InOrder(), [0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11]);
assert.ok(multi.InOrder().every(k => Math.abs(multi.getBalanceFactor(k)) <= 1));
const ss = new BST();
for (const k of [50, 30, 70, 20, 40, 10, 25, 5]) ss.insert(k);
assert.equal(ss.root.k, 30);
assert.equal(ss.root.right.left.k, 40);
assert.deepEqual(ss.InOrder(), [5, 10, 20, 25, 30, 40, 50, 70]);
assert.deepEqual([ss.getSize(30), ss.getHeight(30)], [8, 4]);
console.log('AVL Lab: inserimento e cancellazione con pausa, metadati OK');
